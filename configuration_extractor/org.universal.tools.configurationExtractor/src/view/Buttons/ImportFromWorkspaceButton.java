package view.Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.LoadControl;

import utility.Constants;
import view.MainPanels.MainWindow;
import view.MainPanels.WorkspaceWindow;

import model.xml.AnnotationsExtractor;


@SuppressWarnings("serial")
public class ImportFromWorkspaceButton extends JButton {
	private MainWindow mainWindow;
	
	public ImportFromWorkspaceButton(MainWindow mainWindow){
//		super("ImportFromWorkspace");
		this.mainWindow=mainWindow;

		ImageIcon openFromWorkspaceIcon = new ImageIcon( Constants.OPEN_FROM_WORKSPACE_ICON );
		this.setIcon(openFromWorkspaceIcon);
		this.setMargin(new java.awt.Insets(0, 0, 0, 0));
		
		
		this.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  
				  if(isImported()){
					    int ok = JOptionPane.showConfirmDialog(null,"Are you sure you want to create a new project? All actual data will be lost! !", 
			                    "Confirmation", JOptionPane.OK_CANCEL_OPTION);
						if (ok == JOptionPane.YES_OPTION){
//					  				if(isImported()){
					  					clearView();
					  					LoadControl loadControl=LoadControl.getInstance();
					  					loadControl.removeHistory();
					  					
//					  				}
					  				makeImport();
						}
				  	}else{
				  		makeImport();
				  	}
			  }
			  } );
		String help = "Open the Use Case from eclipse workspace";
		this.setToolTipText( help );
		
	}
	private void makeImport(){
		 
		 WorkspaceWindow wsw= new WorkspaceWindow("Please select your directory from the workspace", mainWindow);
	}
	
	private boolean isImported() {
		return mainWindow.isImported();
	}
	
	private void clearView(){
//		
		mainWindow.clearView();
//		mainWindow.init();
		
	}
	
}
