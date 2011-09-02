/*package view.Buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import utility.Constants;
import view.LowLevelPanels.ParentPanel;


@SuppressWarnings("serial")
public class RollButton extends JButton{
	
	
	ParentPanel parent;
	Boolean rollButton;

//	ImageIcon rollOpenIcon = new ImageIcon( "C:/Users/Ilja/workspace/CE/icons/rollopen.gif" );
//	ImageIcon rollIcon = new ImageIcon( "C:/Users/Ilja/workspace/CE/icons/roll.gif" );
	ImageIcon rollOpenIcon = new ImageIcon( Constants.ROLLOPEN_ELEMENTS_ICON );
	ImageIcon rollIcon = new ImageIcon( Constants.ROLL_ELEMENTS_ICON);
//	ImageIcon rollOpenIcon = new ImageIcon( "icons/rollopen.gif" );
//	ImageIcon rollIcon = new ImageIcon( "icons/roll.gif" );
	
	
	
	public RollButton(ParentPanel parent){
		this.parent=parent;
		rollButton=true;
		
		this.setIcon(rollOpenIcon);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(35, 15));
		this.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  if(!parentIsEmpty()) {
					  if(rollButton){
					    rollAllElements();
					  }else{
						  rollOpenElements(); 
					  }
				  }
				  }
				} );
	}
	private boolean parentIsEmpty(){
		return !(this.parent.getElementsList().size()>0);
	}
	void changeRollButton(){
		if(rollButton){
			rollButton=false;

			this.setIcon(rollIcon);
		}else{
			rollButton=true;

			this.setIcon(rollOpenIcon);
		}
	}
	
	void rollAllElements(){
		parent.rollElements();
		changeRollButton();
		
	}
	void rollOpenElements(){
		parent.rollOpenElements();
		changeRollButton();
		
	}
	
}
*/