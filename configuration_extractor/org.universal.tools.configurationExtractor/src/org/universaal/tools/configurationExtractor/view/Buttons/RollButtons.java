package org.universaal.tools.configurationExtractor.view.Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.universaal.tools.configurationExtractor.utility.Constants;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.ParentPanel;

/**
 * 
 * @author Ilja
 * This class allowed to create two buttons to show and hide all elements of the parent panel. 
 */
@SuppressWarnings("serial")
public class RollButtons extends JPanel{
	
	
	ParentPanel parent;
	ImageIcon rollOpenIcon = new ImageIcon( Constants.ROLLOPEN_ELEMENTS_ICON );
	ImageIcon rollIcon = new ImageIcon( Constants.ROLL_ELEMENTS_ICON);

	/**
	 * Constructor method
	 */
	public RollButtons(ParentPanel parent){
		this.parent=parent;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JButton min= new JButton(rollOpenIcon);
		JButton max= new JButton(rollIcon);
		
		String helpMax = "Show elements!";
		max.setToolTipText(helpMax);
		
		String helpMin = "Hide elements!";
		min.setToolTipText(helpMin);
		
		max.setMargin(new java.awt.Insets(0, 0, 0, 0));
		min.setMargin(new java.awt.Insets(0, 0, 0, 0));
		min.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  	    rollAllElements();
				  }
				} );
		max.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
						  rollOpenElements(); 
				  }
				} );
		
		
		this.add(min);
		this.add(max);

	}
	
	/*
	 * hide all elements
	 */
	private void rollAllElements(){
		parent.rollElements();
		
	/*
	 * Show all elements	
	 */
	}
	private void rollOpenElements(){
		parent.rollOpenElements();

	}
	
}
