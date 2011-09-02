package view.MainPanels;


import java.awt.Color;
import java.awt.Component;

import java.awt.Dimension;



import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import view.Elements.Panels;
import view.Elements.Root;
import view.LowLevelPanels.BigRootPanel;

@SuppressWarnings("serial")
public class CentralPanel extends JPanel{
		
	public CentralPanel(){
//		this.setPreferredSize(new Dimension(width, height));
		
//		this.setBackground(color);
		this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		init();
	}

	void init(){
//		Color color= new Color(242,238,219);
		JPanel cPanel=new JPanel();
//		cPanel.setBackground(color);
		cPanel.setLayout( new BoxLayout(cPanel, BoxLayout.Y_AXIS) );
//		cPanel.setBorder(new EtchedBorder(Color.MAGENTA,  Color.MAGENTA));
		JLabel jl =new JLabel("Configuration-Panel");
		jl.setBackground(Color.DARK_GRAY);
//		JPanel labelPanel=new JPanel();
//		labelPanel.add(jl);
		
//		labelPanel.setBorder(new EtchedBorder(Color.black,  Color.black));
//		System.out.println("width=="+cPanel.getWidth());
//		labelPanel.setMaximumSize(new Dimension(width,25));
		
		cPanel.add(jl);
//		jl.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		BigRootPanel bigRootPanel= BigRootPanel.getInstance();
		cPanel.add(bigRootPanel);
		
		JScrollPane cP= new JScrollPane();
//		cP.add(cPanel);
		cP.setViewportView(cPanel);
		this.add(cP);

		
	}
	
	
	
}
