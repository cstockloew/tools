package org.universaal.tools.configurationExtractor.view.MainPanels;


import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.BigRootPanel;

/**
 * 
 * @author Ilja
 * This panel contains a XML related view (normal mode) of current configuration.
 */
@SuppressWarnings("serial")
public class CentralPanel extends JPanel{
	/**
	 * Constructor	
	 */
	public CentralPanel(){
		this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		init();
	}
	/*
	 * Helper method for initializing of central panel
	 */
	private void init(){
		JPanel cPanel=new JPanel();
		cPanel.setLayout( new BoxLayout(cPanel, BoxLayout.Y_AXIS) );
		JLabel jl =new JLabel("Configuration-Panel");
		jl.setBackground(Color.DARK_GRAY);
		cPanel.add(jl);
		BigRootPanel bigRootPanel= BigRootPanel.getInstance();
		cPanel.add(bigRootPanel);
		JScrollPane cP= new JScrollPane();
		cP.setViewportView(cPanel);
		this.add(cP);
	
	}
		
}
