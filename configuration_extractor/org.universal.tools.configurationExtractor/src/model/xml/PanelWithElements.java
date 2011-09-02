package model.xml;
import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import controller.GuiControl;
import controller.MainPanelControl;

import view.Elements.BigElement;
import view.Elements.Panels;
import view.Elements.Root;
import view.LowLevelPanels.BigRootPanel;
import view.LowLevelPanels.ParentPanel;

public class PanelWithElements {
	private static PanelWithElements panelWithElements;	
	private GuiControl guiControl = GuiControl.getInstance();
//	private MainPanelControl mpControl=MainPanelControl.getInstance();
	private	LinkedList <ParentPanel> parentPanels = new LinkedList<ParentPanel>();
	private LinkedList<DefaultMutableTreeNode> nodesList=new LinkedList<DefaultMutableTreeNode>();
	private LinkedList<BigElement> listWithOtherElements = new LinkedList<BigElement>();
	private Root root;
		
		private PanelWithElements(){
			guiControl = GuiControl.getInstance();
		}
		
		public void removeAll(){
			BigRootPanel brp=BigRootPanel.getInstance();
			brp.removeRoot();
			for(int i=0;i<parentPanels.size();i++){				
				brp.deleteParentPanel(parentPanels.get(i));
			}
			parentPanels.clear();		
//			Iterator<ParentPanel> itr = parentPanels.iterator();
//			while (itr.hasNext()) {
//				ParentPanel p=(ParentPanel) itr;
//				deleteParentPanel(p);
//			}
			//			brp=null;
//			parentPanels = new LinkedList<ParentPanel>();
//			nodesList=new LinkedList<DefaultMutableTreeNode>();
//			listWithOtherElements = new LinkedList<BigElement>();
//			root=null;
			panelWithElements=new PanelWithElements();	
			
		}
		
		public Root getRoot(){
			return root;
		}
		
		public void setRoot(Root r){
			MainPanelControl mpControl=MainPanelControl.getInstance();
			root=r;
//			System.out.println("roooooooooooooooot?"+mpControl);
			mpControl.addRoot(root);
		
		}
		
		public void addParentPanel(ParentPanel p){
			guiControl = GuiControl.getInstance();
			MainPanelControl mpControl=MainPanelControl.getInstance();
//			GuiControl guiControl = GuiControl.getInstance();
			parentPanels.add(p);
			refresh();
			DefaultMutableTreeNode child=new DefaultMutableTreeNode(p.getTypeOfPanel()+p.getTitle());

			System.out.println("parentSize= "+parentPanels.size());
			guiControl.addNode(child, parentPanels.size()-1);
			nodesList.add(child);
		}
		public void deleteParentPanel(ParentPanel parent){
			 guiControl = GuiControl.getInstance();
			MainPanelControl mpControl=MainPanelControl.getInstance();
			DefaultMutableTreeNode parentNode=getNode(parent.getTitle());
//			System.out.println("size is="+parent.getElementsList().size());
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

		private void refresh(){
			MainPanelControl mpControl=MainPanelControl.getInstance();
			mpControl.refresh(parentPanels);
		}
		
		public void changeTwoParentPanels(ParentPanel p1 , ParentPanel p2){
			 guiControl = GuiControl.getInstance();
//			System.out.println("NULLLLLLLLLLLLL?=="+guiControl);
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
		public LinkedList <ParentPanel> getParentsList(){
			return parentPanels;
		}

		public ParentPanel getParentPanel(String title){
			
			System.out.println("title:"+title);
			
			for(int i= 0;i<parentPanels.size();i++) {
				System.out.println("parent:"+parentPanels.get(i).getTitle());
				if(parentPanels.get(i).getTitle().equals(title) || ("Listpanel "+parentPanels.get(i).getTitle()).equals(title)
					|| ("Panel "+parentPanels.get(i).getTitle()).equals(title)) {
				
					System.out.println("Panel gefunden!");
					return parentPanels.get(i);
				}
			}
			System.out.println("Panel nicht gefunden!");
			return parentPanels.get(parentPanels.size()-1);
		}
		
	public DefaultMutableTreeNode getNode(String title){
//		System.out.println("eingabe=="+"Listpanel "+title);
//		System.out.println(nodesList.size());
			for(int i= 0;i<nodesList.size();i++) {

				System.out.println("parent number "+i+"is: "+nodesList.get(i).toString());
				if(nodesList.get(i).toString().equals("Listpanel "+title)  ||nodesList.get(i).toString().equals("Panel "+title)) {
					return nodesList.get(i);
				}
			}
			System.out.println("blatt "+title+" nicht gefunden!!");
			return nodesList.get(nodesList.size()-1);
		}

	public void changeNameOfNode(DefaultMutableTreeNode node , String name, String type){
		int index = nodesList.indexOf(node);
		nodesList.get(index).setUserObject(type+name);
		guiControl.updateTree();
	}
		
	//public LinkedList<BigElement> getlistWithOtherElementst(){
//		return listWithOtherElements;
	//}
	public void addToOtherElementsList(BigElement element){
		listWithOtherElements.add(element);
	}
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

	public void  moveElementToAnotherPanel(String leaf, String sourcePanel,String targetPanel){
		System.out.println("here!");
		System.out.println("parentfromLeaf1: "+leaf);
		System.out.println("sourcePanel: "+sourcePanel);
		System.out.println("targetPanel: "+targetPanel);
		ParentPanel source;
		ParentPanel target;
		BigElement element;
		
		if(targetPanel==null){
			System.out.println("move to otherElements");
			source=getParentPanel(sourcePanel);
			element= source.findBigElement(leaf);
			listWithOtherElements.add(element);
			source.deleteElement(element);
			
		}else if(sourcePanel==null){ 
			System.out.println("move from otherElements");
			element=getElementFromOtherElements(leaf);
			target=getParentPanel(targetPanel);
			int index= listWithOtherElements.indexOf(element);
			listWithOtherElements.remove(index);
			element.setParentPanel(target);
//			target.addElement(element);
			
		}else{
		
			source=getParentPanel(sourcePanel);
			target=getParentPanel(targetPanel);
			element= source.findBigElement(leaf);
//			source.deleteElement(element);
			element.setParentPanel(target);
//			target.addElement(element);
			
			
			
		}
	}

	public static PanelWithElements getInstance(){
		if(panelWithElements==null){
			System.out.println("panelWithElements init!");
			panelWithElements=new PanelWithElements();
		}
		return panelWithElements;
	}
}
