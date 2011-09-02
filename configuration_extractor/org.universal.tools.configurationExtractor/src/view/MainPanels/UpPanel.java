package view.MainPanels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import view.Buttons.AddPanelButton;
import view.Buttons.ExportButton;
import view.Buttons.ImportButton;
import view.Buttons.ImportFromWorkspaceButton;
import view.Buttons.SwitchButton;
import view.LowLevelPanels.BigRootPanel;

@SuppressWarnings("serial")
public class UpPanel extends JPanel{

	private MainWindow mainWindow;	
	private ExportButton exp;
	private AddPanelButton apb;
	private SwitchButton sb;
	
	public UpPanel(MainWindow mainWindow){
		this.mainWindow = mainWindow;
		this.setLayout( new BoxLayout(this, BoxLayout.X_AXIS) );
		//this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setBorder(new EtchedBorder(Color.blue,  Color.black));

		ImportButton imp = new ImportButton(mainWindow);
		this.add(imp);
		
		ImportFromWorkspaceButton impFromWorkspace = new ImportFromWorkspaceButton(mainWindow);
		this.add(impFromWorkspace);
		
		exp = new ExportButton(mainWindow);
		this.add(exp);
		
		this.add(Box.createRigidArea(new Dimension(200,0)));
		
		sb= new SwitchButton(this.mainWindow);
		this.add(sb);
		
		this.add(Box.createRigidArea(new Dimension(200,0)));
		
		apb=new AddPanelButton();
		this.add(apb);
		setButtonsNotEnabled();
		
	}
	
	public void setButtonsNotEnabled(){
		exp.setEnabled(false);
		apb.setEnabled(false);
		sb.setEnabled(false);
	}
	public void setButtonsEnabled(){
		exp.setEnabled(true);
		apb.setEnabled(true);
		sb.setEnabled(true);
	}
	
}
