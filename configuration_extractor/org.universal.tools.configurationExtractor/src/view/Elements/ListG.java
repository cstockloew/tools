package view.Elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import view.LowLevelPanels.ParentPanel;

@SuppressWarnings("serial")
public class ListG  extends BigElement {
	String label;
	int limit;
	int initialLimit;
	String domain;
	
	JPanel firstPanel;
	JPanel secondPanel;

	
	public ListG(String label,String title, int limit,String domain,ParentPanel parent){
		super(title,parent);
//		this.setPreferredSize(new Dimension(100,100));
		this.label=label;

		this.limit=limit;
		this.initialLimit=limit;
		
		this.domain=domain;
		
		Color color = new Color(238,252,226);
		this.setBackground(color);
		this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
//		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setBorder(new EtchedBorder(Color.black,Color.black));	
		
	
		firstPanel=new JPanel();
		firstPanel.setLayout( new BoxLayout(firstPanel, BoxLayout.X_AXIS) );
		firstPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		firstPanel.setBackground(color);
//		firstPanel.setBorder(new EtchedBorder(Color.red,  Color.gray));
		
//		firstPanel.setMaximumSize(new java.awt.Dimension(100, 20));
		
		secondPanel=new JPanel();
		secondPanel.setLayout( new BoxLayout(secondPanel, BoxLayout.X_AXIS) );
		secondPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		secondPanel.setBackground(color);
//		secondPanel.setBorder(new EtchedBorder(Color.red,  Color.red));
		
		JLabel jl=new JLabel("<List>");
		firstPanel.add(Box.createRigidArea(new Dimension(60,0)));
		firstPanel.add(jl);		
		firstPanel.add(Box.createRigidArea(new Dimension(72,0)));

		firstPanel.add(new JLabel("Label: "));
		firstPanel.add(Box.createRigidArea(new Dimension(51,0)));
		final JTextField textLabel=new JTextField(""+label);
		textLabel.setMaximumSize(new Dimension(150,25));
		
		ActionListener labelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input=textLabel.getText();
				setLabel(input);
				
			} };
			textLabel.addActionListener(labelListener);
		
		firstPanel.add(textLabel);
		
		firstPanel.add(Box.createRigidArea(new Dimension(39,0)));
		
		firstPanel.add(new JLabel("Hover Text: "));
		firstPanel.add(Box.createRigidArea(new Dimension(10,0)));
		final JTextField textHoverLabel=new JTextField(""+title);
		
		ActionListener textHoverListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input=textHoverLabel.getText();
				setTitle(input);
				
			} };
			textHoverLabel.addActionListener(textHoverListener);
		
		firstPanel.add(textHoverLabel);
		textHoverLabel.setMaximumSize(new Dimension(150,25));
		
		
			
//		firstPanel.add(Box.createRigidArea(new Dimension(10,0)));
//		JButton name=new JButton(super.getTitle());
//		this.add(name);
		
		
		secondPanel.add(Box.createRigidArea(new Dimension(167,0)));
		secondPanel.add(new JLabel("Limit: "));
		secondPanel.add(Box.createRigidArea(new Dimension(54,0)));
		final JFormattedTextField jt=new JFormattedTextField(
			    new DecimalFormat("#") );
		jt.setValue(limit);
		jt.setMaximumSize(new Dimension(30,25));
		
		
		jt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	//			 System.out.println( ((JFormattedTextField)e.getSource()).getText() );
				 String input =((JFormattedTextField)e.getSource()).getText().toString();
				 int inp=Integer.parseInt(input);
				 if (inp < -1){
					 JOptionPane.showMessageDialog( null, "Sorry, but limit has to be equal or greater as -1!" );
//					 setLimitBack();
					 jt.setValue(initialLimit);
				 }
				
			} });
		secondPanel.add(jt);
		secondPanel.add(Box.createRigidArea(new Dimension(159,0)));
		
		String split[]= domain.split("/");
		String domainEnd=split[split.length-1];
		
		JLabel domainLabel=new JLabel("Domain:           ");
		
		secondPanel.add(domainLabel);
		secondPanel.add(new JLabel(""+domainEnd));
		
//		NavigationButtonForElements nb= new NavigationButtonForElements(parent , this);
//		this.add(nb);
//		this.add(Box.createRigidArea(new Dimension(30,0)));
//		DeleteButtonForElements db= new DeleteButtonForElements(parent,this);
//		this.add(db);
		this.add(Box.createRigidArea(new Dimension(0,2)));
		this.add(firstPanel);
		this.add(Box.createRigidArea(new Dimension(0,8)));
		this.add(secondPanel);
		
//		firstPanel.setMaximumSize(new Dimension(800,50));
//		secondPanel.setMaximumSize(new Dimension(800,50));
		this.setMaximumSize(new Dimension(904,55));
//		this.setPreferredSize(new Dimension(800,50));
//		secondPanel.setVisible(false);

	}
	public String getElementType(){
		return "List";
	}
	//override
	public String getTitle(){
		return getLabel();
	}

	public int getLimit(){
		return limit;
	}
	public void setLimit(int newLimit){
		this.limit= newLimit;
	}
//	private void setLimitBack(){
//		this.limit=initialLimit;
//	}
	public String getDomain(){
		return domain;
	}
	public String getLabel(){
		return label;
	}
	private void setLabel(String input){
		this.label=input;
	}
	public void toMinimize(){
		System.out.println("minimize!");
		secondPanel.setVisible(false);
	}
	
	public void toMaximize(){
		System.out.println("maximize!");
		secondPanel.setVisible(true);
	}
}
