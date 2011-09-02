package controller;

import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;

import model.xml.PanelWithElements;
import view.Elements.Root;
import view.LowLevelPanels.BigRootPanel;
import view.LowLevelPanels.ParentPanel;

public class MainPanelControl {

	private static final MainPanelControl instance = new MainPanelControl();
//	private BigRootPanel bigRootPanel= BigRootPanel.getInstance();
//	private PanelWithElements panelWithElements = PanelWithElements.getInstance();
	
	private MainPanelControl(){
	}
		
	public static MainPanelControl getInstance(){
		return instance;
	}
	
	public void addRoot(Root r){
		BigRootPanel bigRootPanel= BigRootPanel.getInstance();
		System.out.println("roooooooooooooooot?"+bigRootPanel);
		BigRootPanel.getInstance().setRoot(r);
	}

	public DefaultMutableTreeNode getNode(String title){
		return PanelWithElements.getInstance().getNode(title);
	}
	
	public void addParentPanel(ParentPanel p){
		BigRootPanel bigRootPanel= BigRootPanel.getInstance();
		bigRootPanel.add(p);
	}
	
	public void removeParentPanel(ParentPanel p){
		BigRootPanel bigRootPanel= BigRootPanel.getInstance();
		bigRootPanel.deleteParentPanel(p);
	}
	
	public void refresh(LinkedList <ParentPanel> parentPanels){
		BigRootPanel bigRootPanel= BigRootPanel.getInstance();
		bigRootPanel.refresh(parentPanels);
	}
	public void changeNameOfNode(DefaultMutableTreeNode node, String input, String type){
		PanelWithElements.getInstance().changeNameOfNode(node,input,type);
	}
}
