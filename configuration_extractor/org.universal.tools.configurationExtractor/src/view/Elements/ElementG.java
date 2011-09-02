package view.Elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;


import utility.Constants;
import view.LowLevelPanels.ParentPanel;

@SuppressWarnings("serial")
public class ElementG extends BigElement {
	String type;
	String label;
	String standartV;
	String domain;
	String id;

	JPanel firstPanel;
	JPanel secondPanel;
	
	ParentPanel parent;
	
	public ElementG(String type, String label,String title, String standartV,String domain, String id, ParentPanel parent){
		super(title,parent);
		this.type=type;
		this.label=label;
		this.standartV=standartV;
		this.domain=domain;
		this.id=id;
		this.parent=parent;
		
		Color color= new Color(242,238,219);
//		this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );		
		this.setBorder(new EtchedBorder(Color.black,Color.black));		
//		this.setPreferredSize(new Dimension(200, 50));
		
		JPanel mainPanel=new JPanel();
		mainPanel.setLayout( new BoxLayout(mainPanel, BoxLayout.X_AXIS) );
		mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		mainPanel.setBackground(color);
//		mainPanel.setBorder(new EtchedBorder(Color.red,  Color.red));
		
//		mainPanel.setMaximumSize(new java.awt.Dimension(100, 20));	
		
		firstPanel=new JPanel();
		firstPanel.setLayout( new BoxLayout(firstPanel, BoxLayout.X_AXIS) );
		firstPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		firstPanel.setBackground(color);
//		firstPanel.setBorder(new EtchedBorder(Color.red,  Color.red));
		
//		firstPanel.setMaximumSize(new java.awt.Dimension(100, 20));
		
		secondPanel=new JPanel();
		secondPanel.setLayout( new BoxLayout(secondPanel, BoxLayout.X_AXIS) );
		secondPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		secondPanel.setBackground(color);
//		secondPanel.setBorder(new EtchedBorder(Color.red,  Color.red));

//		secondPanel.setMaximumSize(new java.awt.Dimension(100, 20));
		JLabel jl=new JLabel("<Element>");
		
		mainPanel.add(Box.createHorizontalStrut(55));
		mainPanel.add(jl);	

		mainPanel.add(Box.createHorizontalStrut(52));
		
	
		
		JLabel idLabel=new JLabel("ID: "+id);
		idLabel.setFont(new Font("Arial", Font.BOLD, 12));
		JPanel idPanel=new JPanel();
//		idPanel.setBackground(Color.PINK);
		idPanel.setBackground(color);
		idPanel.setLayout( new BoxLayout(idPanel, BoxLayout.X_AXIS) );
		idPanel.setMaximumSize(new Dimension(400,50));
		idPanel.add(idLabel);
		mainPanel.add(idPanel);
//		mainPanel.add(idLabel);

		
		mainPanel.add(Box.createHorizontalStrut(150));
	//	nb.setBounds( 300, 35, 150, 20 );
		mainPanel.add(nb);


		mainPanel.add(Box.createHorizontalStrut(30));
		
		ImageIcon minimize = new ImageIcon( Constants.MINIMIZE_ELEMENT_ICON );
		JButton min=new JButton(minimize);
		min.setMargin(new java.awt.Insets(0, 0, 0, 0));
		
		min.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  	  toMinimize();
				  }
				} );
		
		mainPanel.add(min);
		ImageIcon maximize = new ImageIcon( Constants.MAXIMIZE_ELEMENT_ICON );
		JButton max=new JButton(maximize);
		max.setMargin(new java.awt.Insets(0, 0, 0, 0));
		max.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  	  toMaximize();
				  }
				} );
		
		mainPanel.add(max);
		String helpMax = "Show parameters!";
		max.setToolTipText(helpMax);
		
		String helpMin = "Hide parameters!";
		min.setToolTipText(helpMin);
//		mainPanel.add(Box.createHorizontalStrut(20));
		
//		if(parent.getTypeOfPanel().equals("Panel ")){
			mainPanel.add(db);
