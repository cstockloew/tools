/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universAAL.tools.makrorecorder.gui;

import javax.swing.DefaultComboBoxModel;

import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.tools.makrorecorder.resourcebuilder.ContextEventBuilder;

/**
 *
 * @author mdjakow
 */
public class editContextEventDialog extends javax.swing.JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6992449957682711879L;
	private ContextEvent ce = null;
    
    public ContextEvent showDialog() {
        setVisible(true);
        return getContextEvent();
    }
    
    public ContextEvent getContextEvent() {
        return ce;
    }
    
    public editContextEventDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public void initData() {
    	((DefaultComboBoxModel)subjectComboBox.getModel()).removeAllElements();
    	for(Resource r : ContextEventBuilder.getSubjects()) {
    		((DefaultComboBoxModel)subjectComboBox.getModel()).addElement(r.toString());
    	}
    	((DefaultComboBoxModel)predicatesComboBox.getModel()).removeAllElements();
    	for(String s : ContextEventBuilder.getPredicates()) {
    		((DefaultComboBoxModel)predicatesComboBox.getModel()).addElement(s);
    	}
    }

    public editContextEventDialog(java.awt.Frame parent, boolean modal, ContextEvent contextEvent) {
        super(parent, modal);
        initComponents();
        ce = contextEvent;
        
        for(Resource r : ContextEventBuilder.getSubjects())
            subjectComboBox.addItem(r);
        
        for(String s : ContextEventBuilder.getPredicates())
            predicatesComboBox.addItem(s);
        
        subjectComboBox.setSelectedItem(ce.getRDFSubject());
        predicatesComboBox.setSelectedItem(ce.getRDFPredicate());
        objectTextField.setText(ce.getRDFObject().toString());
    }


    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        subjectComboBox = new javax.swing.JComboBox();
        predicatesComboBox = new javax.swing.JComboBox();
        subjectLabel = new javax.swing.JLabel();
        predicateLabel = new javax.swing.JLabel();
        objectLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        objectTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit ContextEvent");
        setBounds(new java.awt.Rectangle(300, 300, 800, 130));
        setMaximumSize(new java.awt.Dimension(2147483647, 130));
        setMinimumSize(new java.awt.Dimension(600, 130));
        setPreferredSize(new java.awt.Dimension(800, 130));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        subjectComboBox.setModel(new javax.swing.DefaultComboBoxModel());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        getContentPane().add(subjectComboBox, gridBagConstraints);

        predicatesComboBox.setModel(new javax.swing.DefaultComboBoxModel());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        getContentPane().add(predicatesComboBox, gridBagConstraints);

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
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        getContentPane().add(okButton, gridBagConstraints);

        cancelButton.setText("cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        getContentPane().add(cancelButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        getContentPane().add(objectTextField, gridBagConstraints);

        
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/universAAL.gif")).getImage());
        
        pack();
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if(subjectComboBox.getSelectedIndex()>-1) {
            Resource subject = (Resource) subjectComboBox.getSelectedItem();
            if(predicatesComboBox.getSelectedIndex()>-1) {
                String predicate = (String) predicatesComboBox.getSelectedItem();
                ce = new ContextEvent(subject, predicate);
                //ce.setRDFObject(objectTextField.getText());
            }
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(editContextEventDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(editContextEventDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(editContextEventDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(editContextEventDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                editContextEventDialog dialog = new editContextEventDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel objectLabel;
    private javax.swing.JTextField objectTextField;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel predicateLabel;
    private javax.swing.JComboBox predicatesComboBox;
    private javax.swing.JComboBox subjectComboBox;
    private javax.swing.JLabel subjectLabel;

}
