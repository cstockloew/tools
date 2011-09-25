package org.universaal.tools.configurationExtractor.view.LowLevelPanels;

import java.awt.Color;
import javax.swing.BorderFactory;
import org.universaal.tools.configurationExtractor.view.Elements.PanelG;

/**
 * 
 * @author Ilja
 * This class provides  <panel> element with all its <elements>
 */
@SuppressWarnings("serial")
public class BigPanel extends ParentPanel {

	private String title;
	/**
	 * Constructor
	 * @param title title of <panel>
	 */
	public BigPanel(String title){
		super(title);
		this.title=title;
		PanelG p=new PanelG(title,this);
		this.add(p);
		Color lightBlue = new Color(161,190,230);
		this.setBorder(BorderFactory.createLineBorder(lightBlue,7));
	}

	/**
	 * Get type of panel
	 * @return type of panel
	 */
	@Override
	public String getTypeOfPanel(){
		return "Panel ";
	}
	
	/**
	 * Get title of <panel>
	 * @return title of <panel>
	 */
	@Override
	public String getTitle(){
		return title;
	}
	/**
	 * Set new title of <panel>
	 * @param input new title
	 */
	public void setTitle(String input){
		this.title=input;
		changeTitleOfNode(input,getTypeOfPanel());
		
	}
}
