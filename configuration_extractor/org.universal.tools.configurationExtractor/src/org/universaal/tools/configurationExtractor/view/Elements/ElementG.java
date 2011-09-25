package org.universaal.tools.configurationExtractor.view.Elements;

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
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import org.universaal.tools.configurationExtractor.utility.Constants;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.ParentPanel;

/**
 * 
 * @author Ilja
 * This class provides a ElementG instance, which symbolizes a <element> with all its attributes
 */
@SuppressWarnings("serial")
public class ElementG extends BigElement {
	private String type;
	private String label;
	private String standartV;
	private String domain;
	private String id;
	private JPanel firstPanel;
	private JPanel secondPanel;
	private ParentPanel parent;
	
	/**
	 * Constructor
	 * @param type type of <element>
	 * @param label label of <element>
	 * @param title title of <element>
	 * @param standartV standard value of <element>
	 * @param domain domain of <element>
	 * @param id id of <element>
	 * @param parent parent panel of <element>
	 */
	public ElementG(String type, String label,String title, String standartV,String domain, String id, ParentPanel parent){
		super(title,parent);
		this.type=type;
		this.label=label;
		this.standartV=standartV;
		this.domain=domain;
		this.id=id;
		this.parent=parent;
		
		Color color= new Color(242,238,219);
		this.setBorder(new EtchedBorder(Color.black,Color.black));		
		JPanel mainPanel=new JPanel();
		mainPanel.setLayout( new BoxLayout(mainPanel, BoxLayout.X_AXIS) );
		mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		mainPanel.setBackground(color);

		firstPanel=new JPanel();
		firstPanel.setLayout( new BoxLayout(firstPanel, BoxLayout.X_AXIS) );
		firstPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		firstPanel.setBackground(color);

		secondPanel=new JPanel();
		secondPanel.setLayout( new BoxLayout(secondPanel, BoxLayout.X_AXIS) );
		secondPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		secondPanel.setBackground(color);

		JLabel jl=new JLabel("<Element>");
		
		mainPanel.add(Box.createHorizontalStrut(55));
		mainPanel.add(jl);	

		mainPanel.add(Box.createHorizontalStrut(52));
				
		JLabel idLabel=new JLabel("ID: "+id);
		idLabel.setFont(new Font("Arial", Font.BOLD, 12));
		JPanel idPanel=new JPanel();
		idPanel.setBackground(color);
		idPanel.setLayout( new BoxLayout(idPanel, BoxLayout.X_AXIS) );
		idPanel.setMaximumSize(new Dimension(400,50));
		idPanel.add(idLabel);
		mainPanel.add(idPanel);
	
		mainPanel.add(Box.createHorizontalStrut(150));
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
		mainPanel.add(db);
	
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
		String typeEnd=typeSplit[typeSplit.length-1];
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
		firstPanel.setMaximumSize(new Dimension(900,35));
		secondPanel.setMaximumSize(new Dimension(900,35));
		firstPanel.setVisible(false);
		secondPanel.setVisible(false);
	}
	
	/**
	 * Hide all subpanels
	 */
	public void toMinimize(){
		firstPanel.setVisible(false);
		secondPanel.setVisible(false);
	}
	
	/**
	 * Show all subpanels 
	 */
	public void toMaximize(){
		firstPanel.setVisible(true);
		secondPanel.setVisible(true);
	}

	/**
	 * get type of element 
	 * @return type of element
	 */
	@Override
	public String getElementType(){
		return "Element";
	}
	/**
	 * Get id
	 */
	@Override
	public String getId(){
		return id;
	}
	/**
	 * Get title
	 */
	@Override
	public String getTitle(){
		return getId();
	}
	/**
	 *  Get type of GUI component
	 */
	@Override
	public String getType(){
		return type;
	}
	/**
	 * Get standard value 
	 */
	@Override
	public String getStandartV(){
		return standartV;
	}
	/*
	 * Set standard value
	 */
	private void setStandardV(String input){
		this.standartV=input;
	}
	/**
	 * Get domain
	 */
	@Override
	public String getDomain(){
		return domain;
	}
	/**
	 * Get label
	 */
	@Override
	public String getLabel(){
		return label;
	}
	
	/*
	 * Set label
	 */
	private void setLabel(String input){
		this.label=input;
	}
}
