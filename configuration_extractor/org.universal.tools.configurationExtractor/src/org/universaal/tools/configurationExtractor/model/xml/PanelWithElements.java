package org.universaal.tools.configurationExtractor.model.xml;

import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.tree.DefaultMutableTreeNode;
import org.universaal.tools.configurationExtractor.controller.GuiControl;
import org.universaal.tools.configurationExtractor.controller.MainPanelControl;
import org.universaal.tools.configurationExtractor.view.Elements.BigElement;
import org.universaal.tools.configurationExtractor.view.Elements.Root;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.BigRootPanel;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.ParentPanel;

/**
 * @author Ilja
 * This part of the Model(MVC) contains all data structures which will be presented on main panel of GUI   
 */
public class PanelWithElements {
	private static PanelWithElements panelWithElements;	
	private GuiControl guiControl = GuiControl.getInstance();
	private	LinkedList <ParentPanel> parentPanels = new LinkedList<ParentPanel>();
	private LinkedList<DefaultMutableTreeNode> nodesList=new LinkedList<DefaultMutableTreeNode>();
	private LinkedList<BigElement> listWithOtherElements = new LinkedList<BigElement>();
	private Root root;

	/*
	 * The constructor is private because of singleton implementation of this class
	 */
	private PanelWithElements(){
		guiControl = GuiControl.getInstance();
	}
	/**
	 * Clear all data of this class	
	 */
	public void removeAll(){
		BigRootPanel brp=BigRootPanel.getInstance();
		brp.removeRoot();
		for(int i=0;i<parentPanels.size();i++){				
			brp.deleteParentPanel(parentPanels.get(i));
		}
		parentPanels.clear();		
		panelWithElements=new PanelWithElements();	
		
	}
	/**
	 * get root element of use case
	 * @return root element
	 */
	public Root getRoot(){
		return root;
	}
	/**
	 * Set root element	
	 * @param r root element
	 */
	public void setRoot(Root r){
		MainPanelControl mpControl=MainPanelControl.getInstance();
		root=r;
		mpControl.addRoot(root);
	
	}
	/**
	 * Add a new ParentPanel (e.g. BigListPanel, BigPanel) 
	 * @param p ParentPanel which will be added
	 */
	public void addParentPanel(ParentPanel p){
		guiControl = GuiControl.getInstance();
		parentPanels.add(p);
		refresh();
		DefaultMutableTreeNode child=new DefaultMutableTreeNode(p.getTypeOfPanel()+p.getTitle());
		
//      debug output
//		System.out.println("parentSize= "+parentPanels.size());
		
		guiControl.addNode(child, parentPanels.size()-1);
		nodesList.add(child);
	}
	/**
	 * Remove given ParentPanel 
	 * @param parent ParentPanel which will be removed
	 */
	public void deleteParentPanel(ParentPanel parent){
        guiControl = GuiControl.getInstance();
		MainPanelControl mpControl=MainPanelControl.getInstance();
		DefaultMutableTreeNode parentNode=getNode(parent.getTitle());
		Iterator<BigElement> itr = parent.getElementsList().iterator();
		while (itr.hasNext()) {
			DefaultMutableTreeNode leaf=guiControl.getLeaf(parentNode, 0);
			guiControl.toMoveALeaf(leaf, parentNode, guiControl.getOtherElementsNode());
		}
		DefaultMutableTreeNode child=(DefaultMutableTreeNode) guiControl.getTree().getModel().getChild(guiControl.getPanelsNode(), parentPanels.indexOf(parent));
		guiControl.removeNode(child);
		nodesList.remove(child);
		guiControl.expandTree();
		mpControl.removeParentPanel(parent);
		parentPanels.remove(parent); 
		refresh();

	}

	/**
	 * Refresh main panel of GUI 
	 */
	private void refresh(){
		MainPanelControl mpControl=MainPanelControl.getInstance();
		mpControl.refresh(parentPanels);
	}
		
	/**
	 * This method allows to change two ParentPanels
	 * @param p1 first ParentPanel
	 * @param p2 second ParentPanel
	 */
	public void changeTwoParentPanels(ParentPanel p1 , ParentPanel p2){
	    guiControl = GuiControl.getInstance();
		DefaultMutableTreeNode child1=(DefaultMutableTreeNode) guiControl.getTree().getModel().getChild(guiControl.getPanelsNode(), parentPanels.indexOf(p1));
		DefaultMutableTreeNode child2=(DefaultMutableTreeNode) guiControl.getTree().getModel().getChild(guiControl.getPanelsNode(), parentPanels.indexOf(p2));
		guiControl.removeNode(child1);
		guiControl.addNode(child1, parentPanels.indexOf(p2));
		guiControl.removeNode(child2);
		guiControl.addNode(child2, parentPanels.indexOf(p1));
	
		p1.setVisible(false);
		p2.setVisible(false);
		ParentPanel help=p1.cloneParentPanel();
		
		int indexOfFirst=parentPanels.indexOf(p1);
		int indexOfSecond=parentPanels.indexOf(p2);
		
		parentPanels.set(indexOfFirst, p2);
			
		parentPanels.set(indexOfSecond, help);
		p1.setVisible(true);
		p2.setVisible(true);
		refresh();

	}
	/**
	 * Get all ParentPanels
	 * @return List with all ParentPanels
	 */
	public LinkedList <ParentPanel> getParentsList(){
		return parentPanels;
	}

