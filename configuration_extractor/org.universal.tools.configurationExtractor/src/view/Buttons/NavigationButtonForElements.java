package view.Buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import utility.Constants;
import view.Elements.BigElement;
import view.Elements.ListG;
import view.LowLevelPanels.ParentPanel;

@SuppressWarnings("serial")
public class NavigationButtonForElements extends JPanel {
	JButton top;
	JButton down;
	ParentPanel parent;
	BigElement element;
	public NavigationButtonForElements(ParentPanel parent , BigElement element){
		this.parent=parent;
		this.element=element;
		this.setLayout( new BoxLayout(this, BoxLayout.X_AXIS) );
//		this.setBorder(new EtchedBorder(Color.red,  Color.green));
	//	ImageIcon upIcon = new ImageIcon( "C:/Users/Ilja/workspace/CE/icons/pfeil_hoch.gif" );
		ImageIcon upIcon = new ImageIcon( Constants.ARROW_UP_ELEMENT_ICON );
	//	ImageIcon upIcon = new ImageIcon( "icons/pfeil_hoch.gif" );
		top=new JButton(upIcon);
		top.setMargin(new java.awt.Insets(0, 0, 0, 0));
		top.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  	  changeWithTop();
				  }
				} );
		
		
	//	ImageIcon downIcon = new ImageIcon( "C:/Users/Ilja/workspace/CE/icons/pfeil_runter.gif" );
		ImageIcon downIcon = new ImageIcon( Constants.ARROW_DOWN_ELEMENT_ICON );
	//	ImageIcon downIcon = new ImageIcon( "icons/pfeil_runter.gif" );
		down=new JButton(downIcon);
		down.setMargin(new java.awt.Insets(0, 0, 0, 0));
		down.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  changeWithDown();
				  }
				} );
		this.add(top);
		this.add(down);
		
		String helpTop = "Change current element with element over!";
		top.setToolTipText(helpTop);
		
		String helpDown = "Change current element with element down!";
		down.setToolTipText(helpDown);
	}
	
	private void changeWithTop(){
		System.out.println(parent.getTitle());
		System.out.println(parent.getElementsList().size());

		
		int indexOfTop=parent.getElementsList().indexOf(element)-1;
		if(indexOfTop >= 0 && !(parent.getElementsList().get(indexOfTop) instanceof ListG)){
			parent.changeTwoElements(element,parent.getElementsList().get(indexOfTop));
		}else{
			JOptionPane.showMessageDialog( null, "Sorry, but \"list\" has to be always on the top from \"listpanel\"" );
		}
	}
	
	private void changeWithDown(){
		System.out.println("size of elements is:"+parent.getElementsList().size());
		System.out.println("parent is:"+parent.getTitle());
		int indexOfDown=parent.getElementsList().indexOf(element)+1;

		if(indexOfDown<parent.getElementsList().size()){
			parent.changeTwoElements(element,parent.getElementsList().get(indexOfDown));
		}
	}
	public void setParent(ParentPanel p){
		this.parent=p;
	}

}
