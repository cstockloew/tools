/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.tools.makrorecorder.swinggui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.universAAL.tools.makrorecorder.Activator;
import org.universAAL.tools.makrorecorder.makrorecorder.Pattern;
import org.universAAL.tools.makrorecorder.makrorecorder.ServiceCallHandler;

/**
 * 
 * @author Alexander Marinc, Mark Prediger
 *
 */
public class MainFrame extends JFrame implements ListSelectionListener, MouseListener, ServiceCallHandler {
	
	// Menubar is not working with Sensekit o:O
	/*
	 * JMenuBar menuBar; JMenu menu; JMenuItem playMenuItem; JMenuItem recordMenuItem; JMenuItem editMenuItem; JMenuItem
	 * deleteMenuItem; JCheckBoxMenuItem keepTimeItem;
	 */
	private static final long serialVersionUID = -1020520993429013831L;
	
	JButton recordButton;
	JButton playButton;
	JButton deleteButton;
	JCheckBox keepTimeBox;
	JTable table;
	
	DefaultTableModel model;
	
	ImageIcon logo;
	ImageIcon recordIconBig;
	ImageIcon playIconBig;
	ImageIcon recordIconSmall;
	ImageIcon playIconSmall;
	ImageIcon deleteIconSmall;
	ImageIcon deleteIconBig;
	
	boolean recording = false;
	
	private AbstractAction startAction;
	private AbstractAction playAction;
	private AbstractAction editAction;
	private AbstractAction deleteAction;
	
	private JFrame playProgressFrame;
	private JProgressBar playProgressBar;
	
	public MainFrame() {
		this.setBounds(0, 0, 300, 450);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout(10, 10));
		
		// reads all the images.
		// big images are for the main Frame
		logo = new ImageIcon(MainFrame.class.getClassLoader().getResource("images/uaal.gif"));
		recordIconBig = new ImageIcon(MainFrame.class.getClassLoader().getResource("images/RecordButton.png"));
		playIconBig = new ImageIcon(MainFrame.class.getClassLoader().getResource("images/StartButton.png"));
		deleteIconBig = new ImageIcon(MainFrame.class.getClassLoader().getResource("images/Delete.png"));
		
		// small images are for the Menubar
		recordIconSmall = new ImageIcon(MainFrame.class.getClassLoader().getResource("images/RecordButton.png"));
		playIconSmall = new ImageIcon(MainFrame.class.getClassLoader().getResource("images/StartButton.png"));
		deleteIconSmall = new ImageIcon(MainFrame.class.getClassLoader().getResource("images/Delete.png"));
		
		Image icon = logo.getImage();
		ImageIcon newlogo = new ImageIcon(icon.getScaledInstance(200, 100, Image.SCALE_SMOOTH));
		JLabel logoLabel = new JLabel(newlogo);
		// logoLabel.setBounds(50, 10, 200, 100);
		logoLabel.setPreferredSize(new Dimension(200, 100));
		
		icon = recordIconBig.getImage();
		recordIconBig = new ImageIcon(icon.getScaledInstance(70, 70, Image.SCALE_SMOOTH));
		
		icon = playIconBig.getImage();
		playIconBig = new ImageIcon(icon.getScaledInstance(70, 70, Image.SCALE_SMOOTH));
		
