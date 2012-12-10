/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universAAL.tools.makrorecorder.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;

import org.universAAL.tools.makrorecorder.MakroRecorder;
import org.universAAL.tools.makrorecorder.pattern.Pattern;


/**
 *
 * @author mdjakow
 */
public class MainGUI extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4270287900663009508L;
	/**
     * Creates new form MainGUI
     */
    public MainGUI() {
    	MakroRecorder.loadFromFiles();
        initComponents();
        reloadPattern();
    }
    
    public void reloadPattern() {
    	((DefaultListModel)patternList.getModel()).clear();
    	for(String n : MakroRecorder.getAllPatternNames()) {
            ((DefaultListModel)patternList.getModel()).addElement(n);
        }
	}



    private void initComponents() {
    	
    	menuBar = new JMenuBar();
    	menuFile = new JMenu();
    	menuFile.setText("Datei");
    	menuItemNew = new JMenuItem();
    	menuItemNew.setText("Pattern erstellen");
    	menuItemNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				actionNew();
			}
		});
    	menuFile.add(menuItemNew);
    	menuItemEdit = new JMenuItem();
    	menuItemEdit.setText("Pattern bearbeiten");
    	menuItemEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEdit();				
			}
		});
    	menuItemEdit.setEnabled(false);
		menuFile.add(menuItemEdit);
		menuItemDelete = new JMenuItem();
		menuItemDelete.setText("Pattern löschen");
		menuItemDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionDelete();				
			}
		});
		menuItemDelete.setEnabled(false);
		menuFile.add(menuItemDelete);
		/*menuItemActivate = new JCheckBoxMenuItem();
    	menuItemActivate.setText("activate");
    	menuItemActivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if(menuItemActivate.isSelected()) {
            		actionActivate();
        		} else {
        			actionDeactivate();
        		}
            }
        });
    	menuFile.add(menuItemActivate);*/
    	menuFile.add(new JSeparator());
    	menuItemExit = new JMenuItem();
    	menuItemExit.setText("Beenden");
    	menuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	System.exit(0);
            }
        });
    	menuFile.add(menuItemExit);
    	menuBar.add(menuFile);
    	menuHelp = new JMenu();
    	menuHelp.setText("Hilfe");
    	menuItemAbout = new JMenuItem();
    	menuItemAbout.setText("Über");
    	menuHelp.add(menuItemAbout);
    	menuBar.add(menuHelp);
    	setJMenuBar(menuBar);
    	
    	
    	
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        patternList = new javax.swing.JList();
        nameTextArea = new javax.swing.JTextArea();
        descriptionTextArea = new javax.swing.JTextArea();
        editButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();

        
        class PatternListCellRenderer extends JLabel implements ListCellRenderer {
            /**
			 * 
			 */
			private static final long serialVersionUID = -5811359230143661865L;
			public PatternListCellRenderer() {
                setOpaque(true);
            }
            public Component getListCellRendererComponent(JList paramlist, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                setText(value.toString());
                if(cellHasFocus)
                	setBackground(paramlist.getSelectionBackground());
                else
                	setBackground(paramlist.getBackground());
                
                if (MakroRecorder.getPatternByName((String)value).isActive()) {
                    setIcon(new javax.swing.ImageIcon(getClass().getResource("/active.png")));
                } else {
                	setIcon(new javax.swing.ImageIcon(getClass().getResource("/inactive.png")));
                }
                	
                	paramlist.repaint();
	                return this;
            }
            
        }
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UniversAAL Makro Recorder");
        setBounds(new java.awt.Rectangle(100, 100, 600, 400));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(600, 400));
        setName("MainFrame");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(300, 300));

        patternList.setModel(new DefaultListModel());
        patternList.setCellRenderer(new PatternListCellRenderer());
        patternList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        patternList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                patternListValueChanged(evt);
            }
        });
        patternList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
               if (e.getClickCount() == 2)
            	   actionEdit();
               if (e.getButton() == MouseEvent.BUTTON3) {
            	   /*JPopupMenu pop = new JPopupMenu();
            	   pop.setLocation(e.getXOnScreen(), e.getYOnScreen());
            	   pop.add(menuItemActivate);
            	   pop.add(menuItemEdit);
            	   pop.add(menuItemDelete);
            	   pop.setVisible(true);*/
               }
            	   
            }
         });
        patternList.setBackground(this.getBackground());
        jScrollPane1.setViewportView(patternList);
        jScrollPane1.setBorder(BorderFactory.createTitledBorder("Pattern:"));
        jScrollPane1.setMinimumSize(new Dimension(300,200));
        jScrollPane1.setPreferredSize(new Dimension(300,200));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        nameTextArea.setText("");
        nameTextArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Name:"));
        nameTextArea.setBackground(this.getBackground());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        getContentPane().add(nameTextArea, gridBagConstraints);

        descriptionTextArea.setText("");
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        //descriptionLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        descriptionTextArea.setBackground(this.getBackground());
        jScrollPane2 = new JScrollPane(descriptionTextArea);
        jScrollPane2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Beschreibung:"));
        jScrollPane2.setMinimumSize(new Dimension(300,200));
        getContentPane().add(jScrollPane2, gridBagConstraints);

        editButton.setText("Bearbeiten");
        editButton.setEnabled(false);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionEdit();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        getContentPane().add(editButton, gridBagConstraints);

        newButton.setText("Erstellen");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionNew();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        getContentPane().add(newButton, gridBagConstraints);

        deleteButton.setText("Löschen");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionDelete();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(deleteButton, gridBagConstraints);

        jCheckBox1.setText("Active");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if(jCheckBox1.isSelected()) {
            		actionActivate();
        		} else {
        			actionDeactivate();
        		}
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jCheckBox1, gridBagConstraints);

        
        //ImageIcon icon = new ImageIcon(MeineKlasse.class.getResource("ico.gif"));
        //this.setIconImage(new ImageIcon("universAAL.gif").getImage());
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/universAAL.gif")).getImage());
        
        newButton.setVisible(false);
        //editButton.setVisible(false);
        //deleteButton.setVisible(false);
        
        pack();
    }

    private void actionNew() {//GEN-FIRST:event_newButtonActionPerformed
        //new PatternEditGUI().setVisible(true);
    	new Pattern().showGUI();
    }
    
    private void actionEdit() {//GEN-FIRST:event_editButtonActionPerformed
    	if(patternList.getSelectedIndex() > -1) {
    		String selected = (String)patternList.getSelectedValue();
    		Pattern selPattern = MakroRecorder.getPatternByName(selected);
    		selPattern.showGUI();
        	//new PatternEditGUI(selPattern).setVisible(true);
    	}
    }

    private void actionDelete() {//GEN-FIRST:event_deleteButtonActionPerformed
    	if(patternList.getSelectedIndex() > -1) {
	    	String selected = (String)patternList.getSelectedValue();
	    	MakroRecorder.removePattern(selected);
	        reloadPattern();
    	}
    }
    
    private void actionActivate() {
    	if(patternList.getSelectedIndex() > -1) {
	    	String selected = (String)patternList.getSelectedValue();
	        Pattern selPattern = MakroRecorder.getPatternByName(selected);
	        selPattern.activate();
	        selPattern.saveToFile();
	        //menuItemActivate.setSelected(true);
	        jCheckBox1.setSelected(true);
        }
    }
    
    private void actionDeactivate() {
    	if(patternList.getSelectedIndex() > -1) {
	    	String selected = (String)patternList.getSelectedValue();
	        Pattern selPattern = MakroRecorder.getPatternByName(selected);
	        selPattern.deactivate();
	        selPattern.saveToFile();
	        //menuItemActivate.setSelected(false);
	        jCheckBox1.setSelected(false);
        }
    }

    private void patternListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_patternListValueChanged
        if(patternList.getSelectedIndex() > -1) {
	    	String selected = (String)patternList.getSelectedValue();
	        Pattern selPattern = MakroRecorder.getPatternByName(selected);
	        //nameLabel.setText("Name: "+selPattern.getName());
	        nameTextArea.setText(selPattern.getName());
	        //descriptionLabel.setText("<html><body>Beschreibung:<br>"+selPattern.getDescription().replaceAll("\n", "<br>")+"</body></html>");
	        descriptionTextArea.setText(selPattern.getDescription());
	        jCheckBox1.setSelected(selPattern.isActive());
	        deleteButton.setEnabled(true);
	        editButton.setEnabled(true);
	        menuItemEdit.setEnabled(true);
	        menuItemDelete.setEnabled(true);
        } else {
        	//nameLabel.setText("Name: ");
	        //descriptionTextarea.setText("Beschreibung:\n");
	        nameTextArea.setText("");
	        descriptionTextArea.setText("");
	        jCheckBox1.setSelected(false);
	        deleteButton.setEnabled(false);
	        editButton.setEnabled(false);
	        menuItemEdit.setEnabled(false);
	        menuItemDelete.setEnabled(false);
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
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }

    private javax.swing.JButton deleteButton;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JButton editButton;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea nameTextArea;
    private javax.swing.JButton newButton;
    private javax.swing.JList patternList;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenuItem menuItemNew;
    private javax.swing.JMenuItem menuItemEdit;
    private javax.swing.JMenuItem menuItemDelete;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemAbout;
    

}
