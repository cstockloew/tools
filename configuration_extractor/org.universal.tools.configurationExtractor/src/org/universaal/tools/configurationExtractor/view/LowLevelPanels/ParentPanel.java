package org.universaal.tools.configurationExtractor.view.LowLevelPanels;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import org.universaal.tools.configurationExtractor.controller.GuiControl;
import org.universaal.tools.configurationExtractor.controller.MainPanelControl;

import org.universaal.tools.configurationExtractor.view.Elements.BigElement;
/**
 * 
 * @author Ilja
 * This class is provides a ParentPanel instance, which could be <panel> or <listpanel> element.
 */
@SuppressWarnings("serial")
public class ParentPanel extends JPanel {
	private GuiControl guiControl = GuiControl.getInstance();	
	private	LinkedList <BigElement> elements = new LinkedList<BigElement>();
	protected String title;
	private MainPanelControl mpControl= MainPanelControl.getInstance();
	/**
	 * Constructor
	 * @param title of parent panel
	 */
	public ParentPanel(String title){
		super.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		this.title=title;
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,7));
	}
	
	/**
	 * Add given BigElement to the parent panel
	 * @param el BigElement, which will be added to the parent panel
	 */
	public void addElement(BigElement el){
		this.add(el);
		elements.add(el);
		refresh();
//		debug output		
//		System.out.println("size after ADD is:"+elements.size());
		

	}
	/**
	 * Delete given BigElement from the parent panel
	 * @param el BigElement
	 */
	public void deleteElement(BigElement el){
		
		this.remove(el);
		elements.remove(el); 
		refresh();
//		debug output
//		System.out.println("size after DELETE is:"+elements.size());
	}
	/**
	 * Hide all "children" of parent panel
	 */
	public void rollElements(){
		for(int i=0;i<elements.size();i++){
			elements.get(i).setVisible(false);
		}
	}
	/**
	 * Show all "children" of parent panel
	 */
	public void rollOpenElements(){
		for(int i=0;i<elements.size();i++){
			elements.get(i).setVisible(true);
		}
	}
	
	/**
	 * Refresh all "children" of parent panel
	 */
	private void refresh(){
		for(int i=0;i<elements.size();i++){
			this.remove(elements.get(i));
		}
		for(int i=0;i<elements.size();i++){
			this.add(elements.get(i));
		}
		for(int i=0;i<elements.size();i++){
			elements.get(i).setVisible(false);
		}
		for(int i=0;i<elements.size();i++){
			elements.get(i).setVisible(true);
		}
	}
	/**
	 * Change the places of two children
	 * @param e1 first element
	 * @param e2 second element
	 */
	public void changeTwoElements(BigElement e1 , BigElement e2){
		mpControl= MainPanelControl.getInstance();
		e1.setVisible(false);
		e2.setVisible(false);
		BigElement help=e1.cloneBigElement();
		
		int indexOfFirst=elements.indexOf(e1);
		int indexOfSecond=elements.indexOf(e2);
		
		elements.set(indexOfFirst, e2);
		
		elements.set(indexOfSecond, help);
		e1.setVisible(true);
		e2.setVisible(true);
		refresh();
		TreeModel treeModel= guiControl.getTree().getModel();
		DefaultMutableTreeNode  parent =mpControl.getNode(getTitle());
		DefaultMutableTreeNode leaf1=(DefaultMutableTreeNode) treeModel.getChild(parent, indexOfFirst);
		DefaultMutableTreeNode leaf2=(DefaultMutableTreeNode) treeModel.getChild(parent, indexOfSecond);
		int index1= treeModel.getIndexOfChild(parent, leaf1);
		int index2=	treeModel.getIndexOfChild(parent, leaf2);
		guiControl.removeNode(leaf1);
		guiControl.addLeafWithIndex(leaf1,parent, index2);
		guiControl.removeNode(leaf2);
		guiControl.addLeafWithIndex(leaf2,parent ,index1);
		
	}
	/**
	 * Get the list with all children of the parent panel
	 * @return list with children of parent panel
	 */
	public LinkedList <BigElement> getElementsList(){
		return elements;
	}
	/**
	 * ------------------------------------------------------------------------------------
	 * Find the element with special title
	 * @param node title of element
	 * @return return the element with given title.otherwise return null
	 */
	public BigElement findBigElement  (String node){
		Iterator<BigElement> itr = elements.iterator();
		while (itr.hasNext()) {
			BigElement element = itr.next(); 
			if(("Element "+element.getTitle()).equals(node)){
				return element;
			}
		}
//		debug output
		System.out.println("element named "+node+" wasnt found!");
		return null;
	}

	/**
	 * Make a copy of parent panel
	 * @return copy of parent panel
	 */
	public ParentPanel cloneParentPanel(){
	       
		ParentPanel cloneParentPanel = this;	        	       
	    return cloneParentPanel;	       
	}
	/**
	 * Get title of parent panel
	 * @return title
	 */
	public String getTitle(){
		return title;
	}
	/**
	 * Change title of node <panel> in the tree perspective
	 * @param input new title
	 * @param type type of parent panel
	 */
	public void changeTitleOfNode(String input, String type){
		mpControl= MainPanelControl.getInstance();
		DefaultMutableTreeNode node= mpControl.getNode(title);
		mpControl.changeNameOfNode(node,input,type);

	}
	/**
	 * Get type of parent panel
	 * @return type of parent panel
	 */
	public String getTypeOfPanel(){
		return "ParentPanel";
	}
	
}