//		}
	
		firstPanel.add(Box.createRigidArea(new Dimension(167,0)));
		
		
		JLabel jLabel=new JLabel("Label: ");
		firstPanel.add(jLabel);
		firstPanel.add(Box.createRigidArea(new Dimension(51,0)));

		final JTextField textLabel=new JTextField(label);
		textLabel.setMaximumSize(new Dimension(150,25));

		ActionListener labelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input=textLabel.getText();
				setLabel(input);
				
			} };
			textLabel.addActionListener(labelListener);
		
		firstPanel.add(textLabel);
		
		
		
//		firstPanel.add(new JButton("label"));
		firstPanel.add(Box.createRigidArea(new Dimension(40,0)));
		
		JLabel jHoverText=new JLabel("Hover Text: ");
		firstPanel.add(jHoverText);
		firstPanel.add(Box.createRigidArea(new Dimension(10,0)));
		final JTextField textHoverLabel=new JTextField(super.getTitle());
		textHoverLabel.setMaximumSize(new Dimension(150,25));
		
		ActionListener textHoverListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input=textHoverLabel.getText();
				setTitle(input);
				
			} };
		textHoverLabel.addActionListener(textHoverListener);
		firstPanel.add(textHoverLabel);
		firstPanel.add(Box.createRigidArea(new Dimension(20,0)));
		String typeSplit[]= type.split("\\.");
//		System.out.println(typeSplit.length);
		String typeEnd=typeSplit[typeSplit.length-1];
//		System.out.println(typeEnd);
		firstPanel.add(new JLabel("Type: "+typeEnd));

		
		
		secondPanel.add(Box.createRigidArea(new Dimension(165,0)));

		JLabel jstandartV=new JLabel("Standart Value: ");
		secondPanel.add(jstandartV);
		final JTextField textstandartV=new JTextField(standartV);
		textstandartV.setMaximumSize(new Dimension(150,25));
		
		
		
		ActionListener standardValueListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input=textstandartV.getText();
				setStandardV(input);
				
			} };
		textstandartV.addActionListener(standardValueListener);

		secondPanel.add(textstandartV);
		
		secondPanel.add(Box.createRigidArea(new Dimension(40,0)));
		
		String split[]= domain.split("/");
		String domainEnd=split[split.length-1];
		secondPanel.add(new JLabel("Domain:          "+domainEnd));		
		
		secondPanel.add(Box.createRigidArea(new Dimension(164,0)));

		
		this.add(mainPanel);
		this.add(firstPanel);
		this.add(secondPanel);
		
		
		mainPanel.setMaximumSize(new Dimension(900,35));
//		mainPanel.setMinimumSize(new Dimension(900,50));
		firstPanel.setMaximumSize(new Dimension(900,35));
		secondPanel.setMaximumSize(new Dimension(900,35));
		firstPanel.setVisible(false);
		secondPanel.setVisible(false);
//		this.setPreferredSize(new Dimension(800,150));
//		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT,JTabbedPane.SCROLL_TAB_LAYOUT);
//		
//		tabbedPane.addTab( "Reiter 1", mainPanel );
//		tabbedPane.addTab( "Reiter 2", firstPanel );
//		tabbedPane.addTab( "Reiter 3", secondPanel );
//		
//		tabbedPane.setMaximumSize(new Dimension(800, 60));
//
//		this.add(tabbedPane);
	}
	
	public void toMinimize(){
		System.out.println("minimize!");
		firstPanel.setVisible(false);
		secondPanel.setVisible(false);
	}
	
	public void toMaximize(){
		System.out.println("maximize!");
		firstPanel.setVisible(true);
		secondPanel.setVisible(true);
	}

	public String getElementType(){
		return "Element";
	}
	public String getId(){
		return id;
	}
	public String getTitle(){
		return getId();
	}
	
	public String getType(){
		return type;
	}public String getStandartV(){
		return standartV;
	}
	
	private void setStandardV(String input){
		this.standartV=input;
	}
	
	public String getDomain(){
		return domain;
	}
	public String getLabel(){
		return label;
	}
	
	private void setLabel(String input){
		this.label=input;
	}
}
