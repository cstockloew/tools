package view.Buttons;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.xml.PanelWithElements;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;


import utility.Constants;
import view.LowLevelPanels.BigRootPanel;
import view.LowLevelPanels.ParentPanel;

@SuppressWarnings("serial")
public class DeleteButtonForPanel extends JButton{
	private PanelWithElements panelWithElements= PanelWithElements.getInstance();
	private ParentPanel element;
	public DeleteButtonForPanel(ParentPanel element){
		this.element=element;
		

		
//		ImageIcon del = new ImageIcon( "C:/Users/Ilja/workspace/CE/icons/DeleteIcon.gif" );
      ImageIcon del = new ImageIcon( Constants.DELETE_PANEL_ICON );
 //     ImageIcon del = new ImageIcon( "icons/DeleteIcon.gif" );
        this.setIcon(del);
        this.setMargin(new java.awt.Insets(0, 0, 0, 0));


////	
//
		this.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  deleteElement();
				  
			  }
			  } );
		String help = "Remove panel with all elements";
		this.setToolTipText(help);
	}
	void deleteElement(){
		element.setVisible(false);
		panelWithElements.deleteParentPanel(element);
	}
}