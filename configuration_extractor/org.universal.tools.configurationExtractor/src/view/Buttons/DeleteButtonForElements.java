package view.Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import model.xml.PanelWithElements;

import controller.GuiControl;

import utility.Constants;
import view.Elements.BigElement;
import view.LowLevelPanels.BigRootPanel;
import view.LowLevelPanels.ParentPanel;

import view.MainPanels.TreePanel;

@SuppressWarnings("serial")
public class DeleteButtonForElements extends JButton{
	private ParentPanel parent;
	private BigElement element;
	private GuiControl guiControl = GuiControl.getInstance();
//	private LeftPanel lf=LeftPanel.getInstance();
//	TreePanel treePanel=TreePanel.getInstance();
//	BigRootPanel bRP=BigRootPanel.getInstance();
	PanelWithElements panelWithElements= PanelWithElements.getInstance();
	
	public DeleteButtonForElements(ParentPanel parent , BigElement element){
		this.element=element;
		this.parent=parent;
//		ImageIcon del = new ImageIcon( "C:/Users/Ilja/workspace/CE/icons/DeleteIcon1.gif" );
		ImageIcon del = new ImageIcon( Constants.DELETE_ELEMENT_ICON);
//		ImageIcon del = new ImageIcon( "icons/DeleteIcon1.gif" );
		
		
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
	void deleteElement(){
		if(parent.getTypeOfPanel().equals("Panel ")){
			element.setVisible(false);
			DefaultMutableTreeNode p=panelWithElements.getNode(parent.getTitle());
			int indexOfChild= parent.getElementsList().indexOf(element);
			DefaultMutableTreeNode leaf=guiControl.getLeaf(p, indexOfChild);
			guiControl.toMoveALeaf(leaf, p, guiControl.getOtherElementsNode());
//			treePanel.updateUI();
			guiControl.expandTree();
//			System.out.println("parent from deletebutton="+element.getTitle());
			
		}else{
			System.out.println("not possible to remove Element from Listpanel");
			JOptionPane.showMessageDialog( null, "It's not allowed to remove Elements from Listpanel!" );
			}		
	}
	public void setParent(ParentPanel p){
		this.parent=p;
	}
}
