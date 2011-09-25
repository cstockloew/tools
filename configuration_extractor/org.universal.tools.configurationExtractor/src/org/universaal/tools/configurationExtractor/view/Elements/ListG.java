package org.universaal.tools.configurationExtractor.view.Elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.ParentPanel;

/**
 * 
 * @author Ilja
 * This class provides a ListG instance, which symbolizes a <list> with all its attributes
 */
@SuppressWarnings("serial")
public class ListG  extends BigElement {
	private String label;
	private int limit;
	private int initialLimit;
	private String domain;
	private JPanel firstPanel;
	private JPanel secondPanel;
	
	/**
	 * Constructor
	 * @param label label of <list>
	 * @param title title of <list>
	 * @param limit limit of <list>
	 * @param domain domain of <list>
	 * @param parent parent panel of <list>
	 */
	public ListG(String label,String title, int limit,String domain,ParentPanel parent){
		super(title,parent);
		this.label=label;
		this.limit=limit;
		this.initialLimit=limit;
		this.domain=domain;
		Color color = new Color(238,252,226);
		this.setBackground(color);
		this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		this.setBorder(new EtchedBorder(Color.black,Color.black));	

		firstPanel=new JPanel();
		firstPanel.setLayout( new BoxLayout(firstPanel, BoxLayout.X_AXIS) );
		firstPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		firstPanel.setBackground(color);
		secondPanel=new JPanel();
		secondPanel.setLayout( new BoxLayout(secondPanel, BoxLayout.X_AXIS) );
		secondPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		secondPanel.setBackground(color);
		
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
				 String input =((JFormattedTextField)e.getSource()).getText().toString();
				 int inp=Integer.parseInt(input);
				 if (inp < -1){
					 JOptionPane.showMessageDialog( null, "Sorry, but limit has to be equal or greater as -1!" );
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

		this.add(Box.createRigidArea(new Dimension(0,2)));
		this.add(firstPanel);
		this.add(Box.createRigidArea(new Dimension(0,8)));
		this.add(secondPanel);
		this.setMaximumSize(new Dimension(904,55));
	}
	
	 
	/**
	 * get type of element 
	 * @return type of element
	 */
	@Override
	public String getElementType(){
		return "List";
	}
	/**
	 * Get title
	 */
	@Override
	public String getTitle(){
		return getLabel();
	}
	/**
	 * Get limit
	 */
	@Override
	public int getLimit(){
		return limit;
	}
	/**
	 * Set limit
	 * @param newLimit new limit
	 */
	public void setLimit(int newLimit){
		this.limit= newLimit;
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
	/**
	 * Set label
	 * @param input new label
	 */
	private void setLabel(String input){
		this.label=input;
	}
	/**
	 * 
	 * Hide all subpanels
	 */
	public void toMinimize(){
		secondPanel.setVisible(false);
	}
	/**
	 * Show all subpanels
	 */
	public void toMaximize(){
		secondPanel.setVisible(true);
	}
}
