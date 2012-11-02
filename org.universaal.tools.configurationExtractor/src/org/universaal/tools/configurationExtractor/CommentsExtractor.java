package org.universaal.tools.configurationExtractor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javax.swing.JOptionPane;

import org.universaal.tools.configurationExtractor.data.ConfigItem;
import org.universaal.tools.configurationExtractor.data.GeneralUCConfig;
import org.universaal.tools.configurationExtractor.data.ItemType;


/**
 * CommentsExtractor extracts the configuration comments from Java projects
 * 
 * @author schwende
 */
public class CommentsExtractor {
	

    private Set<String> varNames = new HashSet<String>();
    
    private GeneralUCConfig generalItem;
    private static String generalItemFile;
    

	/**
	 * Extract annotations of all java classes from the given directory
	 * @param directory of classes with annotations
	 * @throws Exception
	 */
	public LinkedList<ConfigItem> getConfigFromProject(String dir) throws Exception {
		LinkedList<File> javaFiles = getJavaFiles(new File(dir));
		LinkedList<ConfigItem> items = new LinkedList<ConfigItem>();
		List<ConfigItem> comments;
		
		// add the comments of each file into the List
		for(File file: javaFiles) {
			comments = getCommentsInFile(file.getAbsolutePath());
			if (comments == null) {
				return null;
			}
			items.addAll(comments);
		}
		 // check if the general usecase configuration was found
	    if (generalItem == null) {
	    	JOptionPane.showMessageDialog(null, "The general usecase configuration comment could not be found!\n" +
	    			"Please insert it before continuing!", "Error found!", JOptionPane.ERROR_MESSAGE);
	    	return null;
	    }
		
		return items;
	}	
	
	/**
	 * Checks (DFS) all files in a given directory for source files with java extension.
	 * 
	 * @param	startDir Start directory for search java files
	 * @return	the list with all java files
	 * 
	 * @author Ilja
	 */
	private static LinkedList<File> getJavaFiles(File startDir) {
		LinkedList<File> files = new LinkedList<File>();
	    Stack<File> dirs = new Stack<File>();
	    Stack<File> srcDirs = new Stack<File>();

	    //file with source directory
	    File src = null;
	    
	    if(startDir.isDirectory() && startDir.getName().equals("src")){
	    	src=startDir;
	    }else if ( startDir.isDirectory()) {
	    	boolean srcWasFounded=false;
	    	dirs.push( startDir );
		    while (dirs.size() > 0 && !srcWasFounded) {
		    	for (File file : dirs.pop().listFiles()) {
		    		if (file.isDirectory() && file.getName().equals("src")) {
		    			src = file ;
		    			srcWasFounded=true;
		    		}else if (file.isDirectory()) {
		    			dirs.push( file );
		    		}
		    	}
		    }
	    }
	    if(src!=null){
		    srcDirs.push( src );
		    while (srcDirs.size() > 0) {
		    	for (File srcFile : srcDirs.pop().listFiles()) {
		    		if (srcFile.isDirectory()) {
		    			srcDirs.push( srcFile );
		    		}
		    		else if (srcFile.canRead()) {
		    			files.add( srcFile );
		    		}
		    	}
		    }
	
		    Iterator itr = files.iterator(); 
		    while(itr.hasNext()) {
		    	File file=(File) itr.next();
		    	if (!file.getName().endsWith("java")) {
		    		itr.remove();
		    	}
		    } 
	    
	    }
	    return files;
	}

