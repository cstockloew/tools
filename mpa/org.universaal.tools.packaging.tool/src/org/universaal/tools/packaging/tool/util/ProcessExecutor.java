/*

        Copyright 2007-2014 CNR-ISTI, http://isti.cnr.it
        Institute of Information Science and Technologies
        of the Italian National Research Council

        See the NOTICE file distributed with this work for additional
        information regarding copyright ownership

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
 */
package org.universaal.tools.packaging.tool.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.universaal.tools.packaging.tool.preferences.EclipsePreferencesConfigurator;

/**
 * 
 * @author <a href="mailto:federico.volpini@isti.cnr.it">Federico Volpini</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano Lenzi</a>
 * @version $LastChangedRevision$ ( $LastChangedDate$ )
 */
public class ProcessExecutor {

    /**
     * ProcessExecutor must be changed to a class factory instead of a set of static method.
     * It is a bad design since we started to use execTime and End 
     */
    
    private static long execTime = 60000; //By default 1 minutes
    private static boolean end = false;

    public static int runMavenCommand(String options, String workingDir) {
	if (EclipsePreferencesConfigurator.local.isOfflineMode()) {
	    // System.out.println("*** OFFLINE MODE ENABLED ***");
	    options = "--offline " + options;

	}
	return runCommand(EclipsePreferencesConfigurator.local.getMavenCommand() + " " + options,
		workingDir);
    }

    public static int runCommand(String commandLine, String workingPath) {
	// TODO Auto-generated method stub
	File workingDir = new File(workingPath);	
	if ( workingDir.exists() == false ) {
	    System.out.println("[ERROR] The working directory path:" +workingPath + " does not exist. " +
	    		"Working directory is going to be ignored and we are going to use current directory"
		    );
	    workingDir = new File(".");	    
	    System.out.println("[WARNING] Using working directory:" +workingDir.getAbsolutePath() );
	}
	
	if ( workingDir.isDirectory() == false ) {
	    System.out.println("[WARNING] The working directory path:" +workingPath + " is pointing to a file. We are going to use the dirname as working directory");
	    workingDir = workingDir.getParentFile();
	    System.out.println("[WARNING] Using working directory:" +workingDir.getAbsolutePath() );
	}
	
	if ( workingDir.canWrite() == false || workingDir.canRead() == false ) {
	    System.out.println("[ERROR] Missing R/W permission to working directory:" +workingDir.getAbsolutePath()+ "" +
	    		"We are going to use the user's temp folder");
	    workingDir = new File(System.getProperty("java.io.tmpdir"));
	    System.out.println("[WARNING] Using working directory:" +workingDir.getAbsolutePath() );
	}
	
	//TODO You should use already a String[] or List<String> to avoid issue with space. 
	/*
	 * The current code breaks in case of parameters containing space like paths
	 * If using commandLine as type List<String>, you can use the follwing code
	 * String[] command = commandLine.toArray(new String[]{});  
	 */
	String[] command = commandLine.split(" ");
	ProcessBuilder probuilder = new ProcessBuilder(command);

	// You can set up your work directory
	probuilder.directory(workingDir);

	Process process = null;
	try {
	    process = probuilder.start();
	} catch (Throwable t) {
	    t.printStackTrace();
	    return -1;
	}

	try{
	    // Read out dir output
	    InputStream is = process.getInputStream();
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String line;
	    System.out.printf("\n---------\nrunning %s\n---------\n\n",
		    commandLine);
	    while ((line = br.readLine()) != null) {
		System.out.println(line);
	    }
	}catch(IOException ignored){
	}
	// Wait to get exit value
	try {
	    //TODO To avoid looking on the UI we should use process.exitValue() please look at method betterRun() 
	    int exitValue = process.waitFor();
	    System.out.println("\nExit Value is " + exitValue + "\n---------\n");
	    return exitValue;
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return -1;
	}
    }

    public static int betterRun( Process proc ){
	InputStream is = proc.getInputStream();
	int exit = -1;
	int l = -1;
	byte[] buf = new byte[1024];
	
	long timeout = System.currentTimeMillis() + getMaxExecutionTime(); 
	while ( true ) {
	    if ( System.currentTimeMillis() > timeout || isKilled() ) {
		proc.destroy();
	    }
        	try {
        	    try {
			Thread.sleep( 250 );
		    } catch (InterruptedException ignored) {
		    }
        	    try {
			l = is.available();
		    } catch (IOException ignored) {
			l = 0;
		    } 
        	    while ( l > 0 ){
        		/*
        		 * //FIX reading without reaching end of line may result of reading 
        		 * partially a UTF-8 symbol thus producing wrong output 
        		 */
        		int reading = Math.min(buf.length, l);
        		try {
			    is.read(buf, 0, reading);
			    System.out.print(new String(buf));
			    l = is.available();
			} catch (IOException ignored) {
			    l = 0;
			}
        	    }
        	    exit = proc.exitValue();
        	    break; //exiting loop
        	} catch (IllegalStateException ex) {
        	    /* It is not an error, it means only that process is still running */
        	    /*
        	     * Here we should check some flag for blocking the execution and we
        	     * should print the
        	     */
        	}
	}
	try {
	    l = is.available();
	} catch (IOException ignored) {
	    l = 0;
	}
	while (l > 0) {
	    /*
	     * //FIX reading without reaching end of line may result of reading
	     * partially a UTF-8 symbol thus producing wrong output
	     */
	    int reading = Math.min(buf.length, l);
	    try {
			is.read(buf, 0, reading);
			System.out.print(new String(buf));
			l = is.available();
	    } catch (IOException ignored) {
			l = 0;
	    }
	}

	return exit;
    }

    public static void kill() {
    	end  = true;
    }
    
    public static boolean isKilled() {
    	return end;
    }

    public static void setMaxExecutionTime(long ms) {
    	execTime  = ms;
    }
    
    public static long getMaxExecutionTime() {
    	return execTime;
    }
}
