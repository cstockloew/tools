package org.universaal.tools.configurationExtractor.view.Elements;
import org.universaal.tools.configurationExtractor.view.Buttons.*;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.ParentPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * 
 * @author Ilja
 * This class is the part of whole listpanel(without <list> and <elements>)
 */
@SuppressWarnings("serial")
public class ListPanel extends JPanel {
	private String title;
	private String id;
	private ParentPanel parent;
	private RollButtons rb;
	
	/**
	 * Constructor
	 * @param title title of <listpanel>
	 * @param id id of <listpanel>
	 * @param parent parent panel of <listpanel>
	 */
	public ListPanel(String title, String id , ParentPanel parent){
		this.parent=parent;
		this.title= title;
		this.id=id;
		this.setLayout( new BoxLayout(this, BoxLayout.X_AXIS) );
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setBorder(new EtchedBorder(Color.blue,  Color.yellow));
		Color color= new Color(205,233,202);
		this.setBackground(color);
	
		JLabel jl=new JLabel("<Listpanel>");
		
		this.add(Box.createRigidArea(new Dimension(40,0)));
		this.add(jl);		
		JLabel idLabel=new JLabel("ID:                  "+id);
		idLabel.setFont(new Font("Arial", Font.BOLD, 13));
		JPanel idPanel=new JPanel();
		idPanel.setBackground(color);
		idPanel.setLayout( new BoxLayout(idPanel, BoxLayout.X_AXIS) );
		idPanel.setMaximumSize(new Dimension(400,50));
		idPanel.add(idLabel);
		this.add(Box.createRigidArea(new Dimension(61,0)));
		this.add(idPanel);
		this.add(Box.createRigidArea(new Dimension(125,0)));
		NavigationButtonForBigPanels nb= new NavigationButtonForBigPanels(parent);
		this.add(nb);
		this.add(Box.createRigidArea(new Dimension(30,0)));
		rb= new RollButtons(parent);
		this.add(rb);
		this.setMaximumSize(new Dimension(904,50));
	}
	
	/**
	 * Get id of <listpanel>
	 * @return id
	 */
	public String getId(){
		return id;
	}
	/**
	 * Get title of <listpanel>
	 * @return title
	 */
	public String getTitle(){
		return title;
	}
	
	
}
