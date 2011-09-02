package view.Buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import model.xml.PanelWithElements;

import utility.Constants;
import view.LowLevelPanels.BigRootPanel;
import view.LowLevelPanels.ParentPanel;

@SuppressWarnings("serial")
public class NavigationButtonForBigPanels extends JPanel {
	JButton top;
	JButton down;
	PanelWithElements parent = PanelWithElements.getInstance();
	ParentPanel element;
	public NavigationButtonForBigPanels(ParentPanel element){
//		this.parent=parent;
		this.element=element;
		this.setLayout( new BoxLayout(this, BoxLayout.X_AXIS) );
//		this.setBorder(new EtchedBorder(Color.red,  Color.green));
	//	ImageIcon upIcon = new ImageIcon( "C:/Users/Ilja/workspace/CE/icons/ArrowUpIcon.gif" );
		ImageIcon upIcon = new ImageIcon( Constants.ARROW_UP_PANEL_ICON );
		//ImageIcon upIcon = new ImageIcon( "icons/ArrowUpIcon.gif" );	
		top=new JButton(upIcon);
		top.setMargin(new java.awt.Insets(0, 0, 0, 0));
		top.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  	  changeWithTop();
				  }
				} );
		
	//	ImageIcon downIcon = new ImageIcon( "C:/Users/Ilja/workspace/CE/icons/ArrowDownIcon.gif" );	

		ImageIcon downIcon = new ImageIcon( Constants.ARROW_DOWN_PANEL_ICON );
	//	ImageIcon downIcon = new ImageIcon( "icons/ArrowDownIcon.gif" );	
		down=new JButton(downIcon);
		down.setMargin(new java.awt.Insets(0, 0, 0, 0));
		down.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  changeWithDown();
				  }
				} );
		this.add(top);
		this.add(down);
		
		String helpTop = "Change current panel with panel over!";
		top.setToolTipText(helpTop);
		
		String helpDown = "Change current panel with panel down!";
		down.setToolTipText(helpDown);
		
	}
	
	private void changeWithTop(){
		int indexOfTop=parent.getParentsList().indexOf(element)-1;
		if(indexOfTop >= 0){
			parent.changeTwoParentPanels(element,parent.getParentsList().get(indexOfTop));
		}	
	}
	
	private void changeWithDown(){
		int indexOfDown=parent.getParentsList().indexOf(element)+1;
		if(indexOfDown<parent.getParentsList().size()){
			parent.changeTwoParentPanels(element,parent.getParentsList().get(indexOfDown));
		}
	}

}

