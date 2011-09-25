package org.universaal.tools.configurationExtractor.view.Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.universaal.tools.configurationExtractor.model.xml.PanelWithElements;
import org.universaal.tools.configurationExtractor.utility.Constants;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.BigPanel;
/**
 * 
 * @author Ilja
 * The instance of this class is a special button, which can create new panel element.
 */
@SuppressWarnings("serial")
public class AddPanelButton extends JButton{
	public static int index=1;
	
	/**
	 * Constructor method, which include a listener
	 */
	public AddPanelButton(){
	//	super("addPanel");		
		ImageIcon addPanelButton = new ImageIcon( Constants.ADD_PANEL_ICON);
		this.setIcon(addPanelButton);
		this.setMargin(new java.awt.Insets(0, 0, 0, 0));
		this.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  addNewPanel();
				  
			  }
			  } );
		
		String help = "Add a new Panel";
		this.setToolTipText( help );
		
	}
	/*
	 * Helper method to create a new panel 
	 */
	private void addNewPanel(){
		BigPanel bg=new BigPanel("title"+index);
		PanelWithElements.getInstance().addParentPanel(bg);
		index++;
	}
	
	/**
	 * Reset index to 1
	 */
	public void resetIndex(){
		index=1;
	}
}
