package org.universAAL.tools.makrorecorder.pattern;

import java.beans.XMLEncoder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.tools.makrorecorder.Activator;
import org.universAAL.tools.makrorecorder.MakroRecorder;
import org.universAAL.tools.makrorecorder.gui.patternedit.PatternEditGUI;


public class Pattern implements Serializable{
	
	private static final long serialVersionUID = 6803508667123373561L;
	
	public static final String patternFileType = ".xml";
	
	private String name = "";
	private String description = "";
	
	private transient Vector<Resource> input = new Vector<Resource>();
	
	private transient Vector<Resource> output = new Vector<Resource>();
	
	private boolean active = false;
	
	private transient BusRecorder busRecorder = new BusRecorder(this);
	
	private transient PatternListener listener = null;
	
	private transient PatternEditGUI gui = null;
	
	public Pattern() {
		
	}
	
	public Pattern(String name, String description) {
		this.name = name;
		this.description = description;
	}
        
    public Pattern clone() {
    	Pattern ret = new Pattern();
    	ret.name = name;
    	ret.description = description;
    	ret.input = input;
    	ret.output = output;
    	return ret;
    }	
	
	public void activate() {
		if(listener == null)
			listener = new PatternListener(this);
		listener.activate();
		active = true;
	}		
	
	public void deactivate() {
		if(listener != null)
			listener.deactivate();
		active = false;
		listener = null;
	}
	
	public void startRecording() {
		busRecorder.start();
	}
	
	public void stopRecording() {
		busRecorder.stop();
	}
	
	public void sendOutput() {		
		for(Resource r : output) {//pr�fe jedes Element in output ob es ein ServiceRequest is, wenn ja dann sende es raus
			MakroRecorder.sendOut(r);
		}
	}
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
        Vector<String> input = new Vector<String>();
        for(Resource r : this.input)
        	input.add(Activator.getSerializer().serialize(r));
        stream.writeObject(input);
        Vector<String> output = new Vector<String>();
        for(Resource r : this.output)
        	output.add(Activator.getSerializer().serialize(r));
        stream.writeObject(output);
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream stream) throws IOException,ClassNotFoundException {
		stream.defaultReadObject();
		this.input = new Vector<Resource>();
		this.output = new Vector<Resource>();
        Vector<String> input = (Vector<String>) stream.readObject();          
        Vector<String> output = (Vector<String>) stream.readObject();           
        for(String s : input)
        	this.input.add((Resource)Activator.getSerializer().deserialize(s));          
        for(String s : output)
        	this.output.add((Resource)Activator.getSerializer().deserialize(s));
	}
	
	public File getFile() {
		return new File(MakroRecorder.getPatternBaseDir()+getName()+patternFileType);
	}
        
    public void saveToFile() {
        saveToFile(getFile());
        saveToXMLFile(new File(MakroRecorder.getPatternBaseDir()+getName()+".xml"));
    }
        
    public void saveToFile(File file) {
    	try {
    		FileOutputStream fos = new FileOutputStream(file);
    		ObjectOutputStream o = new ObjectOutputStream(fos);
    		o.writeObject(this);
    	} catch (FileNotFoundException ex) {
    		Logger.getLogger(Pattern.class.getName()).log(Level.SEVERE, null, ex);
    	} catch (IOException ex) {
            Logger.getLogger(Pattern.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveToXMLFile(File file) {
    	/*String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
    	xml += "\n<pattern>";
    	
    	xml += "\n\t<name>";
    	xml += "\n\t\t"+name;
    	xml += "\n\t</name>";
    	
    	xml += "\n\t<description>";
    	xml += "\n\t\t"+description;
    	xml += "\n\t</description>";
    	
    	xml += "\n\t<active>";
    	xml += "\n\t\t"+active;
    	xml += "\n\t</active>";
    	
    	xml += "\n\t<input>";
    	for(Resource r : input) {    		
    		xml += "\n\t\t<resource>";
    		xml += "\n\t\t\t"+Activator.getSerializer().serialize(r);
    		xml += "\n\t\t</resource>";
    	}
    	xml += "\n\t</input>";
    	
    	xml += "\n\t<output>";
    	for(Resource r : output) {    		
    		xml += "\n\t\t<resource>";
    		xml += "\n\t\t\t"+Activator.getSerializer().serialize(r);
    		xml += "\n\t\t</resource>";
    	}
    	xml += "\n\t</output>";
    	
    	xml += "\n</pattern>";
    	try {
	    	BufferedWriter out = new BufferedWriter(new FileWriter(file));
	    	out.write(xml);
	    	out.close();
    	} catch (IOException e) {
    		
    	}*/
    	PatternXMLParser.genXML(this);
    }
    
    
        
    public static Pattern loadFromFile(File file) {
    	
    	Pattern ret = null;
        /*try {
        	FileInputStream fis = new FileInputStream(file);
        	ObjectInputStream ois = new ObjectInputStream(fis);
            ret = (Pattern)ois.readObject();
        } catch (FileNotFoundException ex) {
        	Logger.getLogger(Pattern.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Pattern.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        }
        if(ret.isActive())
        	ret.activate();*/
        ret = PatternXMLParser.genPattern(file);
        ret.busRecorder = new BusRecorder(ret);
        return ret;
    }    
        
    public boolean isActive() {
		return active;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Vector<Resource> getInput() {
		return input;
	}
	
	public Vector<Resource> getOutput() {
		return output;
	}
	
	public void addInput(Resource r) {
		input.add(r);
		if(gui!=null)
			gui.readInputs();
	}
	
	public void addOutput(Resource r) {
		output.add(r);
		if(gui!=null)
			gui.readOutputs();
	}
	
	public void showGUI() {
		gui = new PatternEditGUI(this);
		gui.setVisible(true);
	}
}
