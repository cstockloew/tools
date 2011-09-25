package org.universaal.tools.configurationExtractor.view.Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.universaal.tools.configurationExtractor.model.xml.PanelWithElements;
import org.universaal.tools.configurationExtractor.utility.Constants;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.ParentPanel;

/**
 * 
 * @author Ilja
 * This class allowed to change two panels. 
 */
@SuppressWarnings("serial")
public class NavigationButtonForBigPanels extends JPanel {
	JButton top;
	JButton down;
	PanelWithElements parent = PanelWithElements.getInstance();
	ParentPanel element;
	public NavigationButtonForBigPanels(ParentPanel element){
		this.element=element;
		this.setLayout( new BoxLayout(this, BoxLayout.X_AXIS) );
		ImageIcon upIcon = new ImageIcon( Constants.ARROW_UP_PANEL_ICON );
		top=new JButton(upIcon);
		top.setMargin(new java.awt.Insets(0, 0, 0, 0));
		top.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  	  changeWithTop();
				  }
				} );
		ImageIcon downIcon = new ImageIcon( Constants.ARROW_DOWN_PANEL_ICON );
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
	
	/*
	 * Change the current panel with one on the top 
	 */
	private void changeWithTop(){
		int indexOfTop=parent.getParentsList().indexOf(element)-1;
		if(indexOfTop >= 0){
			parent.changeTwoParentPanels(element,parent.getParentsList().get(indexOfTop));
		}	
	}
	
	/*
	 * Change the current panel with one on the down 
	 */
	private void changeWithDown(){
		int indexOfDown=parent.getParentsList().indexOf(element)+1;
		if(indexOfDown<parent.getParentsList().size()){
			parent.changeTwoParentPanels(element,parent.getParentsList().get(indexOfDown));
		}
	}

}

