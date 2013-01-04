package org.universAAL.tools.makrorecorder.gui;

import org.universAAL.middleware.context.ContextEvent;

/**
 *
 * @author mdjakow
 */
public class editContextEventGUI extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3462404445311835668L;
	
	
	private ContextEvent ce = null;
    /**
     * Creates new form editContextEventGUI
     */
    public editContextEventGUI(ContextEvent contextEvent) {
        initComponents();
        ce = contextEvent;
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        subjectComboBox = new javax.swing.JComboBox();
        predicatesComboBox = new javax.swing.JComboBox();
        objectComboBox = new javax.swing.JComboBox();
        subjectLabel = new javax.swing.JLabel();
        predicateLabel = new javax.swing.JLabel();
        objectLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit ContextEvent");
        setBounds(new java.awt.Rectangle(300, 300, 800, 130));
        setMaximumSize(new java.awt.Dimension(2147483647, 130));
        setMinimumSize(new java.awt.Dimension(500, 131));
        setPreferredSize(new java.awt.Dimension(800, 130));
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        subjectComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        getContentPane().add(subjectComboBox, gridBagConstraints);

        predicatesComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {  }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        getContentPane().add(predicatesComboBox, gridBagConstraints);

        objectComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {  }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        getContentPane().add(objectComboBox, gridBagConstraints);

        subjectLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subjectLabel.setText("Subject");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        getContentPane().add(subjectLabel, gridBagConstraints);

        predicateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        predicateLabel.setText("Predicate");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        getContentPane().add(predicateLabel, gridBagConstraints);

        objectLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        objectLabel.setText("Object");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        getContentPane().add(objectLabel, gridBagConstraints);

        okButton.setText("ok");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        getContentPane().add(okButton, gridBagConstraints);

        cancelButton.setText("cancel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        getContentPane().add(cancelButton, gridBagConstraints);

        pack();
    }

    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox objectComboBox;
    private javax.swing.JLabel objectLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel predicateLabel;
    private javax.swing.JComboBox predicatesComboBox;
    private javax.swing.JComboBox subjectComboBox;
    private javax.swing.JLabel subjectLabel;

}
