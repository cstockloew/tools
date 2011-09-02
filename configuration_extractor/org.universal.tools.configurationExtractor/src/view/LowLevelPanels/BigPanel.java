package view.LowLevelPanels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;

import view.Elements.PanelG;


@SuppressWarnings("serial")
public class BigPanel extends ParentPanel {

	String title;
	public BigPanel(String title){
		super(title);
		this.title=title;
		PanelG p=new PanelG(title,this);
		this.add(p);
		Color lightBlue = new Color(161,190,230);
		this.setBorder(BorderFactory.createLineBorder(lightBlue,7));
	}

	
	public String getTypeOfPanel(){
		return "Panel ";
	}
	
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String input){
		System.out.println("bigpanel.setTitle()");
		this.title=input;
		changeTitleOfNode(input,getTypeOfPanel());
		
	}
}
