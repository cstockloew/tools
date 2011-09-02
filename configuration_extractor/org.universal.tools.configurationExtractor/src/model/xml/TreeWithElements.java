package model.xml;

import java.util.LinkedList;
import java.awt.Component;
import java.awt.Point;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import utility.Constants;
import view.Elements.BigElement;
import view.LowLevelPanels.BigRootPanel;
import view.LowLevelPanels.ParentPanel;
import model.xml.XMLCreator;
	
	public class TreeWithElements {

		private PanelWithElements panelWithElements= PanelWithElements.getInstance();
		private static TreeWithElements treeWithElements;
		private JTree tree;
		private DefaultTreeModel treeModel;
		private DefaultMutableTreeNode panels;
		private DefaultMutableTreeNode otherElements;
//		private BigRootPanel bRP=BigRootPanel.getInstance();
		private	DefaultMutableTreeNode root;
		
		public void removeAll(){
			tree=null;
//			treeModel=null;
//			panels=null;
//			otherElements=null;
//			root=null;
//			treeWithElements=new TreeWithElements();
			panels.removeAllChildren();
			otherElements.removeAllChildren();	
			init();
			expandTree();
			System.out.println("treeeeeee="+tree);
			updateTree();

		}
		
		public static TreeWithElements getInstance(){
			if(treeWithElements==null){
				System.out.println("treeWithElements init!");
				treeWithElements=new TreeWithElements();
			}
			return treeWithElements;
		}

		private TreeWithElements(){
			        init();	
			
		}
		public void init(){

	    	root = new DefaultMutableTreeNode( "root" );
			treeModel = new DefaultTreeModel( root );
			panels = new DefaultMutableTreeNode( "panels" ) ;
			root.add(panels);
			otherElements=new DefaultMutableTreeNode( "otherElements" ) ;
			root.add(otherElements);
			tree=new JTree(treeModel);
			
			
			
			
			
//			ElementTreeNode e=new ElementTreeNode("child");
//			otherElements.add(e);
//			
//			  final ImageIcon e_SYMBOL = new ImageIcon( Constants.E_SYMBOL);
//
//		        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {
//		            @Override
//		            public Component getTreeCellRendererComponent(JTree tree,
//		                    Object value, boolean sel, boolean expanded, boolean leaf,
//		                    int row, boolean hasFocus) {
//		                DefaultMutableTreeNode currentTreeNode = (DefaultMutableTreeNode) value;
//		                System.out.println("currentTreeNode "+currentTreeNode);
//		                Object userObject = (Object) currentTreeNode.getUserObject();
//		             
//		                if(currentTreeNode.toString().equals(("child"))){
//		                	setLeafIcon(e_SYMBOL);
//		                    setClosedIcon(e_SYMBOL);
//		                    setOpenIcon(e_SYMBOL);
//		                }
//		                
//		                return super.getTreeCellRendererComponent(tree, value, sel,
//		                        expanded, leaf, row, hasFocus);
//		            }
//		        };
//		        tree.setCellRenderer(renderer);
			
			
			
			
			
			
			
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
		 
//		                        System.out.println("###################");
//		 
//		                        System.out.println("srcPath: " + sourcePath);
//		                        System.out.println("targetPath: " + targetPath);
//		                        System.out.println("selectedNode: " + selectedNode);
		 
		                        if (isDropAllowed(sourcePath, targetPath, selectedNode)) {
		                            System.out.println("drop accept");
		                             DefaultMutableTreeNode sourceParentNode = (DefaultMutableTreeNode) sourcePath
		                                    .getLastPathComponent();
		                            DefaultMutableTreeNode targetParentNode = (DefaultMutableTreeNode) targetPath
		                                    .getLastPathComponent();
		                           
		 
//		                            sourceParentNode.remove(selectedNode);
//		                            targetParentNode.add(selectedNode);
	                     
		                            toMoveALeaf(selectedNode, sourceParentNode,targetParentNode);
		 
		                            dtde.dropComplete(true);
		                            updateTree();
//		                            tree.updateUI();
		                        } else {
		                            System.out.println("drop: reject");
		                            dtde.rejectDrop();
		                            dtde.dropComplete(false);
		                        }
		                    }
		 
		                    private boolean isDropAllowed(TreePath sourcePath, TreePath targetPath, DefaultMutableTreeNode selectedNode) {
		           //            System.out.println("sourcePath.getLastPathComponent()="+sourcePath.getLastPathComponent());
//		                    	if (((DefaultMutableTreeNode) sourcePath.getLastPathComponent()).isLeaf()) {
//		                    		System.out.println("isLeaf");
//		                        } else 
//		                    	 System.out.println("selectedNode="+selectedNode);
//		                       System.out.println("sourcePath="+sourcePath.getLastPathComponent());
//		                       System.out.println("targetPath="+targetPath.getLastPathComponent());
		                    	if (selectedNode.equals(panels) || selectedNode.equals(root)) {
	                        		System.out.println("root and panels arent moveable !");
	                        		return false;
	                        	}
		                        	if (targetPath.equals(sourcePath)) {
		                        		System.out.println("source and target are the same ");
		                            return false;
		                        	}
		                        	if (selectedNode.equals(otherElements)) {
		                        		System.out.println("dont move otherElemnts!");
		                            return false;
		                        	}
		                        	if (sourcePath.getLastPathComponent().equals(panels)) {
		                        		System.out.println("dont move panels!");
		                        		return false;
		                        	}
		                        	if (!(targetPath.getParentPath().getLastPathComponent().equals(panels) || targetPath.getLastPathComponent().equals(otherElements))) {
		                        		System.out.println("parent isnt panels or otherElements");
		                        		return false;
			                        }
		                        	
		                        	String typeOfTargetArray[]=targetPath.getLastPathComponent().toString().split(" ");
		                        	String typeOfTarget=typeOfTargetArray[0];
		                        	String typeOfSourceArray[]=sourcePath.getLastPathComponent().toString().split(" ");
		                        	String typeOfSource=typeOfSourceArray[0];	                        	
		                        //	System.out.println("source= "+typeOfSource+" target="+typeOfTarget);
		                        	
		                        	
		                        	if (typeOfTarget.equals("Listpanel") || typeOfSource.equals("Listpanel")) {
		                        		System.out.println("dont move elements to or from Listpanel!");
		                        		JOptionPane.showMessageDialog( null, "It's not allowed to add or remove Elements from Listpanel!" );
		                        		return false;
			                        }
		                        	
		                        return selectedNode.isLeaf();
		                    }
		 
		       }));
		    expandTree();
		}
		
		public void updateTree(){
			tree.updateUI();
		}
		
		public void addNode(MutableTreeNode child , int index){
//			System.out.println(panels.getChildCount());
			treeModel.insertNodeInto(child, panels, index);	
//			System.out.println(panels.getChildCount());
		}
		
		
		public void addLeaf(MutableTreeNode child ,MutableTreeNode parent){
//			System.out.println(panels.getChildCount());

			treeModel.insertNodeInto(child, parent, parent.getChildCount());	

		}
		public void addLeaf1(MutableTreeNode child ,MutableTreeNode parent,int index){
//			System.out.println(panels.getChildCount());
			treeModel.insertNodeInto(child, parent, index);	
//			System.out.println(panels.getChildCount());
		}
		
		public void removeNode(MutableTreeNode node){
			
//			System.out.println("rem1"+panels.getChildCount());

			treeModel.removeNodeFromParent(node);
//			System.out.println("rem2"+panels.getChildCount());
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
	        	System.out.println("leaf="+leaf);
	        	
	        }else if(sourceParentNode.equals(otherElements)){
	        	panelsWithElements.moveElementToAnotherPanel(leaf, null,target);
	        }else{
	        	panelsWithElements.moveElementToAnotherPanel(leaf, source,target);
	        }
	        tree.updateUI();
		}
		
		public DefaultMutableTreeNode getLeaf(DefaultMutableTreeNode parent, int indexOfChild){
//			System.out.println("parent="+parent);
//			System.out.println("index="+indexOfChild);
			
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
