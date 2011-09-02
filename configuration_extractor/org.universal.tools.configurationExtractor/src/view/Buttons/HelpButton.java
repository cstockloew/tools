package view.Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import view.Elements.Root;
import view.LowLevelPanels.BigRootPanel;

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


	public void setHelp(){
		String h;
		h=JOptionPane.showInputDialog( "Please add some information about Use Case" ,help );
		if(h!=null){
			this.help=h;
			this.root.setInformation(h);
		}
	}

	public String getHelp(){
		return this.help;
	}


}