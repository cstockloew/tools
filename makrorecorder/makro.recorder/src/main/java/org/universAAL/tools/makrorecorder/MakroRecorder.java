package org.universAAL.tools.makrorecorder;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.universAAL.tools.makrorecorder.gui.MainGUI;
import org.universAAL.tools.makrorecorder.pattern.Pattern;


public class MakroRecorder {
    
    private static MakroRecorder instance = null;
    
    private static String patternBaseDir = "Pattern/";
    
    public static MakroRecorder getInstance() {
        if(instance == null)
            instance = new MakroRecorder();
        return instance;
    }
    
    private HashMap<String,Pattern> pattern = null;
    
    private MainGUI gui = null;
    
    public MakroRecorder() {
        pattern = new HashMap<String, Pattern>();
        new File(patternBaseDir).mkdir();
    }
    
    public void showGUI() {
    	gui = new MainGUI();
        gui.setVisible(true);
    }

    public static String getPatternBaseDir() {
        return patternBaseDir;
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
    		if (JOptionPane.showConfirmDialog(null, "Soll das Pattern '"+name+"' gelöscht werden?", "Pattern löschen", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
    			return false;
    		}
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
    	if(getInstance().pattern.containsKey(newPattern.getName())) {
    		if (JOptionPane.showConfirmDialog(
    				null, 
    				"Pattern mit dem Namen '"+newPattern.getName()+"' bereits vorhanden.\nSoll das Pattern Überschrieben werden?", 
    				"Pattern speichern", 
    				JOptionPane.YES_NO_OPTION
    				) != JOptionPane.YES_OPTION)
    			return false;
    	}
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
    	 File[] files = new File(patternBaseDir).listFiles();
    	 if(files != null) {
	    	 for(File f : files) {
	    		 if(f.toString().endsWith(".pattern")) {
	    			 addPattern(Pattern.loadFromFile(f));
	    		 }
	    	 }
    	 }
	}
}
