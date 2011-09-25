package org.universaal.tools.configurationExtractor.view.MainPanels;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * 
 * @author Ilja
 * The goodbye frame of CE 
 */
@SuppressWarnings("serial")
public class EndWindow extends JFrame {
	
	private MainWindow mainWindow;
	/**
	 * Constructor
	 * @param mainWindow
	 */
	public EndWindow(MainWindow mainWindow){
		super.setTitle("Goodbye!");
		this.mainWindow=mainWindow;
		mainWindow.setVisible(false);
		Dimension d = new Dimension(300,100);
    	this.setSize(d);
		Container pane=this.getContentPane();
		JLabel jl=new JLabel("Thank you for using \"Configuration Extractor\"!");
		JPanel panel=new JPanel();
		panel.add(jl,BorderLayout.NORTH);
		pane.add(panel);
		this.setVisible(true);
		
	}
	
}
