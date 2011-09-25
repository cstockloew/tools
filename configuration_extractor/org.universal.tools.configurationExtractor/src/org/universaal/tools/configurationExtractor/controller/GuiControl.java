package org.universaal.tools.configurationExtractor.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.swing.DefaultListModel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import org.universaal.tools.configurationExtractor.view.Elements.BigElement;
import org.universaal.tools.configurationExtractor.view.MainPanels.XMLPanel;
import org.universaal.tools.configurationExtractor.model.xml.AnnotationsExtractor;
import org.universaal.tools.configurationExtractor.model.xml.TreeWithElements;
import org.universaal.tools.configurationExtractor.model.xml.XMLCreator;

/**
 * This class is a part of controller component. GuiControl observes GUI of CE and give the user actions to Model. GuiControl is a singleton.
 */

public class GuiControl {
	private static GuiControl instance = new GuiControl();
	private AnnotationsExtractor annotationsExtractor=  AnnotationsExtractor.getInstance();
	private TreeWithElements treeWithElements= TreeWithElements.getInstance(); 
	/*
	 * The constructor is private,because there is only one instance of the class.
	 */
	private GuiControl(){
	}
	/**
	 * Singleton method
	 * @return singleton instance of GuiControl
	 */
	public static GuiControl getInstance(){
		return instance;
	}
	
	/**
	 * This method refreshes a XML Panel of GUI
	 */
   public void refreshXmlPanel(){
	  XMLCreator xc = XMLCreator.getInstance();
	  xc.init();
      xc.printToXmlPanel();
	}
	/**
	 * Print the actual xml configuration into file. If user have changed something in expert mode 
	 * and clicked on import then file will be the same as content of xml panel. Otherwise the actual configuration of PanelWithElements will be saved.  
	 * @param file with xml configuration
	 * @throws IOException 
	 */
	
   public void printXML(File file) throws IOException{
	   XMLPanel xmlPanel= XMLPanel.getInstance();
	   if(xmlPanel.getVisibilityStatus()){
		   String xmlText =xmlPanel.getTextFromXMLPanel();
		   try {  
			   FileOutputStream fos = new FileOutputStream(file);  
			   Writer out = new OutputStreamWriter(fos, "UTF8");  
			   out.write(xmlText);  
			   out.close();  
			   } catch (IOException e) {  
			   e.printStackTrace();  
			   }  
	   }else{
		   XMLCreator xc = XMLCreator.getInstance();
		   xc.init();
		   xc.printToFile(file);
	   }
   }
   /**
    * Check for changes in xml panel 
    * @return true if xml panel was changed.
    */
    public boolean xmlPanelChanged(){
    	 XMLPanel xmlPanel= XMLPanel.getInstance();
    	 return xmlPanel.xmlPanelChanged();
    }
	/**
	 * 
	 * @return tree of tree mode 
	 */
	public JTree getTree(){
		return treeWithElements.getTree();
	}
	/**
	 * expand tree of tree mode 
	 */
	public void expandTree(){
		treeWithElements.expandTree();
	}
	
	/**
	 * Get leaf of parent with special number
	 * @param parent parent node
	 * @param indexOfChild index of child in parent
	 * @return leaf
	 */
	public DefaultMutableTreeNode getLeaf(DefaultMutableTreeNode parent, int indexOfChild){
		return treeWithElements.getLeaf(parent, indexOfChild);
	}
	
	/**
	 * Get node otherElements
	 * @return node otherElements, which is a container for free elements
	 */
	public DefaultMutableTreeNode getOtherElementsNode(){
		return treeWithElements.getOtherElementsNode();
	}
	/**
	 * Move a leaf to other parent node
	 * @param selectedNode 
	 * @param sourceParentNode
	 * @param targetParentNode
	 */
	public void toMoveALeaf(DefaultMutableTreeNode selectedNode, DefaultMutableTreeNode sourceParentNode,DefaultMutableTreeNode targetParentNode){
		treeWithElements.toMoveALeaf(selectedNode, sourceParentNode,targetParentNode);
	}
	
	/**
	 * Check whether otherElements node is empty
	 * @return true if otherElements node is empty
	 */
	public boolean isOtherElementsNodeEmpty(){
		return treeWithElements.isOtherElementsNodeEmpty();
	}
	
	/**
	 * Add leaf to a parent node on the last place
	 * @param child leaf
	 * @param parent node
	 */
	public void addLeaf(MutableTreeNode child ,MutableTreeNode parent){
		treeWithElements.addLeaf(child ,parent);

	}
	/**
	 * Add node to panels
	 * @param child parents leaf
	 * @param index index of place, where parent leaf will be added
	 */
	public void addNode(MutableTreeNode child , int index){
		treeWithElements.addNode(child ,index);
	}
	/**
	 * Delete node from tree
	 * @param node to remove
	 */
	public void removeNode(MutableTreeNode node){
		treeWithElements.removeNode(node);
	}
	/**
	 * refresh treePanel
	 */
	public void updateTree(){
		treeWithElements.updateTree();
	}
		
	/**
	 * Add leaf to a parent node on the place with index
	 * @param child leaf
	 * @param parent node
	 * @param index index to add
	 */
	public void addLeafWithIndex(MutableTreeNode child ,MutableTreeNode parent,int index){
		treeWithElements.addLeafWithIndex(child , parent,index);
	}
	/**
	 * Get "panels" node of the tree
	 * @return panels "panels" node on the tree
	 */
	public DefaultMutableTreeNode getPanelsNode(){
		return treeWithElements.getPanelsNode();
	}
		
	
	public DefaultListModel getDirectories(){
		return annotationsExtractor.getDirs();
	}
	
	
	
}
