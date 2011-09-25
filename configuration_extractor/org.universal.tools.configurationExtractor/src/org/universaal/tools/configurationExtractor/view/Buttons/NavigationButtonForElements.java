package org.universaal.tools.configurationExtractor.view.Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.universaal.tools.configurationExtractor.utility.Constants;
import org.universaal.tools.configurationExtractor.view.Elements.BigElement;
import org.universaal.tools.configurationExtractor.view.Elements.ListG;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.ParentPanel;

/**
 * 
 * @author Ilja
 * This class allowed to change two elements. 
 */
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
		ImageIcon upIcon = new ImageIcon( Constants.ARROW_UP_ELEMENT_ICON );
		top=new JButton(upIcon);
		top.setMargin(new java.awt.Insets(0, 0, 0, 0));
		top.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  	  changeWithTop();
				  }
				} );
		ImageIcon downIcon = new ImageIcon( Constants.ARROW_DOWN_ELEMENT_ICON );
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
	
	/*
	 * Change the current element with one on the top 
	 */
	private void changeWithTop(){
		
		int indexOfTop=parent.getElementsList().indexOf(element)-1;
		if(indexOfTop >= 0 && !(parent.getElementsList().get(indexOfTop) instanceof ListG)){
			parent.changeTwoElements(element,parent.getElementsList().get(indexOfTop));
		}else if((parent.getElementsList().get(indexOfTop) instanceof ListG)){
			JOptionPane.showMessageDialog( null, "Sorry, but \"list\" has to be always on the top of \"listpanel\"" );
		}
	}
	
	/*
	 * Change the current element with one on the down 
	 */
	private void changeWithDown(){
		int indexOfDown=parent.getElementsList().indexOf(element)+1;

		if(indexOfDown<parent.getElementsList().size()){
			parent.changeTwoElements(element,parent.getElementsList().get(indexOfDown));
		}
	}
	
	/**
	 * Set the parent panel
	 * @param p parent panel
	 */
	public void setParent(ParentPanel p){
		this.parent=p;
	}

}