	/**
	 * Checks a java file for configuration comments and puts corresponding config items in a list
	 * @param filename file to check
	 * @return List with ConfigItems
	 */
	private List<ConfigItem> getCommentsInFile(String filename) {
		try {
		    // create a StreamTokenizer for the file to parse
		    FileReader rd = new FileReader(filename);
		    StreamTokenizer st = new StreamTokenizer(rd);

		    // some needed preferences for the StreamTokenizer
		    st.parseNumbers();
		    st.wordChars('_', '_');
		    st.eolIsSignificant(true);
		    st.ordinaryChars(0, ' ');
		    st.ordinaryChar('/');
		    st.slashSlashComments(false);
		    st.slashStarComments(true);
		    
		    // parse this file
		    int token;
		    List<ConfigItem> itemList = new LinkedList<ConfigItem>();
		    ConfigItem item;
		    String param, value;
		    while ((token = st.nextToken()) != StreamTokenizer.TT_EOF) {
		        if (token == '/' && (token = st.nextToken()) == '/'
		        		&& (token = st.nextToken()) == '$' && (token = st.nextToken()) == '$') { // $$ found
		        	
		        	token = st.nextToken(); // now there should be the ItemType in sval
		        	ItemType it = ItemType.fromString(st.sval);
		        	
		        	if (it == null && ! st.sval.equals("ucconfig")) { // unknown comment found
		        		System.err.println("Unknown item type found: " + st.sval + ", ignoring it ...");
		        		continue;
		        		
		        	} else if (st.sval.equals("ucconfig")) { // general ucecase configuration found
		        		item = new GeneralUCConfig();
		        		if (generalItem == null) {
		        			generalItem = (GeneralUCConfig) item;
		        			generalItemFile = filename;
		        		} else { // general item found the second time
		        			JOptionPane.showMessageDialog(null, "Multiple general usecase configuration comments found!\n" +
				        			"There is only one such item allowed!" +
				        			"\n\nPlease insert it before continuing!", "Error found!", JOptionPane.ERROR_MESSAGE);
				        	return null;
		        		}
		        		
		        	} else { // normal config item found
		        		item = new ConfigItem(it);
		        	}
		        	
		        	
		            while ((token = st.nextToken()) != StreamTokenizer.TT_EOL && token != 10 && token != 13) {
		            	
		            	if (token == '$') { // new parameter starts
		            		if ((token = st.nextToken()) != StreamTokenizer.TT_WORD) {
		            			System.err.println("Invalid configuration item found!");
		            		}
		            		param = st.sval;
		            		
		            		do {token = st.nextToken();} while (token != StreamTokenizer.TT_WORD && token != '"');
		            		value = st.sval;
		            		
		            		if (! item.setParameter(param, value)) {
		            			System.err.println("Unknown parameter found: " + param);
		            		}
		            	}
		            	
		            }
		            
		            // check if there are errors
		            String error = item.validate(varNames);
		            
		            if (error != null) {
		            	JOptionPane.showMessageDialog(null, "An error was found in file "
		            			+ filename.substring(filename.indexOf("\\src\\")) + ":\n" + error
		            			+ "\n\nPlease correct this before continuing!", "Error found!", JOptionPane.ERROR_MESSAGE);
		            	return null;
		            }
		            
		            itemList.add(item);
		        }
		    }
		    
		    rd.close();
		    
		    return itemList;
		    
		    
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * add the general usecase configuration into the source code (replacing the existing one)
	 * @param generalConf configuration to save
	 */
	public static void addGeneralConfig(GeneralUCConfig generalConf) {

		File f=new File(generalItemFile);

		FileInputStream fs = null;
		InputStreamReader in = null;
		BufferedReader br = null;

		StringBuffer sb = new StringBuffer();

		String textinLine;

		try {
			// read the file containing the general information
			fs = new FileInputStream(f);
			in = new InputStreamReader(fs);
			br = new BufferedReader(in);

			while(( textinLine = br.readLine()) != null) {
				sb.append(textinLine + "\n");
			}

			// find the general information comment
			int uidIndex = sb.indexOf("//"+"$$uid ");
			if (uidIndex >= 0) {
				sb.delete(uidIndex, sb.indexOf("\n", uidIndex)); // delete the existing comment
			}
			
			// add the new information
			String textToEdit1 = "//"+"$$ucconfig";
			int ucconfIndex = sb.indexOf(textToEdit1);
			int newLine = sb.lastIndexOf("\n", ucconfIndex);
			if (newLine < 0) newLine = 0;
			String spaces = sb.substring(newLine + 1, ucconfIndex);
			sb.replace(ucconfIndex, sb.indexOf("\n", ucconfIndex) + 1, getGeneralConfigComment(generalConf, spaces));

			fs.close();
			in.close();
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			FileWriter fstream = new FileWriter(f);
			BufferedWriter outobj = new BufferedWriter(fstream);
			outobj.write(sb.toString());
			outobj.close();

		} catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}

	/**
	 * creates a comment for the general configuration
	 * @param generalConf configuration for the comment
	 * @param spaces the space before the previous comment, used to make the new comment fit into the source
	 * @return the comment
	 */
	private static String getGeneralConfigComment(GeneralUCConfig generalConf, String spaces) {
		return "//"+"$$ucconfig $ucname \"" + generalConf.getUcName() + "\" " +
				"$versionnr \"" + generalConf.getVersionNr() + "\" " +
				"$author \"" + generalConf.getAuthor() + "\"\n" +
				spaces + "//" + "$$uid " + generalConf.getUid();
	}
	
	
	
}
