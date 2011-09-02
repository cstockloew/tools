package view.Elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import view.Buttons.ExportButton;
import view.Buttons.HelpButton;
import view.Buttons.ImportButton;

@SuppressWarnings("serial")
public class Root extends JPanel {
	String name;
	String title;
	String help;
	String id;
	String information;
	
	public Root(String id ,String name, String title, String help){
		this.name=name;
		this.help=help;
		this.title=title;
		this.id=id;
		this.information="";
		
		this.setLayout( new BoxLayout(this, BoxLayout.X_AXIS) );
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setBorder(new EtchedBorder(Color.LIGHT_GRAY,  Color.LIGHT_GRAY));
//		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,7));
		this.setMaximumSize(new Dimension(918,30));
		Color color= new Color(228,221,250);
		this.setBackground(color);
		
		JLabel jl=new JLabel("<Root>");
	
		this.add(jl);		
		this.add(Box.createRigidArea(new Dimension(133,0)));
//		this.add(new JButton(id));
//		this.add(Box.createRigidArea(new Dimension(10,0)));
		JLabel titleLabel=new JLabel("Title:              ");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 13));
		this.add(titleLabel);
		final JTextField titleField=new JTextField(title);
		titleField.setFont(new Font("Arial", Font.BOLD, 13));
		titleField.setMaximumSize(new Dimension(450,25));
		
		
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
		nameField.setMaximumSize(new Dimension(450,25));
		nameField.setMinimumSize(new Dimension(150,25));
		
		ActionListener nameListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input=nameField.getText();
				changeName(input);
				
			} };
		nameField.addActionListener(nameListener);
		this.add(nameField);
		this.add(Box.createRigidArea(new Dimension(250,0)));
		
		HelpButton h= new HelpButton("info",this);
		this.add(h);
	}
	public String getName(){
		return name;
	}
	private void changeName(String input){
		this.name=input;
	}
	public String getId(){
		return id;
	}
	
	public void setId(String newId){
		this.id=newId;
	}
	
	public String getTitle(){
		return title;
	}
	public void setTitle(String newTitle){
		title=newTitle;
	}
	public String getHelp(){
		return help;
	}
	
	public void setInformation(String s){
		this.information=s;
	}
	public String getInformation(){
		return this.information;
	}
	
}
