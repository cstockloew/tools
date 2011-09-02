package view.Elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class Panels extends JPanel {

	
	public Panels(){
		
		this.setLayout( new BoxLayout(this, BoxLayout.X_AXIS) );
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
//		this.setBorder(new EtchedBorder(Color.blue,  Color.yellow));
		this.setBorder(new EtchedBorder(Color.LIGHT_GRAY,  Color.LIGHT_GRAY));
		JLabel jl=new JLabel("<Panels>");
		this.add(Box.createRigidArea(new Dimension(20,0)));
		this.add(jl);
		this.setMaximumSize(new Dimension(918,25));
		Color color= new Color(230,233,220);
		this.setBackground(color);
	}
	

}
