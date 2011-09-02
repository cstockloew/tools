package view.Elements;
import view.Buttons.*;
import view.LowLevelPanels.BigRootPanel;
import view.LowLevelPanels.ParentPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import model.xml.PanelWithElements;


@SuppressWarnings("serial")
public class ListPanel extends JPanel {
	private String title;
	private String id;
	private ParentPanel parent;
	private RollButtons rb;
//	private BigRootPanel bRP;
	private PanelWithElements panelWithElements= PanelWithElements.getInstance();
	
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
//		this.add(Box.createRigidArea(new Dimension(30,0)));
//		this.add(new JButton(title));
//		this.add(Box.createRigidArea(new Dimension(60,0)));
		JLabel idLabel=new JLabel("ID:                  "+id);
		idLabel.setFont(new Font("Arial", Font.BOLD, 13));
		JPanel idPanel=new JPanel();
//		idPanel.setMaximumSize(new Dimension(500,50));
		idPanel.setBackground(color);
//		idPanel.setBackground(Color.PINK);
		idPanel.setLayout( new BoxLayout(idPanel, BoxLayout.X_AXIS) );
		idPanel.setMaximumSize(new Dimension(400,50));
		idPanel.add(idLabel);
		this.add(Box.createRigidArea(new Dimension(61,0)));
		this.add(idPanel);
		this.add(Box.createRigidArea(new Dimension(125,0)));
//		AddElementButton addB= new AddElementButton(parent);
//		this.add(addB);
//		this.add(Box.createRigidArea(new Dimension(255,0)));
		NavigationButtonForBigPanels nb= new NavigationButtonForBigPanels(parent);
		this.add(nb);
		this.add(Box.createRigidArea(new Dimension(30,0)));
		rb= new RollButtons(parent);
		this.add(rb);
		this.setMaximumSize(new Dimension(904,50));
	}
	
	public String getId(){
		return id;
	}
	public String getTitle(){
		return title;
	}
	
	
}
