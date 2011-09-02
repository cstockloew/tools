package view.MainPanels;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import utility.Constants;

public class EndWindow extends JFrame {
	
	MainWindow mainWindow;
	
	
	public EndWindow(MainWindow mainWindow){
		this.mainWindow=mainWindow;
		mainWindow.setVisible(false);
		
		Dimension d = new Dimension(300,300);
    	this.setSize(d);
		Container pane=this.getContentPane();
		JLabel jl=new JLabel("Thank you for using \"Configuration Extractor\"!");
		
		JPanel panel=new JPanel();
		panel.add(jl,BorderLayout.NORTH);
		
		ImageIcon team=new ImageIcon( Constants.TEAM );
		panel.add(new JLabel(team),BorderLayout.SOUTH);
		pane.add(panel);
		
		this.setVisible(true);
		
	}
	
}
