package org.universaal.tools.configurationExtractor.view.Buttons;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.universaal.tools.configurationExtractor.model.xml.PanelWithElements;
import org.universaal.tools.configurationExtractor.utility.Constants;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.ParentPanel;

/**
 * 
 * @author Ilja
 * The instance of this class is a special button, which can remove a panel with all its elements.
 */
@SuppressWarnings("serial")
public class DeleteButtonForPanel extends JButton{
	private PanelWithElements panelWithElements= PanelWithElements.getInstance();
	private ParentPanel element;
	public DeleteButtonForPanel(ParentPanel element){
		this.element=element;
		ImageIcon del = new ImageIcon( Constants.DELETE_PANEL_ICON );
        this.setIcon(del);
        this.setMargin(new java.awt.Insets(0, 0, 0, 0));

		this.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  deleteParentPanel();
				  
			  }
			  } );
		String help = "Remove panel with all elements";
		this.setToolTipText(help);
	}
	/*
	 * Helper method to delete a parent panel
	 */
	private void deleteParentPanel(){
		element.setVisible(false);
		panelWithElements.deleteParentPanel(element);
	}
}