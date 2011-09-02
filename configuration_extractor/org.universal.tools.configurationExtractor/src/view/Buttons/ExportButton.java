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

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import controller.GuiControl;
import controller.LoadControl;

import utility.Constants;
import view.MainPanels.EndWindow;
import view.MainPanels.MainWindow;
import view.MainPanels.TreePanel;

import model.xml.AnnotationsExtractor;
import model.xml.XMLCreator;


@SuppressWarnings("serial")
public class ExportButton extends JButton{
	private MainWindow mainWindow;
	private GuiControl guiControl = GuiControl.getInstance();

	public ExportButton(MainWindow mainWindow){
//		super("Export");
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
	private void export(){
		
//		TreePanel treePanel = TreePanel.getInstance();
		if(guiControl.isOtherElementsNodeEmpty()){
//			 XMLCreator xc = XMLCreator.getInstance();
//			 xc.init();
			 
			 JFileChooser fc = new JFileChooser();
	
			    fc.setFileFilter( new FileNameExtensionFilter("xml", ".xml") );
			    fc.setDialogType(JFileChooser.SAVE_DIALOG);
	
			    fc.setSelectedFile( new File("c:/test_CE.xml") );
			    
			    int state = fc.showSaveDialog( null );
	
			    if ( state == JFileChooser.APPROVE_OPTION ) {
			      File file = fc.getSelectedFile();
	//		      System.out.println( file.getName() );
			      
			      try {
					GuiControl.getInstance().printXML(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			      
//			      xc.printToFile(file);
			      removeHistory();
			      
			      
			    }
			    else{
			      System.out.println( "Auswahl abgebrochen" );
			    }
		   //  xc.printToFile("CF.xml");
		}else{
			JOptionPane.showMessageDialog( null, "You have to use all elements! \"OtherElements\" node is not empty!" );
		}
	}
	public void removeHistory(){
		LoadControl loadControl=LoadControl.getInstance();
		loadControl.removeHistory();
		EndWindow end=new EndWindow(mainWindow);
		
		
	}
}
