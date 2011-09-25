package org.universaal.tools.configurationExtractor.view.Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.universaal.tools.configurationExtractor.controller.LoadControl;
import org.universaal.tools.configurationExtractor.utility.Constants;
import org.universaal.tools.configurationExtractor.view.MainPanels.MainWindow;
import org.universaal.tools.configurationExtractor.view.MainPanels.WorkspaceWindow;

/**
 * 
 * @author Ilja
 * This class provides another export button. Click on this button performs a export function of use case from workspace of eclipse.  	
 */
@SuppressWarnings("serial")
public class ImportFromWorkspaceButton extends JButton {
	private MainWindow mainWindow;
	
	public ImportFromWorkspaceButton(MainWindow mainWindow){
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
					  					clearView();
					  					LoadControl loadControl=LoadControl.getInstance();
					  					loadControl.removeHistory();
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
	
	/*
	 * Helper method, which calls a new window with a list of all workspace directories 
	 */
	private void makeImport(){
		 WorkspaceWindow wsw= new WorkspaceWindow("Open project from workspace", mainWindow);
	}
	/*
	 * This method return true, if user has already started with import
	 */
	private boolean isImported() {
		return mainWindow.isImported();
	}
	/*
	 * After start of import the main window must be cleared
	 */
	private void clearView(){
		mainWindow.clearView();
		
	}
	
}