	/**
	 * Get ParentPanel of given title
	 * @param title of some instance of ParentPanel
	 * @return ParentPanel with the title ="title"
	 */
	public ParentPanel getParentPanel(String title){
			
//		System.out.println("title:"+title);
			
		for(int i= 0;i<parentPanels.size();i++) {
			if(parentPanels.get(i).getTitle().equals(title) || ("Listpanel "+parentPanels.get(i).getTitle()).equals(title)
				|| ("Panel "+parentPanels.get(i).getTitle()).equals(title)) {
				return parentPanels.get(i);
			}
		}
//  		debug output		
		System.out.println("Panel nicht gefunden!");
		return parentPanels.get(parentPanels.size()-1);
	}
	/**
	 * Get tree node with special title
	 * @param title of some "panel node"
	 * @return the panel node
	 */
	public DefaultMutableTreeNode getNode(String title){
		for(int i= 0;i<nodesList.size();i++) {
//			System.out.println("parent number "+i+"is: "+nodesList.get(i).toString());
			if(nodesList.get(i).toString().equals("Listpanel "+title)  ||nodesList.get(i).toString().equals("Panel "+title)) {
				return nodesList.get(i);
			}
		}
		return nodesList.get(nodesList.size()-1);
	}
	/**
	 * Change name of node in the tree structure
	 * @param node which name should be changed
	 * @param input new name
	 * @param type type of element (e.g. Panel)
	 */
	public void changeNameOfNode(DefaultMutableTreeNode node , String name, String type){
		int index = nodesList.indexOf(node);
		nodesList.get(index).setUserObject(type+name);
		guiControl.updateTree();
	}
	/**
	 * Add given BigElement instance to the "otherElements" (BigElements without ParentPanels) list
	 * @param element BigElement instance, which will be added to the "otherElements"
	 */
	public void addToOtherElementsList(BigElement element){
		listWithOtherElements.add(element);
	}
	/**
	 * Get BigElement instance with title="leaf"
	 * @param leaf title of some leaf of "otherElements"
	 * @return BigElement from "otherElements"
	 */
	private BigElement getElementFromOtherElements(String leaf){
		Iterator<BigElement> itr = listWithOtherElements.iterator();
		while (itr.hasNext()) {
			BigElement element = itr.next(); 
			if(("Element "+element.getTitle()).equals(leaf)){
				System.out.println("element found!");
				return element;
			}
		}
		System.out.println("element not found!");
		return null;
	}
	/**
	 * This method allows to move BigElements between different BigPanels (Drag and Drop in the tree structure)
	 * @param leaf id of BigElement leaf
	 * @param sourcePanel title of souceParentPanel
	 * @param targetPanel title of targetParentPanel
	 */
	public void moveElementToAnotherPanel(String leaf, String sourcePanel,String targetPanel){
//		debug output
//		System.out.println("parentfromLeaf1: "+leaf);
//		System.out.println("sourcePanel: "+sourcePanel);
//		System.out.println("targetPanel: "+targetPanel);
		ParentPanel source;
		ParentPanel target;
		BigElement element;
		
//		move to otherElements;
		if(targetPanel==null){			
			source=getParentPanel(sourcePanel);
			element= source.findBigElement(leaf);
			listWithOtherElements.add(element);
			source.deleteElement(element);
//		move from otherElements			
		}else if(sourcePanel==null){ 
			element=getElementFromOtherElements(leaf);
			target=getParentPanel(targetPanel);
			int index= listWithOtherElements.indexOf(element);
			listWithOtherElements.remove(index);
			element.setParentPanel(target);
			
		}else{		
			source=getParentPanel(sourcePanel);
			target=getParentPanel(targetPanel);
			element= source.findBigElement(leaf);
			element.setParentPanel(target);

		}
	}
	/**
	 * Typical singleton method
	 * @return singleton instance of PanelWithElements
	 */
	public static PanelWithElements getInstance(){
		if(panelWithElements==null){
//      		debug output			
//			System.out.println("panelWithElements init!");
			panelWithElements=new PanelWithElements();
		}
		return panelWithElements;
	}
}
