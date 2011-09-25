package org.universaal.tools.configurationExtractor.view.Elements;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import org.universaal.tools.configurationExtractor.view.Buttons.*;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.ParentPanel;

/**
 * 
 * @author Ilja
 * This class provides a BigElement instance, which symbolizes a abstract element (e.g. <elemet> or <list>) with all its attributes
 */
@SuppressWarnings("serial")
public class BigElement extends JPanel{

	private ParentPanel parent;
	private String title;
	protected NavigationButtonForElements nb;
	protected DeleteButtonForElements db;
	
	/**
	 * Constructor method
	 * @param title title of element
	 * @param parent parent panel of element(<panel> or <listpanel>)
	 */
	public BigElement(String title, ParentPanel parent){
		this.parent=parent;
		this.title=title;
		this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setBorder(new EtchedBorder(Color.black,  Color.black));
		nb= new NavigationButtonForElements(parent , this);
		db= new DeleteButtonForElements(parent,this);
	}
	/**
	 * Get parent panel
	 * @return parent panel of element
	 */
	public ParentPanel getParentPanel(){
		return parent;
	}
	
	/**
	 * Set parent panel of element
	 * @param p new parent panel of element
	 */
	public void setParentPanel(ParentPanel p){
		if(this.parent!=null){
			parent.deleteElement(this);
		}
		
		this.parent=p;
		nb.setParent(p);
		db.setParent(p);
		p.addElement(this);
	}
	
	/**
	 * Clone the BigElement
	 * @return the copy of BigElement
	 */
	public BigElement cloneBigElement(){
	       
		BigElement cloneElement = this;	        	       
	    return cloneElement;	       
	}
	
	/**
	 * Get title of element
	 * @return title of element
	 */
	public String getTitle(){
		return title;
	}
	/**
	 * Set new title 
	 * @param input new title
	 */
	public void	setTitle(String input){
		this.title=input;
	}
	
	/**
	 * get type of element 
	 * @return type of element
	 */
	public String getElementType(){
		return "Bigelement";
	}
	
	/**
	 * Get id
	 * @return id
	 */
	public String getId(){
		return "no Id";
	}
	
	/**
	 * Get type of GUI component
	 * @return
	 */
	public String getType(){
		return "no Type";
	}
	/**
	 * Get standard value 
	 * @return standard value 
	 */
	public String getStandartV(){
		return "no standartV";
	}
	/**
	 * Get limit
	 * @return limit
	 */
	public int getLimit(){
		return 0;
	}
	
	/**
	 * Get domain
	 * @return domain
	 */
	public String getDomain(){
		return "no Domain";
	}
	/**
	 * Get label
	 * @return label
	 */
	public String getLabel(){
		return "no Label";
	}
	
}
