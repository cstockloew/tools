package org.universaal.tools.configurationExtractor.view.Buttons;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.universaal.tools.configurationExtractor.controller.GuiControl;
import org.universaal.tools.configurationExtractor.utility.Constants;
import org.universaal.tools.configurationExtractor.view.MainPanels.MainWindow;

/**
 * 
 * @author Ilja
 * This class provides the button, which can change between two perspectives (Expert View and Normal View) 
 */
@SuppressWarnings("serial")
public class SwitchButton extends JButton{
private static boolean xmlPanelIsVisible=false;	
private MainWindow mainWindow;	
private ImageIcon expertModeIcon;
private ImageIcon normalModeIcon;
	
	/**
	 * Constructor method
	 * @param mainWindow
	 */
	public SwitchButton(MainWindow mainWindow){
		expertModeIcon = new ImageIcon( Constants.EXPERT_MODE_ICON );
		normalModeIcon = new ImageIcon( Constants.NORMAL_MODE_ICON );
		this.mainWindow=mainWindow;
		this.setIcon(expertModeIcon);
		this.setMargin(new java.awt.Insets(0, 0, 0, 0));
			
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanels();
				
			}			
		});
		String help = "Change to expert mode";
		this.setToolTipText( help );
	}

	/*
	 * Helper method for switching between perspectives
	 */
	private void switchPanels(){
		if(xmlPanelIsVisible){
			if(GuiControl.getInstance().xmlPanelChanged()){
				int ok = JOptionPane.showConfirmDialog(null,"Your changes will be lost if you will change to Normal_Mode!", 
	                    "Confirmation", JOptionPane.OK_CANCEL_OPTION);
				if (ok == JOptionPane.YES_OPTION){
					mainWindow.makeXmlPanelInVisible();
					xmlPanelIsVisible=false;
					this.setIcon(expertModeIcon);
					this.setMargin(new java.awt.Insets(0, 0, 0, 0));
					String help = "Change to expert mode";
					this.setToolTipText( help );
				}
			    }else{
				mainWindow.makeXmlPanelInVisible();
				xmlPanelIsVisible=false;
				this.setIcon(expertModeIcon);
				this.setMargin(new java.awt.Insets(0, 0, 0, 0));
				String help = "Change to expert mode";
				this.setToolTipText( help );
			    }
		}else{
			mainWindow.makeXmlPanelVisible();
			xmlPanelIsVisible=true;
			this.setIcon(normalModeIcon);
			this.setMargin(new java.awt.Insets(0, 0, 0, 0));
			String help = "Change to normal mode";
			this.setToolTipText( help );
		}
	}
	
}
