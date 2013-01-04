package org.universAAL.tools.makrorecorder.gui.patternedit;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.universAAL.tools.makrorecorder.MakroRecorder;
import org.universAAL.tools.makrorecorder.pattern.Pattern;

/**
 *
 * @author mdjakow
 */
public class PatternEditGUI extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private javax.swing.JPanel RecordingPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton recordButton;
    private javax.swing.JLabel recordingLabel;
    private ResourcePanel inputsPanel;
    private ResourcePanel outputsPanel;
	
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
        
        this.pattern = pattern;
        initComponents();
        readPattern();        
    }
    
      
    private void readPattern() {
        nameTextField.setText(pattern.getName());
        descriptionTextArea.setText(pattern.getDescription());
        readInputs();
        readOutputs();
    }
    
    public void readInputs() {
    	inputsPanel.reload();
    }
    
    public void readOutputs() {
    	outputsPanel.reload();
    }
    
    private void savePattern() {
    	if(nameTextField.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(this, "Please enter Name", "info", JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	if(MakroRecorder.getAllPatternNames().contains(nameTextField.getText())) {
    		if (JOptionPane.showConfirmDialog(
    				null, 
    				"Pattern '"+nameTextField.getText()+"' already exist.\nOverwrite?", 
    				"Save pattern", 
    				JOptionPane.YES_NO_OPTION
    				) != JOptionPane.YES_OPTION)
    			return;
    	}
        pattern.setName(nameTextField.getText());
        pattern.setDescription(descriptionTextArea.getText());
        if(MakroRecorder.savePattern(pattern)) {
        	this.dispose();
        }
    }


    private void initComponents() {
    	addWindowListener(new WindowAdapter() {
    	      public void windowClosing(WindowEvent e) {
    	    	  pattern.stopRecording();
    	      }
    	    });
    	
        java.awt.GridBagConstraints gridBagConstraints;
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Pattern");
        setBounds(new java.awt.Rectangle(200, 200, 1000, 700));
        setMinimumSize(new java.awt.Dimension(800, 700));
        setPreferredSize(new java.awt.Dimension(1000, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());
        
        inputsPanel = new ResourcePanel(pattern.getInput(), "Inputs:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(inputsPanel, gridBagConstraints);
        
        outputsPanel = new ResourcePanel(pattern.getOutput(), "Outputs:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(outputsPanel, gridBagConstraints);

        infoPanel = new JPanel();
        infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Pattern Information"));
        infoPanel.setLayout(new java.awt.GridBagLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(new JLabel("Name:"), gridBagConstraints);
        
        nameTextField = new JTextField();
        nameTextField.setText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(nameTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(new JLabel("Description:"), gridBagConstraints);

        descriptionTextArea = new JTextArea();
        descriptionTextArea.setColumns(20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(new JScrollPane(descriptionTextArea), gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(infoPanel, gridBagConstraints);

        controlPanel = new JPanel();
        controlPanel.setLayout(new java.awt.GridBagLayout());

        RecordingPanel = new JPanel();
        RecordingPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Recording"));
        RecordingPanel.setLayout(new java.awt.GridBagLayout());

        recordButton = new JButton();
        recordButton.setForeground(new java.awt.Color(51, 102, 0));
        recordButton.setText("Start");
        recordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if(recordButton.getForeground().equals(new Color(128, 25, 0))) {
                    recordButton.setForeground(new Color(25, 128, 0));
                    recordButton.setText("Start");
                    pattern.stopRecording();
                    recordingLabel.setText("Recording stoped");
                } else {
                    recordButton.setForeground(new Color(128, 25, 0));
                    recordButton.setText("Stop");
                    pattern.startRecording();
                    recordingLabel.setText("Recording...");
                }
            }
        });
        RecordingPanel.add(recordButton, new java.awt.GridBagConstraints());

        recordingLabel = new JLabel();
        recordingLabel.setText("Recording stoped");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        RecordingPanel.add(recordingLabel, gridBagConstraints);


        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 0);
        RecordingPanel.add(new JLabel("Status:"), gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        controlPanel.add(RecordingPanel, gridBagConstraints);

        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	pattern.stopRecording();
            	dispose();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        controlPanel.add(cancelButton, gridBagConstraints);

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
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
        controlPanel.add(saveButton, gridBagConstraints);
        
        resetButton = new JButton();
        resetButton.setText("Reset");
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

        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/universAAL.gif")).getImage());
        
        pack();
    }
}
