package org.universaal.tools.packaging.tool.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessExecutor {
	
	public static int runMavenCommand(String options, String workingDir){
		if(Configurator.local.isOfflineMode()){
			System.out.println("*** OFFLINE MODE ENABLED ***");
			options = "--offline "+options;
			
		}
		return runCommand(Configurator.local.getMavenCommand()+" "+options, workingDir);
	}
	
	public static int runCommand(String commandLine, String workingDir) {
		// TODO Auto-generated method stub
		if(!new File(workingDir).isDirectory()) workingDir = onlyPath(workingDir);
		String[] command = commandLine.split(" ");
        ProcessBuilder probuilder = new ProcessBuilder( command );

        //You can set up your work directory
        probuilder.directory(new File(workingDir));
        
        
        Process process = null;
		try {
			process = probuilder.start();
		  //Read out dir output
	        InputStream is = process.getInputStream();
	        InputStreamReader isr = new InputStreamReader(is);
	        BufferedReader br = new BufferedReader(isr);
	        String line;
	        System.out.printf("\n---------\nrunning %s\n---------\n\n",
	        		commandLine);
	        while ((line = br.readLine()) != null) {
	            System.out.println(line);
	        }
	        
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

      
        //Wait to get exit value
        try {
            int exitValue = process.waitFor();
            System.out.println("\nExit Value is " + exitValue+"\n---------\n");
            return exitValue;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }
	}

	private static String onlyPath(String path){
		String[] segments = path.split("/");
		path = "";
		
		for(int i=0; i<segments.length-1; i++)
			path += "/" + segments[i];
		
		return path;
	}

}
