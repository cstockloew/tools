package view.LowLevelPanels;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import controller.GuiControl;
import controller.MainPanelControl;

import view.Elements.BigElement;

@SuppressWarnings("serial")
public class ParentPanel extends JPanel {
	private GuiControl guiControl = GuiControl.getInstance();	
	private	LinkedList <BigElement> elements = new LinkedList<BigElement>();
	String title;
//	TreePanel tree = TreePanel.getInstance();
//	BigRootPanel bRP=BigRootPanel.getInstance();
//	private PanelWithElements panelWithElements= PanelWithElements.getInstance();
	private MainPanelControl mpControl= MainPanelControl.getInstance();

	public ParentPanel(String title){
		super.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		this.title=title;
//		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED,Color.green,  Color.green));
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,7));
	}
	
	
	public void addElement(BigElement el){
		this.add(el);
		elements.add(el);
		refresh();
		System.out.println("size after ADD is:"+elements.size());
		

	}
	public void deleteElement(BigElement el){
		
		this.remove(el);
		elements.remove(el); 
		refresh();
		System.out.println("size after DELETE is:"+elements.size());
	}

	public void rollElements(){
		for(int i=0;i<elements.size();i++){
			elements.get(i).setVisible(false);
		}
	}
	public void rollOpenElements(){
		for(int i=0;i<elements.size();i++){
			elements.get(i).setVisible(true);
		}
	}
	
	void refresh(){
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
//		System.out.println(getTypeOfPanel()+getTitle());
		TreeModel treeModel= guiControl.getTree().getModel();
		DefaultMutableTreeNode  parent =mpControl.getNode(getTitle());
//		System.out.println("mpControl.getNode(getTitle()= "+mpControl.getNode(getTitle()));
//		System.out.println("panelWithElements.getNode(getTitle())= "+panelWithElements.getNode(getTitle()));
//		System.out.println("equals?="+mpControl.getNode(getTitle()).equals(panelWithElements.getNode(getTitle())));
//		System.out.println("Strings.equals?="+mpControl.getNode(getTitle().toString()).equals(panelWithElements.getNode(getTitle().toString())));
		DefaultMutableTreeNode leaf1=(DefaultMutableTreeNode) treeModel.getChild(parent, indexOfFirst);
		DefaultMutableTreeNode leaf2=(DefaultMutableTreeNode) treeModel.getChild(parent, indexOfSecond);
		int index1= treeModel.getIndexOfChild(parent, leaf1);
		int index2=	treeModel.getIndexOfChild(parent, leaf2);
		
//		System.out.println(leaf1.toString());
//		System.out.println(leaf2.toString());
		
		guiControl.removeNode(leaf1);
		guiControl.addLeaf1(leaf1,parent, index2);
		guiControl.removeNode(leaf2);
		guiControl.addLeaf1(leaf2,parent ,index1);
		
		
		
		
	}
	public LinkedList <BigElement> getElementsList(){
		return elements;
	}
	
	public BigElement findBigElement  (String node){
		Iterator<BigElement> itr = elements.iterator();
		while (itr.hasNext()) {
			BigElement element = itr.next(); 
			if(("Element "+element.getTitle()).equals(node)){
				System.out.println("element found!");
				return element;
			}
		}
		System.out.println("element named "+node+" wasnt found!");
		return null;
	}
	
	
//	public void removeAllElements(){
//		Iterator<BigElement> itr = elements.iterator();
//		while (itr.hasNext()) {
//			itr.remove();
//		}
//	}
	
	
	public ParentPanel cloneParentPanel(){
	       
		ParentPanel cloneParentPanel = this;	        	       
	    return cloneParentPanel;	       
	}
	public String getTitle(){
		return title;
	}
	public void changeTitleOfNode(String input, String type){
		mpControl= MainPanelControl.getInstance();
		System.out.println("parentPanel.changeTitle()");
		DefaultMutableTreeNode node= mpControl.getNode(title);
		System.out.println("node=="+node.toString());
//		node.setUserObject(input);
//		tree.updateTree();
		mpControl.changeNameOfNode(node,input,type);

	}
	
	public String getTypeOfPanel(){
		return "ParentPanel";
	}
	
}
