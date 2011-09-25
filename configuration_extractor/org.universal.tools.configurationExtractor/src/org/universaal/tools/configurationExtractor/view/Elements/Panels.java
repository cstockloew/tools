package org.universaal.tools.configurationExtractor.view.Elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * 
 * @author Ilja
 * This class provides a special <panels> element, which contains all <panel> and <listpanel> elements
 */
@SuppressWarnings("serial")
public class Panels extends JPanel {
	/**
	 * Constructor
	 */
	public Panels(){
		this.setLayout( new BoxLayout(this, BoxLayout.X_AXIS) );
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setBorder(new EtchedBorder(Color.LIGHT_GRAY,  Color.LIGHT_GRAY));
		JLabel jl=new JLabel("<Panels>");
		this.add(Box.createRigidArea(new Dimension(20,0)));
		this.add(jl);
		this.setMaximumSize(new Dimension(918,25));
		Color color= new Color(230,233,220);
		this.setBackground(color);
	}
	

}
