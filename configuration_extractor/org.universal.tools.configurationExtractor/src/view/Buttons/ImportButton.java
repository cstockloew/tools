package view.Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.LoadControl;

import utility.Constants;
import view.MainPanels.MainWindow;

import model.xml.AnnotationsExtractor;
import model.xml.XMLCreator;

@SuppressWarnings("serial")
public class ImportButton extends JButton {
	private MainWindow mainWindow;
//	JFrame startWindow;
	
	public ImportButton(MainWindow mainWindow){
//		super("Import");
		this.mainWindow=mainWindow;
		ImageIcon openFromDirectoryIcon = new ImageIcon( Constants.OPEN_FROM_DIRECTORY_ICON );
		this.setIcon(openFromDirectoryIcon);
		
		this.setMargin(new java.awt.Insets(0, 0, 0, 0));
//		this.startWindow=startWindow;
		
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
		String help = "Open the Use Case from directories";
		this.setToolTipText( help );
	}
	
	private boolean isImported() {
		return mainWindow.isImported();
	}
	
	private void clearView(){
//		
		mainWindow.clearView();
//		mainWindow.init();
		
	}
	
	private void makeImport(){
		 System.out.println("Importieren...");
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

//			mainWindow.resetTree();
			if(LoadControl.getInstance().isRootFound()){
				mainWindow.importSuccesful();
			}else{
				setImportedToFalse();
			}
			
//			startWindow.setVisible(false);   
		 }else{
		      System.out.println( "Auswahl abgebrochen" );
//		      setImportedToFalse();
		 }
	}
	
    private void setImportedToFalse() {
		mainWindow.setImportedToFalse();
	}
}