		icon = recordIconSmall.getImage();
		recordIconSmall = new ImageIcon(icon.getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		
		icon = playIconSmall.getImage();
		playIconSmall = new ImageIcon(icon.getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		
		icon = deleteIconSmall.getImage();
		deleteIconSmall = new ImageIcon(icon.getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		
		icon = deleteIconBig.getImage();
		deleteIconBig = new ImageIcon(icon.getScaledInstance(70, 70, Image.SCALE_SMOOTH));
		
		/*
		 * menuBar = new JMenuBar(); menu = new JMenu("File"); menu.setMnemonic(KeyEvent.VK_F); menuBar.add(menu);
		 * 
		 * // keepTimeItem = new JCheckBoxMenuItem(); // keepTimeItem.setText("Keep Time dependancie"); //
		 * keepTimeItem.addItemListener(this); // keepTimeItem.setMnemonic(KeyEvent.VK_K); // menu.add(keepTimeItem);
		 * 
		 * 
		 * playMenuItem = new JMenuItem(); playMenuItem.setAction(playRecord()); playMenuItem.setText("Play");
		 * playMenuItem.setMnemonic(KeyEvent.VK_P); playMenuItem.setEnabled(false); playMenuItem.setIcon(playIconSmall);
		 * menu.add(playMenuItem);
		 * 
		 * recordMenuItem = new JMenuItem(); recordMenuItem.setAction(startRecording());
		 * recordMenuItem.setText("Record"); recordMenuItem.setMnemonic(KeyEvent.VK_R); recordMenuItem.setEnabled(true);
		 * recordMenuItem.setIcon(recordIconSmall); menu.add(recordMenuItem);
		 * 
		 * editMenuItem = new JMenuItem(); editMenuItem.setAction(editRecord()); editMenuItem.setText("Edit");
		 * editMenuItem.setMnemonic(KeyEvent.VK_E); editMenuItem.setEnabled(false); menu.add(editMenuItem);
		 * 
		 * deleteMenuItem = new JMenuItem(); deleteMenuItem.setAction(deleteRecord()); deleteMenuItem.setText("Delete");
		 * deleteMenuItem.setMnemonic(KeyEvent.VK_D); deleteMenuItem.setEnabled(false);
		 * deleteMenuItem.setIcon(deleteIconSmall); menu.add(deleteMenuItem);
		 */

		recordButton = new JButton();
		// recordButton.setBounds(20, 130, 75, 75);
		recordButton.setPreferredSize(new Dimension(75, 75));
		recordButton.setAction(startRecording());
		recordButton.setEnabled(true);
		recordButton.setIcon(recordIconBig);
		
		playButton = new JButton();
		// playButton.setBounds(170, 130, 75, 75);
		playButton.setPreferredSize(new Dimension(75, 75));
		playButton.setAction(playRecord());
		playButton.setEnabled(false);
		playButton.setIcon(playIconBig);
		
		deleteButton = new JButton();
		deleteButton.setPreferredSize(new Dimension(75, 75));
		deleteButton.setAction(deleteRecord());
		deleteButton.setEnabled(false);
		deleteButton.setIcon(deleteIconBig);
		
		// JButton fakeeditButton = new JButton();
		// fakeeditButton.setAction(editRecord());
		
		keepTimeBox = new JCheckBox();
		keepTimeBox.setText("Keep the time dependancie of reqeusts");
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});
		
		table = new JTable(fillTable()) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.getColumn("Active").setCellRenderer(table.getDefaultRenderer(Boolean.class));
		table.getColumn("Active").setCellEditor(table.getDefaultEditor(Boolean.class));
		
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(true);
		// table.setCellSelectionEnabled(false);
		
		ListSelectionModel selectionModel;
		selectionModel = table.getSelectionModel();
		selectionModel.addListSelectionListener(this);
		table.setSelectionModel(selectionModel);
		table.addMouseListener(this);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(270, 200));
		
		JPanel buttonPanel = new JPanel(new BorderLayout(10, 10));
		buttonPanel.add(recordButton, BorderLayout.WEST);
		buttonPanel.add(playButton, BorderLayout.CENTER);
		buttonPanel.add(deleteButton, BorderLayout.EAST);
		
		JPanel northpanel = new JPanel(new BorderLayout());
		northpanel.add(logoLabel, BorderLayout.NORTH);
		northpanel.add(keepTimeBox, BorderLayout.PAGE_END);
		
		this.getContentPane().add(buttonPanel, BorderLayout.CENTER);
		this.getContentPane().add(scrollPane, BorderLayout.SOUTH);
		this.getContentPane().add(northpanel, BorderLayout.NORTH);
		
		// this.setJMenuBar(menuBar);
		
		this.setVisible(true);
		
		playProgressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
		playProgressBar.setStringPainted(true);
		playProgressFrame = new JFrame();
		playProgressFrame.add(playProgressBar);
		playProgressFrame.setSize(500, 90);
	}
	
	/**
	 * invoked on delete
	 **/
	private AbstractAction deleteRecord() {
		if (deleteAction == null) {
			deleteAction = new AbstractAction() {
				
				public void actionPerformed(ActionEvent e) {
					if (table.getSelectedRow() != -1) {
						String name = (String) table.getModel().getValueAt(table.getSelectedRow(), 1);
						Activator.resourceOrginazer.deletePattern(Activator.resourceOrginazer.getPatternByName(name));
						model.removeRow(table.getSelectedRow());
						table.repaint();
					}
					
				}
			};
		}
		return deleteAction;
	}
	
	/**
	 * invoked on record button press
	 **/
	public AbstractAction startRecording() {
		if (startAction == null) {
			startAction = new AbstractAction() {
				
				public void actionPerformed(ActionEvent e) {
					String eingabe = JOptionPane.showInputDialog(null, "Geben Sie einen Namen ein", "Name?",
							JOptionPane.PLAIN_MESSAGE);
					if (eingabe != null) {
						Activator.myContextSubscriber.startRecording();
						recording = true;
						RecordFrame recordFrame = new RecordFrame(eingabe, Activator.gui);
						recordFrame.setVisible(true);
						recordFrame.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosing(WindowEvent e) {
								if (Activator.gui.recording) {
									Activator.gui.recording = false;
									Activator.gui.setEnabled();
								}
								super.windowClosing(e);
							}
						});
						
						setDisabled();
					}
				}
			};
		}
		return startAction;
	}
	
	public boolean keepTimeDependencies() {
		return keepTimeBox.isSelected();
	}
	
	/**
	 * invoked on play button press
	 **/
	public AbstractAction playRecord() {
		if (playAction == null) {
			playAction = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					if (table.getSelectedRow() != -1) {
						String name = (String) table.getModel().getValueAt(table.getSelectedRow(), 1);
						
						playAction.setEnabled(false);
						progressChanged(0, 0);
						Activator.myServiceProvider.startCallingServices(name, keepTimeDependencies(), MainFrame.this);
					}
				}
			};
		}
		return playAction;
	}
	
	/**
	 * invoked on double click of a Record
	 **/
	public AbstractAction editRecord() {
		if (editAction == null) {
			editAction = new AbstractAction() {
				
				public void actionPerformed(ActionEvent e) {
					String patternName = (String) model.getValueAt(table.getSelectedRow(), 1);
					Pattern pattern = Activator.resourceOrginazer.getPatternByName(patternName);
					RecordFrame frame = new RecordFrame(pattern, Activator.gui);
					frame.setVisible(true);
					table.clearSelection();
				}
			};
		}
		return editAction;
		
	}
	
	/**
	 * is invoked after a new Record is added and on programm start
	 **/
	
	public TableModel fillTable() {
		
		Vector<String> Names = Activator.resourceOrginazer.getPatternNames();
		Vector<String> Dates = Activator.resourceOrginazer.getPatternDates();
		Vector<Boolean> booleans = Activator.resourceOrginazer.getState();
		String[] colNames = { "Active", "Name", "Date" };
		model = new DefaultTableModel(colNames, 0);
		
		for (int i = 0; i < Names.size(); i++) {
			Object[] data = { booleans.get(i), Names.get(i), Dates.get(i) };
			model.addRow(data);
		}
		
		return model;
	}
	
	/**
	 * invoked on change of the pattern-table
	 **/
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			setEnabled();
			
			Object object = table.getSelectedColumn();
			
			if (table.getSelectedColumn() != -1 && table.getSelectedRow() != -1 && table.getSelectedColumn() == 0) {
				String patternName = (String) model.getValueAt(table.getSelectedRow(), 1);
				
				Pattern pattern = Activator.resourceOrginazer.getPatternByName(patternName);
				table.setValueAt(pattern.toggleActive(), table.getSelectedRow(), table.getSelectedColumn());
				
				table.clearSelection();
				table.repaint();
			}
		}
	}
	
	/**
	 * adds new pattern to table and repaints it
	 **/
	public void addToTable(Pattern pattern) {
		Object[] data = { pattern.getActive(), pattern.getName(), pattern.getDate() };
		model.addRow(data);
		table.repaint();
	}
	
	/**
	 * triggers editaction on doublelick
	 **/
	public void mouseClicked(MouseEvent e) {
		
		if (e.getClickCount() == 2) {
			ActionEvent event = new ActionEvent(e.getSource(), e.getID(), "editAction");
			editRecord();
			editAction.actionPerformed(event);
		}
	}
	
	public void mouseEntered(MouseEvent e) {
	}
	
	public void mouseExited(MouseEvent e) {
	}
	
	public void mousePressed(MouseEvent e) {
	}
	
	public void mouseReleased(MouseEvent e) {
	}
	
	public void setEnabled() {
		playButton.setEnabled(true);
		// playMenuItem.setEnabled(true);
		
		recordButton.setEnabled(true);
		// recordMenuItem.setEnabled(true);
		
		deleteButton.setEnabled(true);
		// deleteMenuItem.setEnabled(true);
		// editMenuItem.setEnabled(true);
	}
	
	public void setDisabled() {
		
		playButton.setEnabled(false);
		// playMenuItem.setEnabled(false);
		
		recordButton.setEnabled(false);
		// recordMenuItem.setEnabled(false);
		
		deleteButton.setEnabled(false);
		// deleteMenuItem.setEnabled(false);
		// editMenuItem.setEnabled(false);
	}
	
	public void callingCanceled() {
		playAction.setEnabled(true);
		playProgressFrame.setVisible(false);
	}
	
	public void callingFinished(boolean result) {
		playAction.setEnabled(true);
		playProgressFrame.setVisible(false);
	}
	
	public void progressChanged(float progress, long time) {
		if (keepTimeDependencies()) {
			playProgressBar.setValue((int) (progress * 100));
			playProgressBar.setString(String.valueOf(time) + " ms");
			playProgressFrame.setVisible(true);
		}
	}
}
