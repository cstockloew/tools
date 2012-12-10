package org.universAAL.tools.makrorecorder.pattern;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.sodapop.msg.MessageContentSerializer;
import org.universAAL.tools.makrorecorder.Activator;
import org.universAAL.tools.makrorecorder.MakroRecorder;
import org.universAAL.tools.makrorecorder.gui.PatternEditGUI;


public class Pattern implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6803508667123373561L;
	
	private String name = "";
	private String description = "";
	
	private transient Vector<Resource> input = new Vector<Resource>();
	
	private transient Vector<Resource> output = new Vector<Resource>();
	
	private boolean active = false;
	
	private transient PatternRecorder recorder = null;
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
		recorder = new PatternRecorder(this);
		recorder.start();
	}
	
	public void stopRecording() {
		recorder.stop();
		recorder = null;
	}
	
	/*private ContextEventPattern[] createSubscription(ContextEvent ce) {
		ContextEventPattern[] ret = new ContextEventPattern[]{new ContextEventPattern()};
		
		if (ce != null) {
			MergedRestriction restriction = new MergedRestriction();
			restriction.addRestriction(MergedRestriction.getFixedValueRestriction(ContextEvent.PROP_RDF_SUBJECT,ce.getProperty(ContextEvent.PROP_RDF_SUBJECT)));
			restriction.addRestriction(MergedRestriction.getFixedValueRestriction(ContextEvent.PROP_RDF_PREDICATE,ce.getProperty(ContextEvent.PROP_RDF_PREDICATE)));
			restriction.addRestriction(MergedRestriction.getFixedValueRestriction(ContextEvent.PROP_RDF_OBJECT,ce.getProperty(ContextEvent.PROP_RDF_OBJECT)));
			ret[0].addRestriction(restriction);
		}
		
		return ret;
	}
	
	private void Subscribe(ContextEventPattern[] cep) {
            //removeMatchingRegParams(getAllProvisions());
            //addNewRegParams(cep);
	}*/
	
	public void sendOutput() {
			//JOptionPane.showMessageDialog(null, "Pattern " + name +" activated!");
            DefaultServiceCaller sc = new DefaultServiceCaller(Activator.getModuleContext());
            for(Resource r : (Vector<Resource>)output.clone()) {//pr�fe jedes Element in output ob es ein ServiceRequest is, wenn ja dann sende es raus
                if(r instanceof ServiceRequest) {
                    sc.call((ServiceRequest)r);
                }
            }
	}
        
        private void writeObject(ObjectOutputStream stream) throws IOException {
        	MessageContentSerializer serializer = (MessageContentSerializer) Activator.getBundleContext().getService(Activator.getBundleContext().getServiceReference(MessageContentSerializer.class.getName()));
            stream.defaultWriteObject();
            Vector<String> input = new Vector<String>();
            for(Resource r : this.input)
                input.add(serializer.serialize(r));
            stream.writeObject(input);
            Vector<String> output = new Vector<String>();
            for(Resource r : this.output)
                output.add(serializer.serialize(r));
            stream.writeObject(output);
        }
        
        private void readObject(ObjectInputStream stream) throws IOException,ClassNotFoundException {
        	MessageContentSerializer serializer = (MessageContentSerializer) Activator.getBundleContext().getService(Activator.getBundleContext().getServiceReference(MessageContentSerializer.class.getName()));
            stream.defaultReadObject();
            this.input = new Vector<Resource>();
            this.output = new Vector<Resource>();
            Vector<String> input = (Vector<String>) stream.readObject();          
            Vector<String> output = (Vector<String>) stream.readObject();           
            for(String s : input)       
                this.input.add((Resource)serializer.deserialize(s));          
            for(String s : output)         
                this.output.add((Resource)serializer.deserialize(s));
        }
        
        public File getFile() {
			return new File(MakroRecorder.getPatternBaseDir()+getName()+".pattern");
		}
        
        public void saveToFile() {
            saveToFile(getFile());
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
        
        public static Pattern loadFromFile(File file) {
        	Pattern ret = null;
            try {
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
            if(ret.active)
            	ret.activate();
            return ret;
            
        }
        
        
        
        
        
    public void setActive(boolean active) {
		this.active = active;
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
		//if(gui!=null)
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
