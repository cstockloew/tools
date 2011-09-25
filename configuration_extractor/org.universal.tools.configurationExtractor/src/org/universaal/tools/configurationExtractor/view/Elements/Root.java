package org.universaal.tools.configurationExtractor.view.Elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import org.universaal.tools.configurationExtractor.view.Buttons.HelpButton;
/**
 * 
 * @author Ilja
 * The <root> element describes a important information about use case
 */
@SuppressWarnings("serial")
public class Root extends JPanel {
	private String name;
	private String title;
	private String help;
	private String id;
	
	/**
	 * Constructor
	 * @param id id of use case
	 * @param name of use case
	 * @param title of use case
	 * @param help help information about use case
	 */
	public Root(String id ,String name, String title, String help){
		this.name=name;
		this.help=help;
		this.title=title;
		this.id=id;
		
		this.setLayout( new BoxLayout(this, BoxLayout.X_AXIS) );
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setBorder(new EtchedBorder(Color.LIGHT_GRAY,  Color.LIGHT_GRAY));
		this.setMaximumSize(new Dimension(918,30));
		Color color= new Color(228,221,250);
		this.setBackground(color);
		
		JLabel jl=new JLabel("<Root>");
	
		this.add(jl);		
		this.add(Box.createRigidArea(new Dimension(133,0)));
		JLabel titleLabel=new JLabel("Title:              ");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 13));
		this.add(titleLabel);
		final JTextField titleField=new JTextField(title);
		titleField.setFont(new Font("Arial", Font.BOLD, 13));
		titleField.setMaximumSize(new Dimension(200,25));
		titleField.setMinimumSize(new Dimension(200,25));
		
		ActionListener fieldListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input=titleField.getText();
				setTitle(input);
				
			} };
		titleField.addActionListener(fieldListener);
		
		this.add(titleField);
		this.add(Box.createRigidArea(new Dimension(50,0)));
		JLabel nameLabel=new JLabel("Name: ");
		nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
		this.add(nameLabel);
		final JTextField nameField=new JTextField(name);
		nameField.setFont(new Font("Arial", Font.BOLD, 13));
		nameField.setMaximumSize(new Dimension(200,25));
		nameField.setMinimumSize(new Dimension(200,25));
		
		ActionListener nameListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input=nameField.getText();
				changeName(input);
				
			} };
		nameField.addActionListener(nameListener);
		this.add(nameField);
		this.add(Box.createRigidArea(new Dimension(98,0)));
		
		HelpButton h= new HelpButton("info",this);
		this.add(h);
	}
	
	/**
	 * Get name
	 * @return name of use case
	 */
	public String getName(){
		return name;
	}
	/*
	 * change name of use case
	 */
	private void changeName(String input){
		this.name=input;
	}
	/**
	 * Get if of use case
	 * @return id
	 */
	public String getId(){
		return id;
	}
	/**
	 * Set id of use case
	 * @param newId new id
	 */
	public void setId(String newId){
		this.id=newId;
	}
	
	/**
	 * Get title of use case
	 * @return title
	 */
	public String getTitle(){
		return title;
	}
	/**
	 * Set title of use case
	 * @param newTitle new title of use case
	 */
	public void setTitle(String newTitle){
		title=newTitle;
	}
	/**
	 * Get current help information of use case
	 * @return current help information
	 */
	public String getHelp(){
		return help;
	}
	/**
	 * Set new help information of use case
	 * @param s new help information
	 */
	public void setHelp(String s){
		this.help=s;
	}
	
}
