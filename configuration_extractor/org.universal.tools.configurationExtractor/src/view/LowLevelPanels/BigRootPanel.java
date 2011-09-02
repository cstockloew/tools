package view.LowLevelPanels;

import java.awt.Color;

import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import javax.swing.border.EtchedBorder;
import controller.MainPanelControl;

import view.Elements.Panels;
import view.Elements.Root;

@SuppressWarnings("serial")
public class BigRootPanel extends JPanel {

private static final BigRootPanel bRP =new BigRootPanel();	
private MainPanelControl mpControl= MainPanelControl.getInstance();	
private	LinkedList <ParentPanel> parentPanels = new LinkedList<ParentPanel>();
private Root root;
private Panels panels;
	
	private BigRootPanel(){
		super.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
//		this.setBorder(new EtchedBorder(Color.black,  Color.black));
//		Panels p=new Panels();
//		this.add(p);
//		this.root=new Root("id","name","title","info");
//		this.add(root);
		Color lightBlue = new Color(245,243,229);
	
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
	}
	
	public Root getRoot(){
		return bRP.root;
	}
	
	public void setRoot(Root r){
//		System.out.println("root---------"+r);
		root=r;
		root.setVisible(false);
		this.add(root);
		root.setVisible(true);
		panels=new Panels();
		this.add(panels);
		
		
	}
	public void removeRoot(){
		this.remove(this.root);
		this.root=null;
		this.remove(panels);
		this.panels=null;
//		parentPanels.clear();
	}
	
	public void addParentPanel(ParentPanel p){
		bRP.add(p);
		p.setVisible(false);
		p.setVisible(true);
	}
	public void deleteParentPanel(ParentPanel parent){
		parent.setVisible(false);
		bRP.remove(parent);
	}

	public void refresh(LinkedList <ParentPanel> pPanels){
		
		parentPanels= pPanels;
	
		//andern
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
	
//	public void changeTwoParentPanels(ParentPanel p1 , ParentPanel p2){
//					
//		p1.setVisible(false);
//		p2.setVisible(false);
//		ParentPanel help=p1.cloneParentPanel();
//		
//		int indexOfFirst=parentPanels.indexOf(p1);
//		int indexOfSecond=parentPanels.indexOf(p2);
//		
//		parentPanels.set(indexOfFirst, p2);
//		
//		parentPanels.set(indexOfSecond, help);
//		p1.setVisible(true);
//		p2.setVisible(true);
//		refresh();
//
//
//	}

	public static BigRootPanel getInstance(){
		return bRP;
	}
	
	






}
