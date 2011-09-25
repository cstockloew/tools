package org.universaal.tools.configurationExtractor.view.LowLevelPanels;

import java.awt.Color;

import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.universaal.tools.configurationExtractor.view.Elements.Panels;
import org.universaal.tools.configurationExtractor.view.Elements.Root;

@SuppressWarnings("serial")
public class BigRootPanel extends JPanel {

private static final BigRootPanel bRP =new BigRootPanel();	
private	LinkedList <ParentPanel> parentPanels = new LinkedList<ParentPanel>();
private Root root;
private Panels panels;
	/**
	 * Constructor is private because of singleton implementation of this class
	 */
	private BigRootPanel(){
		super.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
	}
	/**
	 * get <root>
	 * @return Root of use case
	 */
	public Root getRoot(){
		return bRP.root;
	}
	
	/**
	 * Set <root>
	 * @param r instance of Root 
	 */
	public void setRoot(Root r){
		root=r;
		root.setVisible(false);
		this.add(root);
		root.setVisible(true);
		panels=new Panels();
		this.add(panels);			
	}
	/**
	 * Delete root. This method will be needed for removing all history of CE
	 */
	public void removeRoot(){
		this.remove(this.root);
		this.root=null;
		this.remove(panels);
		this.panels=null;
	}
	/**
	 * Add new parent panel
	 * @param p parent panel
	 */
	public void addParentPanel(ParentPanel p){
		bRP.add(p);
		p.setVisible(false);
		p.setVisible(true);
	}
	/**
	 * Remove given parent panel
	 * @param parent panel, which will be deleted
	 */
	public void deleteParentPanel(ParentPanel parent){
		parent.setVisible(false);
		bRP.remove(parent);
	}

	/**
	 * Refreshing of all parent panels 
	 * @param pPanels list with all parent panels
	 */
	public void refresh(LinkedList <ParentPanel> pPanels){
		parentPanels= pPanels;
	
		for(int i=0;i<parentPanels.size();i++){
			bRP.remove(parentPanels.get(i));
		}
		for(int i=0;i<parentPanels.size();i++){
			bRP.add(parentPanels.get(i));
		}
		for(int i=0;i<parentPanels.size();i++){
			parentPanels.get(i).setVisible(false);
		}
		for(int i=0;i<parentPanels.size();i++){
			parentPanels.get(i).setVisible(true);
		}
	}

	/**
	 * Typical singleton method
	 * @return
	 */
	public static BigRootPanel getInstance(){
		return bRP;
	}
	
	






}
