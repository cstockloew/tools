package view.Buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import utility.Constants;
import view.LowLevelPanels.ParentPanel;


@SuppressWarnings("serial")
public class RollButtons extends JPanel{
	
	
	ParentPanel parent;
//	ImageIcon rollOpenIcon = new ImageIcon( "C:/Users/Ilja/workspace/CE/icons/rollopen.gif" );
//	ImageIcon rollIcon = new ImageIcon( "C:/Users/Ilja/workspace/CE/icons/roll.gif" );
	ImageIcon rollOpenIcon = new ImageIcon( Constants.ROLLOPEN_ELEMENTS_ICON );
	ImageIcon rollIcon = new ImageIcon( Constants.ROLL_ELEMENTS_ICON);
//	ImageIcon rollOpenIcon = new ImageIcon( "icons/rollopen.gif" );
//	ImageIcon rollIcon = new ImageIcon( "icons/roll.gif" );
	
	
	
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
	void rollAllElements(){
		parent.rollElements();
		
	}
	void rollOpenElements(){
		parent.rollOpenElements();

	}
	
}
