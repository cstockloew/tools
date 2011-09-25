package org.universaal.tools.configurationExtractor.view.LowLevelPanels;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.tree.DefaultMutableTreeNode;
import org.universaal.tools.configurationExtractor.model.xml.PanelWithElements;
import org.universaal.tools.configurationExtractor.controller.GuiControl;
import org.universaal.tools.configurationExtractor.view.Elements.ListG;
import org.universaal.tools.configurationExtractor.view.Elements.ListPanel;

/**
 * 
 * @author Ilja
 * BigListPanel provides a <listpanel> element with all its elements.
 */
@SuppressWarnings("serial")
public class BigListPanel extends ParentPanel {
	private GuiControl guiControl = GuiControl.getInstance();
	private String id;
	private PanelWithElements panelWithElements;
	private ListG list;

	/**
	 * Constructor
	 * @param title of <list>
	 * @param id of <listpanel>
	 * @param label of <list>
	 * @param limit of <list>
	 * @param uRI of <list>
	 */
	public BigListPanel(String title , String id ,String label,int limit,String uRI){
		super(title);
		this.id=id;
		this.title=title;
		ListPanel lp=new ListPanel(title,id,this);
		this.add(lp);
		this.panelWithElements=PanelWithElements.getInstance();
		list = new ListG(label,title,limit,uRI,this);
		this.addElement(list);
		Color lightGreen = new Color(145,237,162);
		this.setBorder(BorderFactory.createLineBorder(lightGreen,7));
	}
	
	/**
	 * This method adds a list leaf to listpanel in the tree perspective
	 */
	public void addListLeaf(){		
		DefaultMutableTreeNode child=new DefaultMutableTreeNode(list.getElementType() +" "+ list.getTitle());
		guiControl.addLeaf(child, panelWithElements.getNode(title));
	}

	/**
	 * Get title of <listpanel> (title of listpanel = id of listpanel)
	 * @return title 
	 */
	@Override
	public String getTitle(){
		return getId();
	}
	
	/**
	 * Get id of listpanel
	 * @return id
	 */
	public String getId(){
		return id;
	}

	/**
	 * Get type of panel
	 * @return type of panel 
	 */
	@Override
	public String getTypeOfPanel(){
		return "Listpanel ";
	}
}
