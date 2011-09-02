package view.Elements;
import view.Buttons.*;
import view.LowLevelPanels.BigPanel;
import view.LowLevelPanels.BigRootPanel;
import view.LowLevelPanels.ParentPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EtchedBorder;


@SuppressWarnings("serial")
public class PanelG extends JPanel {
	String title;
	BigPanel parent;
//	BigRootPanel bRP;
	public PanelG(String title , BigPanel parent){
		this.parent=parent;
		this.title=title;
		
		this.setLayout( new BoxLayout(this, BoxLayout.X_AXIS) );
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setBorder(new EtchedBorder(Color.blue,  Color.yellow));
		Color color= new Color(219,230,236);
		this.setBackground(color);
		
		
		JLabel jl=new JLabel("<Panel>");
		this.add(Box.createRigidArea(new Dimension(40,0)));
		this.add(jl);		
		this.add(Box.createRigidArea(new Dimension(79,0)));
		
		JLabel jTitle=new JLabel("Title: ");
		jTitle.setFont(new Font("Arial", Font.BOLD, 13));
		this.add(jTitle);
		final JTextField titleField=new JTextField(title);
		titleField.setFont(new Font("Arial", Font.BOLD, 13));
		titleField.setMaximumSize(new Dimension(150,25));
		
		
		ActionListener titleFieldListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input=titleField.getText();
//				System.out.println("text=="+input);
				setTitle(input);
				
			} };
		titleField.addActionListener(titleFieldListener);

		this.add(Box.createRigidArea(new Dimension(55,0)));
		this.add(titleField);
		this.add(Box.createRigidArea(new Dimension(285,0)));
		
		NavigationButtonForBigPanels nb= new NavigationButtonForBigPanels(parent);
		this.add(nb);
		
		this.add(Box.createRigidArea(new Dimension(30,0)));
		RollButtons rb= new RollButtons(parent);
		this.add(rb);
		DeleteButtonForPanel db= new DeleteButtonForPanel(parent);
		this.add(db);
		this.setMaximumSize(new Dimension(904,50));

	}
	public String getTitle(){
		return title;
	}
	
	private void setTitle(String input){
		parent.setTitle(input);
//		System.out.println("text=="+this.title);
	}

}
