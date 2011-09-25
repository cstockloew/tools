package org.universaal.tools.configurationExtractor.controller;

import java.util.LinkedList;
import javax.swing.tree.DefaultMutableTreeNode;
import org.universaal.tools.configurationExtractor.model.xml.PanelWithElements;
import org.universaal.tools.configurationExtractor.view.Elements.Root;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.BigRootPanel;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.ParentPanel;

/**
 * This class implemented as singleton will be need for interaction from main panel of GUI and Model.
 */

public class MainPanelControl {

	private static final MainPanelControl instance = new MainPanelControl();
	/*
	 * Typically is constructor of singleton private
	 */
	private MainPanelControl(){
	}
	/*
	 * Typical method of singleton pattern
	 */		
	public static MainPanelControl getInstance(){
		return instance;
	}
	
	/**
	 * Add root element
	 * @param r root element
	 */
	public void addRoot(Root r){
		BigRootPanel.getInstance().setRoot(r);
	}
	/**
	 * Get tree node with special title
	 * @param title of some "panel node"
	 * @return the panel node
	 */
	public DefaultMutableTreeNode getNode(String title){
		return PanelWithElements.getInstance().getNode(title);
	}
	/**
	 * Add parentPanel ("<panel>" or "<listpanel>")
	 * @param p some ParentPanel instance
	 */
	public void addParentPanel(ParentPanel p){
		BigRootPanel bigRootPanel= BigRootPanel.getInstance();
		bigRootPanel.add(p);
	}
	/**
	 * Remove some instance of ParentPanel class
	 * @param p ParentPanel instance
	 */
	public void removeParentPanel(ParentPanel p){
		BigRootPanel bigRootPanel= BigRootPanel.getInstance();
		bigRootPanel.deleteParentPanel(p);
	}
	/**
	 * Refresh all parentPanels
	 * @param parentPanels list with all showed parentPanels
	 */
	public void refresh(LinkedList <ParentPanel> parentPanels){
		BigRootPanel bigRootPanel= BigRootPanel.getInstance();
		bigRootPanel.refresh(parentPanels);
	}
	/**
	 * Change name of node in the tree structure
	 * @param node which name should be changed
	 * @param input new name
	 * @param type type of element (e.g. Panel)
	 */
	public void changeNameOfNode(DefaultMutableTreeNode node, String input, String type){
		PanelWithElements.getInstance().changeNameOfNode(node,input,type);
	}
}
