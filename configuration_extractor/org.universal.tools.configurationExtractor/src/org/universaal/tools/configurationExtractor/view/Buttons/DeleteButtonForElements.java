package org.universaal.tools.configurationExtractor.view.Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import org.universaal.tools.configurationExtractor.model.xml.PanelWithElements;

import org.universaal.tools.configurationExtractor.controller.GuiControl;

import org.universaal.tools.configurationExtractor.utility.Constants;
import org.universaal.tools.configurationExtractor.view.Elements.BigElement;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.ParentPanel;
/**
 * 
 * @author Ilja
 * The instance of this class is a special button, which can remove a element from some panel.
 */
@SuppressWarnings("serial")
public class DeleteButtonForElements extends JButton{
	private ParentPanel parent;
	private BigElement element;
	private GuiControl guiControl = GuiControl.getInstance();
	private PanelWithElements panelWithElements= PanelWithElements.getInstance();
	
	/**
	 * Constructor method.
	 * @param parent parent panel of a element
	 * @param element element which will be a container for a button. This element will be deleted after click on the button
	 */
	public DeleteButtonForElements(ParentPanel parent , BigElement element){
		this.element=element;
		this.parent=parent;
		ImageIcon del = new ImageIcon( Constants.DELETE_ELEMENT_ICON);
		
		this.setIcon(del);
		this.setMargin(new java.awt.Insets(0, 0, 0, 0));

		this.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  deleteElement();
				  
			  }
			  } );
		String help = "Remove element";
		this.setToolTipText(help);
	}
	
	/*
	 * Helper method to remove the element  
	 */
	private void deleteElement(){
		if(parent.getTypeOfPanel().equals("Panel ")){
			element.setVisible(false);
			DefaultMutableTreeNode p=panelWithElements.getNode(parent.getTitle());
			int indexOfChild= parent.getElementsList().indexOf(element);
			DefaultMutableTreeNode leaf=guiControl.getLeaf(p, indexOfChild);
			guiControl.toMoveALeaf(leaf, p, guiControl.getOtherElementsNode());
			guiControl.expandTree();
			
		}else{
			System.out.println("not possible to remove Element from Listpanel");
			JOptionPane.showMessageDialog( null, "It's not allowed to remove Elements from Listpanel!" );
			}		
	}
	
	/**
	 * Set a new parent of element
	 * @param p parent panel
	 */
	public void setParent(ParentPanel p){
		this.parent=p;
	}
}
