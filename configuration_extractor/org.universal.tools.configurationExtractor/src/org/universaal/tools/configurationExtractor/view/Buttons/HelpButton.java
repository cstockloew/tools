package org.universaal.tools.configurationExtractor.view.Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.universaal.tools.configurationExtractor.view.Elements.Root;

/**
 * This class provides a information button for a root element. A programmer could write some help information for a user
 * @author Ilja
 *
 */
@SuppressWarnings("serial")
public class HelpButton extends JButton{
	String help;
	String name;
	Root root;
	
	public HelpButton(String name, Root root){
		super(name);
		this.root=root;
		this.name=name;
		this.help="";
		
		this.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				     setHelp();
			 
			  }
			  } );
		String help = "Edit information about Use Case";
		this.setToolTipText(help);
	}

	/**
	 * set or change a information about use case 
	 */
	public void setHelp(){
		String h;
		h=JOptionPane.showInputDialog( "Please add some information about Use Case" ,help );
		if(h!=null){
			this.help=h;
			this.root.setHelp(h);
		}
	}
	
	/**
	 * Return the String with current information of use case
	 * @return
	 */
	public String getHelp(){
		return this.help;
	}


}