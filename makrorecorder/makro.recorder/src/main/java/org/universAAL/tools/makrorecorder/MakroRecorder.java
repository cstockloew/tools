package org.universAAL.tools.makrorecorder;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;


import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.tools.makrorecorder.gui.MainGUI;
import org.universAAL.tools.makrorecorder.pattern.Pattern;

/**
*
* @author maxim djakow
*/
public class MakroRecorder {
    
    private static MakroRecorder instance = null;

    public static MakroRecorder getInstance() {
        if(instance == null)
            instance = new MakroRecorder();
        return instance;
    }
    
    private static final String patternBaseDir = "Pattern/";
    
    private static Vector<File> patternDirs;
        
    private HashMap<String,Pattern> pattern = null;
    
    private DefaultServiceCaller sc = null;
    
    private MainGUI gui = null;
    
    public MakroRecorder() {
        pattern = new HashMap<String, Pattern>();
        patternDirs = new Vector<File>();
        patternDirs.add(new File(patternBaseDir));
        sc = new DefaultServiceCaller(Activator.getModuleContext());
        for(File f : patternDirs)
        	f.mkdir();
    }
    
    public void showGUI() {
    	gui = new MainGUI();
        gui.setVisible(true);
    }

    public static String getPatternBaseDir() {
        return patternBaseDir;
    }
    
    public static Vector<File> getPatternDirs() {
		return patternDirs;
	}
    
    public static HashMap<String, Pattern> getAllPattern() {
        return getInstance().pattern;
    }
    
    public static Collection<String> getAllPatternNames() {
        return getInstance().pattern.keySet();
    }
    
    public static Pattern getPatternByName(String name) {
        return getInstance().pattern.get(name);
    }
    
    public static boolean addPattern(Pattern newPattern) {
        if(getInstance().pattern != null && newPattern != null) {
        	getInstance().pattern.put(newPattern.getName(),newPattern);
        	if(getInstance().gui != null)
        		getInstance().gui.reloadPattern();
        	return true;
        }
        return false;
    }
    
    public static boolean removePattern(String name) {
    	if(getInstance().pattern != null && name != null) {    		
    		Pattern removedPattern = getInstance().pattern.remove(name);
    		if(removedPattern != null) {
	    		removedPattern.deactivate();
	    		File file = removedPattern.getFile();
				if(file.exists()) {
					file.delete();
				}
    		}
    	}
    	return false;
    }
    
    public static boolean savePattern(Pattern newPattern) {    	
    	addPattern(newPattern);
    	newPattern.saveToFile();
    	return true;
    }
    
    public static void saveToFiles() {
        for (String key : getAllPatternNames()) {
            getPatternByName(key).saveToFile();
        }
    }
    
    public static void loadFromFiles() {
    	for(File d : patternDirs) {
	    	 File[] files = d.listFiles();
	    	 if(files != null) {
		    	 for(File f : files) {
		    		 if(f.toString().endsWith(Pattern.patternFileType)) {
		    			 addPattern(loadFromFile(f));
		    		 }
		    	 }
	    	 }
    	}
	}
    
    public static Pattern loadFromFile(File f) {
    	return Pattern.loadFromFile(f);
    }
    
    public static boolean sendOut(Resource r) {
    	if(r instanceof ServiceRequest) {
			return getInstance().sc.call((ServiceRequest)r).getCallStatus() == CallStatus.succeeded;
		} else if(r instanceof ContextEvent) {
			
		}
    	return false;
    }
    
    public static void reload() {
    	getInstance().pattern.clear();
    	loadFromFiles();
	}
}
