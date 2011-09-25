package org.universaal.tools.configurationExtractor.view.MainPanels;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import org.universaal.tools.configurationExtractor.view.Buttons.AddPanelButton;
import org.universaal.tools.configurationExtractor.view.Buttons.ExportButton;
import org.universaal.tools.configurationExtractor.view.Buttons.ImportButton;
import org.universaal.tools.configurationExtractor.view.Buttons.ImportFromWorkspaceButton;
import org.universaal.tools.configurationExtractor.view.Buttons.SwitchButton;

/**
 * 
 * @author Ilja
 * This class provides a panel which contains some important functions (Import, Save, Switch between modes, Add new <panel>)
 */
@SuppressWarnings("serial")
public class UpPanel extends JPanel{

	private MainWindow mainWindow;	
	private ExportButton exp;
	private AddPanelButton apb;
	private SwitchButton sb;
	
	/**
	 * Constructor
	 * @param mainWindow mainwindow frame
	 */
	public UpPanel(MainWindow mainWindow){
		this.mainWindow = mainWindow;
		this.setLayout( new BoxLayout(this, BoxLayout.X_AXIS) );
		this.setBorder(new EtchedBorder(Color.blue,  Color.black));

		ImportButton imp = new ImportButton(mainWindow);
		this.add(imp);
		
		ImportFromWorkspaceButton impFromWorkspace = new ImportFromWorkspaceButton(mainWindow);
		this.add(impFromWorkspace);
		
		exp = new ExportButton(mainWindow);
		this.add(exp);
		
		this.add(Box.createRigidArea(new Dimension(20,0)));
		
		sb= new SwitchButton(this.mainWindow);
		this.add(sb);
		
		this.add(Box.createRigidArea(new Dimension(20,0)));
		
		apb=new AddPanelButton();
		this.add(apb);
		setButtonsNotEnabled();
		
	}
	
	/**
	 * Make all buttons not enabled, except import buttons
	 */
	public void setButtonsNotEnabled(){
		exp.setEnabled(false);
		apb.setEnabled(false);
		sb.setEnabled(false);
	}
	/**
	 * Make all buttons enabled
	 */
	public void setButtonsEnabled(){
		exp.setEnabled(true);
		apb.setEnabled(true);
		sb.setEnabled(true);
	}
	
}
