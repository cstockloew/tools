package org.universaal.tools.configurationExtractor.model.xml;

import java.awt.Point;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import javax.swing.DropMode;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.universaal.tools.configurationExtractor.view.Elements.BigElement;
/**
 * 	
 * @author Ilja
 * Another part of Model (MVC). TreeWithElements contains all important methods to deal with the tree perspective of CE.
 */
public class TreeWithElements {
	private static TreeWithElements treeWithElements;
	private JTree tree;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode panels;
	private DefaultMutableTreeNode otherElements;
	private	DefaultMutableTreeNode root;
	
	/*
	 * Constructor is private because of singleton pattern. 
	 */
	private TreeWithElements(){
        init();	
	}
	
	/**
	 * Remove the history of tree	
	 */
	public void removeAll(){
		tree=null;
		panels.removeAllChildren();
		otherElements.removeAllChildren();	
		init();
		expandTree();
		updateTree();
		}
	/**
	 * Typical singleton method
	 * @return
	 */
	public static TreeWithElements getInstance(){
		if(treeWithElements==null){
//      		debug output
//			System.out.println("treeWithElements init!");
			treeWithElements=new TreeWithElements();
		}
		return treeWithElements;
	}
	/**
	 * Initialize all tree elements
	 */
	public void init(){

	    	root = new DefaultMutableTreeNode( "root" );
			treeModel = new DefaultTreeModel( root );
			panels = new DefaultMutableTreeNode( "panels" ) ;
			root.add(panels);
			otherElements=new DefaultMutableTreeNode( "otherElements" ) ;
			root.add(otherElements);
			tree=new JTree(treeModel);
					
			tree.setDragEnabled(true);
		    tree.getSelectionModel().setSelectionMode(
		    TreeSelectionModel.SINGLE_TREE_SELECTION);
		    tree.setDropMode(DropMode.USE_SELECTION);
		    tree.setDropTarget(new DropTarget(tree, TransferHandler.MOVE, new DropTargetAdapter() {
		                    @Override
		                    public void drop(DropTargetDropEvent dtde) {
		 
		                        TreePath selectionPath = tree.getSelectionPath();
		                        TreePath sourcePath = selectionPath.getParentPath();
		 
		                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
		 
		                        Point dropLocation = dtde.getLocation();
		                        TreePath targetPath = tree.getClosestPathForLocation(
		                                dropLocation.x, dropLocation.y);
		 
//		                    	debug output
//		                        System.out.println("###################");
//		 
//		                        System.out.println("srcPath: " + sourcePath);
//		                        System.out.println("targetPath: " + targetPath);
//		                        System.out.println("selectedNode: " + selectedNode);
		                        
//    						    drop accept
		                        if (isDropAllowed(sourcePath, targetPath, selectedNode)) {		                          
		                             DefaultMutableTreeNode sourceParentNode = (DefaultMutableTreeNode) sourcePath
		                                    .getLastPathComponent();
		                            DefaultMutableTreeNode targetParentNode = (DefaultMutableTreeNode) targetPath
		                                    .getLastPathComponent();
		                            toMoveALeaf(selectedNode, sourceParentNode,targetParentNode);
		 
		                            dtde.dropComplete(true);
		                            updateTree();
		                            
//		                        drop reject   
		                        } else {		                            
		                            dtde.rejectDrop();
		                            dtde.dropComplete(false);
		                        }
		                    }
		 
		                    private boolean isDropAllowed(TreePath sourcePath, TreePath targetPath, DefaultMutableTreeNode selectedNode) {
//		                         	root and panels arent moveable
		                    	if (selectedNode.equals(panels) || selectedNode.equals(root)) {
	                        		return false;
	                        	}
//		                    	    source and target are the same
		                       	if (targetPath.equals(sourcePath)) {		                        		
		                            return false;
		                       	}
//		                        	don´t move otherElemnts;
		                       	if (selectedNode.equals(otherElements)) {
		                       		
		                            return false;
		                        }
//		                            don´t move panels
		                        if (sourcePath.getLastPathComponent().equals(panels)) {                	
		                        	return false;
		                        }
//		                        parent isn´t panels or otherElements
		                        if (!(targetPath.getParentPath().getLastPathComponent().equals(panels) || targetPath.getLastPathComponent().equals(otherElements))) {
		                       		
		                       		return false;
			                    }
		                        	
		                       	String typeOfTargetArray[]=targetPath.getLastPathComponent().toString().split(" ");
		                       	String typeOfTarget=typeOfTargetArray[0];
		                       	String typeOfSourceArray[]=sourcePath.getLastPathComponent().toString().split(" ");
		                       	String typeOfSource=typeOfSourceArray[0];	                        	
//		                        don´t move elements to or from Listpanel!           	
		                       	if (typeOfTarget.equals("Listpanel") || typeOfSource.equals("Listpanel")) {
		                       		
		                       		JOptionPane.showMessageDialog( null, "It's not allowed to add or remove Elements from Listpanel!" );
		                       		return false;
			                    }
		                        	
		                        return selectedNode.isLeaf();
		                    }
		 
		       }));
		    expandTree();
	}
	/**
	 * Refresh tree	
	 */
	public void updateTree(){
		tree.updateUI();
	}
	/**
	 * Add new ParentPanel node to tree structure	
	 * @param child
	 * @param index
	 */
	public void addNode(MutableTreeNode child , int index){
		treeModel.insertNodeInto(child, panels, index);	
	}
		
