package view.LowLevelPanels;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.tree.DefaultMutableTreeNode;

import model.xml.PanelWithElements;

import controller.GuiControl;

import view.Elements.ListG;
import view.Elements.ListPanel;
import view.MainPanels.TreePanel;

@SuppressWarnings("serial")
public class BigListPanel extends ParentPanel {
	private GuiControl guiControl = GuiControl.getInstance();
//	String title;
	String id;
	PanelWithElements panelWithElements;
	ListG list;
//	@ListPanel(id = "Geraete",label="Geräte",hoverText="Geräte auswählen",limit=-1,uRI="http://www.openaal.org/SAM/Ontology/highLevelThing#Device|http://www.openaal.org/SAM/Ontology/highLevelThing#Oven")
	public BigListPanel(String title , String id ,String label,int limit,String uRI){
		super(title);
		this.id=id;
		this.title=title;
		ListPanel lp=new ListPanel(title,id,this);
		this.add(lp);
	//	String label,String title, int limit,String domain,ParentPanel parent
		this.panelWithElements=PanelWithElements.getInstance();
		list = new ListG(label,title,limit,uRI,this);
		this.addElement(list);
		Color lightGreen = new Color(145,237,162);
		this.setBorder(BorderFactory.createLineBorder(lightGreen,7));
		

	}

	public void addListLeaf(){
		
		DefaultMutableTreeNode child=new DefaultMutableTreeNode(list.getElementType() +" "+ list.getTitle());
//		System.out.println("bRP=================="+bRP);
		guiControl.addLeaf(child, panelWithElements.getNode(title));
	}
//	void addList(ListG l){
//		this.add(l);
//		addElement(l);
//	}
//	void deleteList(ListG l){
//		this.remove(l);
//		deleteElement(l);
//	}

	public String getTitle(){
		return getId();
	}
	
	public String getId(){
		return id;
	}

	public String getTypeOfPanel(){
		return "Listpanel ";
	}
}
