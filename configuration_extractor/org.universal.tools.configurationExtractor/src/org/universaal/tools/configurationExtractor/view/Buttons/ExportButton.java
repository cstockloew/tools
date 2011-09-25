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
import org.universaal.tools.configurationExtractor.controller.GuiControl;
import org.universaal.tools.configurationExtractor.controller.LoadControl;
import org.universaal.tools.configurationExtractor.utility.Constants;
import org.universaal.tools.configurationExtractor.view.MainPanels.EndWindow;
import org.universaal.tools.configurationExtractor.view.MainPanels.MainWindow;

/**
 * 
 * @author Ilja
 * This class provides the button, which can export the current configuration.  
 */
@SuppressWarnings("serial")
public class ExportButton extends JButton{
	private MainWindow mainWindow;
	private GuiControl guiControl = GuiControl.getInstance();

	public ExportButton(MainWindow mainWindow){
		this.mainWindow=mainWindow;
		ImageIcon exportIcon = new ImageIcon( Constants.EXPORT_ICON );
		this.setIcon(exportIcon);
		this.setMargin(new java.awt.Insets(0, 0, 0, 0));

		this.addActionListener( new ActionListener() {
			  @Override public void actionPerformed( ActionEvent e ) {
				  export();
				  
			  }
			  } );
		String help = "Export the actual configuration to xml file";
		this.setToolTipText( help );	
	}
	
	/*
	 * Helper method to export a use case from directories
	 */
	private void export(){
		if(guiControl.isOtherElementsNodeEmpty()){
			 JFileChooser fc = new JFileChooser();
	
			    fc.setFileFilter( new FileNameExtensionFilter("xml", ".xml") );
			    fc.setDialogType(JFileChooser.SAVE_DIALOG);
	
			    fc.setSelectedFile( new File("c:/test_CE.xml") );
			    
			    int state = fc.showSaveDialog( null );
	
			    if ( state == JFileChooser.APPROVE_OPTION ) {
			      File file = fc.getSelectedFile();
		      
			      try {
					GuiControl.getInstance().printXML(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			      removeHistory();
			      
			      
			    }
			    else{
			      System.out.println( "Dialog was closed" );
			    }
		}else{
			JOptionPane.showMessageDialog( null, "You have to use all elements! \"OtherElements\" node is not empty!" );
		}
	}
	
	/**
	 * This method removes the history of this class
	 */
	public void removeHistory(){
		LoadControl loadControl=LoadControl.getInstance();
		loadControl.removeHistory();
		EndWindow end=new EndWindow(mainWindow);
		
		
	}
}
