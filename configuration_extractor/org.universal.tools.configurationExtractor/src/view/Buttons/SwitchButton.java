package view.Buttons;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import controller.GuiControl;
import utility.Constants;
import view.MainPanels.MainWindow;

@SuppressWarnings("serial")
public class SwitchButton extends JButton{
private static boolean xmlPanelIsVisible=false;	
private MainWindow mainWindow;	
	
	public SwitchButton(MainWindow mainWindow){
		super("expert_mode!");
		this.mainWindow=mainWindow;
//		ImageIcon expertIcon = new ImageIcon( Constants.EXPERT_ICON );
//		this.setIcon(expertIcon);
//		this.setMargin(new java.awt.Insets(0, 0, 0, 0));
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanels();
				
			}
		});
		String help = "Change to expert mode";
		this.setToolTipText( help );
	}

	
	private void switchPanels(){
		if(xmlPanelIsVisible){
			if(GuiControl.getInstance().xmlPanelChanged()){
				int ok = JOptionPane.showConfirmDialog(null,"Your changes will be lost if you will change to Normal_Mode!", 
	                    "Confirmation", JOptionPane.OK_CANCEL_OPTION);
				if (ok == JOptionPane.YES_OPTION){
					mainWindow.makeXmlPanelUnVisible();
					xmlPanelIsVisible=false;
					this.setText("expert_mode!");
//					ImageIcon expertIcon = new ImageIcon( Constants.EXPERT_ICON );
//					this.setIcon(expertIcon);
//					this.setMargin(new java.awt.Insets(0, 0, 0, 0));
					String help = "Change to expert mode";
					this.setToolTipText( help );
				}
			}else{
				mainWindow.makeXmlPanelUnVisible();
				xmlPanelIsVisible=false;
				this.setText("expert_mode!");
//				ImageIcon expertIcon = new ImageIcon( Constants.EXPERT_ICON );
//				this.setIcon(expertIcon);
//				this.setMargin(new java.awt.Insets(0, 0, 0, 0));
			}
		}else{
			mainWindow.makeXmlPanelVisible();
			xmlPanelIsVisible=true;
			this.setText("normal_mode!");
//			ImageIcon normalIcon = new ImageIcon( Constants.NORMAL_ICON );
//			this.setIcon(normalIcon);
//			this.setMargin(new java.awt.Insets(0, 0, 0, 0));
			String help = "Change to normal mode";
			this.setToolTipText( help );
		}
	}
	
}
