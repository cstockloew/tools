package view.Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import model.xml.PanelWithElements;

import view.LowLevelPanels.BigPanel;
import view.LowLevelPanels.BigRootPanel;

@SuppressWarnings("serial")
public class AddPanelButton extends JButton{
	public static int index=1;
	PanelWithElements panelWithElements = PanelWithElements.getInstance();
	public AddPanelButton(){
		super("addPanel");
		this.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  addNewPanel();
				  
			  }
			  } );
		
		String help = "Add a new Panel";
		this.setToolTipText( help );
		
	}
	void addNewPanel(){
		BigPanel bg=new BigPanel("title"+index);
		panelWithElements.addParentPanel(bg);
		index++;
	}
}
