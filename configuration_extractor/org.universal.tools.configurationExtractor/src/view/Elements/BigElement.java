package view.Elements;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


import view.Buttons.*;
import view.LowLevelPanels.ParentPanel;

@SuppressWarnings("serial")
public class BigElement extends JPanel{


	ParentPanel parent;
	String title;
	NavigationButtonForElements nb;
	DeleteButtonForElements db;
	public BigElement(String title, ParentPanel parent){
		this.parent=parent;
		this.title=title;

		this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setBorder(new EtchedBorder(Color.black,  Color.black));
		nb= new NavigationButtonForElements(parent , this);
		db= new DeleteButtonForElements(parent,this);
	}

	public ParentPanel getParentPanel(){
		return parent;
	}
	public void setParentPanel(ParentPanel p){
		if(this.parent!=null){
			parent.deleteElement(this);
		}
		
		this.parent=p;
		nb.setParent(p);
		db.setParent(p);
	//	nb= new NavigationButtonForElements(parent , this);
	//	p.getElementsList().add(this);
		
		p.addElement(this);
	}
	public BigElement cloneBigElement(){
	       
		BigElement cloneElement = this;	        	       
	    return cloneElement;	       
	}
	public String getTitle(){
		return title;
	}
	public void
	setTitle(String input){
		this.title=input;
	}
	public String getElementType(){
		return "Bigelement";
	}
	

	public String getId(){
		return "no Id";
	}
	public String getType(){
		return "no Type";
	}public String getstandartV(){
		return "no standartV";
	}
	public int getLimit(){
		return 0;
	}
	public String getDomain(){
		return "no Domain";
	}
	public String getLabel(){
		return "no Label";
	}
	
}
