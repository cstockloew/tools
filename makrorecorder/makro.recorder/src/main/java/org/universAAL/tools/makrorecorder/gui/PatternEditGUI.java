package org.universAAL.tools.makrorecorder.gui;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.sodapop.msg.MessageContentSerializer;
import org.universAAL.tools.makrorecorder.Activator;
import org.universAAL.tools.makrorecorder.MakroRecorder;
import org.universAAL.tools.makrorecorder.pattern.Pattern;
import org.universAAL.tools.makrorecorder.resourcebuilder.ContextEventBuilder;

/**
 *
 * @author mdjakow
 */
public class PatternEditGUI extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5765173046552911297L;
	
	
	private Pattern pattern = null;
    /**
     * Creates new form PatternEditGUI
     */
    public PatternEditGUI() {
    	initComponents();
        this.pattern = new Pattern();
        readPattern();
    }

    public PatternEditGUI(Pattern pattern) {
        initComponents();
        this.pattern = pattern;
        readPattern();        
    }
    
      
    private void readPattern() {
        nameTextField.setText(pattern.getName());
        descriptionTextArea.setText(pattern.getDescription());
        readInputs();
        readOutputs();
    }
    
    public void readInputs() {
        ((DefaultListModel)inputsList.getModel()).clear();
        for(Resource r : pattern.getInput()) {
        	String str = ((ContextEvent)r).getRDFSubject().toString().substring(((ContextEvent)r).getRDFSubject().toString().indexOf("#")+1)+" ";
        	str += ((ContextEvent)r).getRDFPredicate().toString().substring(((ContextEvent)r).getRDFPredicate().toString().indexOf("#")+1)+" ";
        	str += ((ContextEvent)r).getRDFObject().toString().substring(((ContextEvent)r).getRDFObject().toString().indexOf("#")+1);
            ((DefaultListModel)inputsList.getModel()).addElement(str);
        }
        inputsList.setSelectedIndex(-1);
    }
    
    public void readOutputs() {
        ((DefaultListModel)outputsList.getModel()).clear();
        for(Resource r : pattern.getOutput()) {
            ((DefaultListModel)outputsList.getModel()).addElement(r.toString());
        }
        outputsList.setSelectedIndex(-1);
    }
    
    private void savePattern() {
    	if(nameTextField.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(this, "Name fehlt", "info", JOptionPane.WARNING_MESSAGE);
    		return;
    	}
        pattern.setName(nameTextField.getText());
        pattern.setDescription(descriptionTextArea.getText());
        if(MakroRecorder.savePattern(pattern)) {
        	this.dispose();
        }
    }


    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        inputsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputsList = new javax.swing.JList();
        editButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        outputsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        outputsList = new javax.swing.JList();
        editButton1 = new javax.swing.JButton();
        newButton1 = new javax.swing.JButton();
        deleteButton1 = new javax.swing.JButton();
        infoPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        controlPanel = new javax.swing.JPanel();
        RecordingPanel = new javax.swing.JPanel();
        recordButton = new javax.swing.JButton();
        recordingLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Pattern");
        setBounds(new java.awt.Rectangle(200, 200, 800, 500));
        setMinimumSize(new java.awt.Dimension(900, 700));
        setPreferredSize(new java.awt.Dimension(900, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        inputsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Inputs"));
        inputsPanel.setLayout(new java.awt.GridBagLayout());

        inputsList.setModel(new DefaultListModel());
        inputsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        inputsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
            	if(inputsList.getSelectedIndex()>-1) {
        	        /*String subject = ((ContextEvent)pattern.getInput().get(inputsList.getSelectedIndex())).getRDFSubject().toString();
        	        String predicate = ((ContextEvent)pattern.getInput().get(inputsList.getSelectedIndex())).getRDFPredicate();
        	        String object = ((ContextEvent)pattern.getInput().get(inputsList.getSelectedIndex())).getRDFObject().toString();
        	        subjectLabel.setText(subject.substring(subject.indexOf("#")+1));
        	        predicateLabel.setText(predicate.substring(predicate.indexOf("#")+1));
        	        objectLabel.setText(object.substring(object.indexOf("#")+1));*/
            		Resource r = pattern.getInput().get(inputsList.getSelectedIndex());
        	        inputsLabel.setText(genInfo(r));       
            	}
            }
        });
        jScrollPane1.setViewportView(inputsList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;


        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        inputsPanel.add(jScrollPane1, gridBagConstraints);
        
        inputsLabel = new JLabel();
        inputsLabel.setVerticalAlignment(SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        inputsPanel.add(inputsLabel,gridBagConstraints);

        editButton.setText("Bearbeiten");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if (inputsList.getSelectedIndex()>-1) {
                    new editContextEventDialog(null, true, (ContextEvent) pattern.getInput().get(inputsList.getSelectedIndex())).showDialog();
                    readInputs();
                }
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        inputsPanel.add(editButton, gridBagConstraints);

        newButton.setText("Erstellen");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        inputsPanel.add(newButton, gridBagConstraints);

        deleteButton.setText("Löschen");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if (inputsList.getSelectedIndex()>-1) {
                    if (JOptionPane.showConfirmDialog(null, "delete Context-Event?") == JOptionPane.YES_OPTION) {
                        pattern.getInput().remove(inputsList.getSelectedIndex());
                        readInputs();
                    }
                }
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        inputsPanel.add(deleteButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.6;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(inputsPanel, gridBagConstraints);

        outputsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Outputs"));
        outputsPanel.setLayout(new java.awt.GridBagLayout());

        outputsList.setModel(new DefaultListModel());
        outputsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        outputsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(outputsList.getSelectedIndex()>-1) {
			        Resource r = pattern.getOutput().get(outputsList.getSelectedIndex());
			        outputsLabel.setText(genInfo(r));        
		    	}
			}
		});
        jScrollPane2.setViewportView(outputsList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        outputsPanel.add(jScrollPane2, gridBagConstraints);
        
        outputsLabel = new JLabel();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        outputsPanel.add(outputsLabel);

        editButton1.setText("Bearbeiten");
        editButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	ContextEvent ce = ContextEventBuilder.createContextEvent();
                if(ce!=null) {
                    pattern.getInput().add(ce);
                    readInputs();
                }        
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        outputsPanel.add(editButton1, gridBagConstraints);

        newButton1.setText("Erstellen");
        newButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if(outputsList.getSelectedIndex()>-1) {
			        Resource r = pattern.getOutput().get(outputsList.getSelectedIndex());
			        new DefaultServiceCaller(Activator.getModuleContext()).call((ServiceRequest)r);
		    	}
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        outputsPanel.add(newButton1, gridBagConstraints);

        deleteButton1.setText("Löschen");
        deleteButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        outputsPanel.add(deleteButton1, gridBagConstraints);
        
        JButton testButton = new JButton();
        testButton.setText("SR-test");
        testButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        outputsPanel.add(testButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.6;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(outputsPanel, gridBagConstraints);

        infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Pattern Information"));
        infoPanel.setLayout(new java.awt.GridBagLayout());

        jLabel7.setText("Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(jLabel7, gridBagConstraints);
        
        nameTextField.setText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(nameTextField, gridBagConstraints);

        jLabel8.setText("Beschreibung:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(jLabel8, gridBagConstraints);

        descriptionTextArea.setColumns(20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setText("");
        jScrollPane3.setViewportView(descriptionTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(jScrollPane3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(infoPanel, gridBagConstraints);

        controlPanel.setLayout(new java.awt.GridBagLayout());

        RecordingPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Aufnahme"));
        RecordingPanel.setLayout(new java.awt.GridBagLayout());

        recordButton.setForeground(new java.awt.Color(51, 102, 0));
        recordButton.setText("starte Aufnahme");
        recordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordButtonActionPerformed(evt);
            }
        });
        RecordingPanel.add(recordButton, new java.awt.GridBagConstraints());

        recordingLabel.setText("Aufnahme gestoppt");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        RecordingPanel.add(recordingLabel, gridBagConstraints);

        jLabel9.setText("Status:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 0);
        RecordingPanel.add(jLabel9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        controlPanel.add(RecordingPanel, gridBagConstraints);

        cancelButton.setText("Abbrechen");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	dispose();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        controlPanel.add(cancelButton, gridBagConstraints);

        okButton.setText("Speichern");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savePattern();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        controlPanel.add(okButton, gridBagConstraints);
        
        resetButton.setText("Zurücksetzen");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	pattern = Pattern.loadFromFile(pattern.getFile());
            	readPattern();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        controlPanel.add(resetButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(controlPanel, gridBagConstraints);

        pack();
    }

    private void recordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordButtonActionPerformed
        
        if(recordButton.getForeground().equals(new Color(128, 25, 0))) {
            recordButton.setForeground(new Color(25, 128, 0));
            recordButton.setText("starte Aufnahme");
            pattern.stopRecording();
            recordingLabel.setText("Aufnahme gestoppt");
        } else {
            recordButton.setForeground(new Color(128, 25, 0));
            recordButton.setText("beende Aufnahme");
            pattern.startRecording();
            recordingLabel.setText("Aufnahme läuft");
        }
    }


    public String genInfo(Resource r) {
    	String info = "<html><body>";
    	if(r.getClass().equals(ServiceRequest.class))
    		info += genInfo((ServiceRequest)r);
    	else if(r.getClass().equals(ContextEvent.class))
    		info += genInfo((ContextEvent)r);
    	info += "</body></html>";
    	return info;
    }
    
    public String genInfo(ContextEvent ce) {
    	String info = "";
    	/*info += "<table style='border: solid 1px #000000;'>";
	    	info += "<tr>";
		    	info += "<th align='right'>Subject:</th>";
		    	info += "<th align='left'>";
		    		info += ce.getRDFSubject().toString().substring(ce.getRDFSubject().toString().indexOf("#")+1);
		    	info += "</th>";
	    	info += "</tr>";
	    	info += "<tr>";
		    	info += "<th align='right'>Predicate:</th>";
		    	info += "<th align='left'>";
		    		info += ce.getRDFPredicate().toString().substring(ce.getRDFPredicate().toString().indexOf("#")+1);
		    	info += "</th>";
	    	info += "</tr>";
	    	info += "<tr>";
		    	info += "<th align='right'>Object:</th>";
		    	info += "<th align='left'>";
		    		info += ce.getRDFObject().toString().substring(ce.getRDFObject().toString().indexOf("#")+1);
		    	info += "</th>";
	    	info += "</tr>";
    	info += "</table>";*/
    	info += "<h2>Context-Event</h2>";
    	info += "Subject:<br/>";
    	info += ce.getRDFSubject().toString();
    	info += "<br/><br/>Predicate:<br/>";
    	info += ce.getRDFPredicate().toString();
    	info += "<br/><br/>Object:<br/>";
    	info += ce.getRDFObject().toString();
    	return info;
    }
    
    public String genInfo(ServiceRequest sr) {
    	MessageContentSerializer serializer = (MessageContentSerializer) Activator.getBundleContext().getService(Activator.getBundleContext().getServiceReference(MessageContentSerializer.class.getName()));
    	return serializer.serialize(sr);
    }

    private javax.swing.JPanel RecordingPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton deleteButton1;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JButton editButton;
    private javax.swing.JButton editButton1;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JList inputsList;
    private javax.swing.JPanel inputsPanel;
    private JLabel inputsLabel;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton newButton;
    private javax.swing.JButton newButton1;
    private javax.swing.JButton okButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JList outputsList;
    private javax.swing.JPanel outputsPanel;
    private JLabel outputsLabel;
    private javax.swing.JButton recordButton;
    private javax.swing.JLabel recordingLabel;

}
