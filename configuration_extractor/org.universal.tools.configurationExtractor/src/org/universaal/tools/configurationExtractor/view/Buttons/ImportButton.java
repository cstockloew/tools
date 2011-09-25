package org.universaal.tools.configurationExtractor.view.Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.universaal.tools.configurationExtractor.controller.LoadControl;
import org.universaal.tools.configurationExtractor.utility.Constants;
import org.universaal.tools.configurationExtractor.view.MainPanels.MainWindow;
import org.universaal.tools.configurationExtractor.model.xml.AnnotationsExtractor;

/**
 * 
 * @author Ilja
 * This class provides a export button. Click on this button performs a export function of use case from directories  
 */
@SuppressWarnings("serial")
public class ImportButton extends JButton {
	private MainWindow mainWindow;
	/**
	 * Constructor
	 */
	public ImportButton(MainWindow mainWindow){
		this.mainWindow=mainWindow;
		ImageIcon openFromDirectoryIcon = new ImageIcon( Constants.OPEN_FROM_DIRECTORY_ICON );
		this.setIcon(openFromDirectoryIcon);
		
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
		String help = "Open the Use Case from directories";
		this.setToolTipText( help );
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
	
	/*
	 * Helper method for import
	 */
	private void makeImport(){
		 System.out.println("Import...");
		 JFileChooser fc = new JFileChooser();
         fc.setDialogType(JFileChooser.OPEN_DIALOG);
         fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
         fc.setFileFilter( new FileNameExtensionFilter("Directory", "DIRECTORIES_ONLY") );
  
         int state = fc.showOpenDialog( null );
 
	     if ( state == JFileChooser.APPROVE_OPTION ) {
		      File directory = fc.getSelectedFile();
		      System.out.println( directory.getName() );
		      AnnotationsExtractor annotationsExtractor= AnnotationsExtractor.getInstance();
		      try {
				annotationsExtractor.extract(directory);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			annotationsExtractor.initialize();
			if(LoadControl.getInstance().isRootFound()){
				mainWindow.importSuccesful();
			}else{
				setImportedToFalse();
			}
		 }else{
//			  debug output
		      System.out.println( "Dialog was closed!" );
		      setImportedToFalse();
		 }
	}
	
	/*
	 * Helper method, which ends with import 
	 */
    private void setImportedToFalse() {
		mainWindow.setImportedToFalse();
	}
}