	/**
	 * Add 	
	 * @param child
	 * @param parent
	 */
	public void addLeaf(MutableTreeNode child ,MutableTreeNode parent){
		treeModel.insertNodeInto(child, parent, parent.getChildCount());	
	}
	public void addLeafWithIndex(MutableTreeNode child ,MutableTreeNode parent,int index){
		treeModel.insertNodeInto(child, parent, index);	
	}
		
	public void removeNode(MutableTreeNode node){
		treeModel.removeNodeFromParent(node);
	}
		
	public void addToOtherElements(BigElement element){
		String idFromElement=element.getId();
		DefaultMutableTreeNode leaf = new DefaultMutableTreeNode( idFromElement );
		treeWithElements.addLeaf(leaf,otherElements);
	}
		
	public void toMoveALeaf(DefaultMutableTreeNode selectedNode, DefaultMutableTreeNode sourceParentNode,DefaultMutableTreeNode targetParentNode){
	    sourceParentNode.remove(selectedNode);
	    targetParentNode.add(selectedNode);
	    String leaf=selectedNode.toString();
	    String source=sourceParentNode.toString();
	    String target=targetParentNode.toString();
	    PanelWithElements panelsWithElements = PanelWithElements.getInstance();
	        
	    if(targetParentNode.equals(otherElements)){
	     	panelsWithElements.moveElementToAnotherPanel(leaf, source,null);
	        	
	    }else if(sourceParentNode.equals(otherElements)){
	       	panelsWithElements.moveElementToAnotherPanel(leaf, null,target);
	    }else{
	      	panelsWithElements.moveElementToAnotherPanel(leaf, source,target);
	    }
	     tree.updateUI();
	}
	
	public DefaultMutableTreeNode getLeaf(DefaultMutableTreeNode parent, int indexOfChild){
		DefaultMutableTreeNode child=(DefaultMutableTreeNode) parent.getChildAt(indexOfChild);
		return child;			
		}
		
	public void expandTree(){
		 for (int currentRowIndex = 0; currentRowIndex < tree.getRowCount(); currentRowIndex++) {
	            tree.expandRow(currentRowIndex);
	        }
	}
		
	public JTree getTree(){
		return tree;
	}

	public DefaultMutableTreeNode getPanelsNode(){
		return panels;
	}
	public DefaultMutableTreeNode getOtherElementsNode(){
		return otherElements;
	}
	
	public boolean isOtherElementsNodeEmpty() {
		return otherElements.isLeaf();
	}

	

}
