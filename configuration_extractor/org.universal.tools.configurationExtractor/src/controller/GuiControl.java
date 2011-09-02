package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.DefaultListModel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import view.Elements.BigElement;
import view.MainPanels.XMLPanel;

import model.xml.AnnotationsExtractor;
import model.xml.TreeWithElements;
import model.xml.XMLCreator;

public class GuiControl {
	private static GuiControl instance = new GuiControl();
	private AnnotationsExtractor annotationsExtractor=  AnnotationsExtractor.getInstance();
	private TreeWithElements treeWithElements= TreeWithElements.getInstance(); 
	
	private GuiControl(){
	}
	
	public static GuiControl getInstance(){
		return instance;
	}
	
   public void refreshXmlPanel(){
	  XMLCreator xc = XMLCreator.getInstance();
	  xc.init();
      xc.printToXmlPanel();
	}
	
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
   
    public boolean xmlPanelChanged(){
    	 XMLPanel xmlPanel= XMLPanel.getInstance();
    	 return xmlPanel.xmlPanelChanged();
    }
	
	public JTree getTree(){
		return treeWithElements.getTree();
	}
	
	public void expandTree(){
		treeWithElements.expandTree();
	}
	
	public DefaultMutableTreeNode getLeaf(DefaultMutableTreeNode parent, int indexOfChild){
		return treeWithElements.getLeaf(parent, indexOfChild);
	}
	
	
	public DefaultMutableTreeNode getOtherElementsNode(){
		return treeWithElements.getOtherElementsNode();
	}
	
	public void toMoveALeaf(DefaultMutableTreeNode selectedNode, DefaultMutableTreeNode sourceParentNode,DefaultMutableTreeNode targetParentNode){
		treeWithElements.toMoveALeaf(selectedNode, sourceParentNode,targetParentNode);
	}
	
	public boolean isOtherElementsNodeEmpty(){
		return treeWithElements.isOtherElementsNodeEmpty();
	}
	
	public void addLeaf(MutableTreeNode child ,MutableTreeNode parent){
		treeWithElements.addLeaf(child ,parent);

	}
	
	public void addNode(MutableTreeNode child , int index){
		treeWithElements.addNode(child ,index);
	}
	
	public void removeNode(MutableTreeNode node){
		treeWithElements.removeNode(node);
	}

	public void updateTree(){
		treeWithElements.updateTree();
	}
		
	public void addLeaf1(MutableTreeNode child ,MutableTreeNode parent,int index){
		treeWithElements.addLeaf1(child , parent,index);
	}
	public DefaultMutableTreeNode getPanelsNode(){
		return treeWithElements.getPanelsNode();
	}
		
		
		
		
	public DefaultListModel getDirectories(){
		return annotationsExtractor.getDirs();
	}
	
	public void addElementToParentPanel(BigElement e1){
		
	}
	
	
}
