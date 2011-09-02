package view.MainPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.GuiControl;
import controller.LoadControl;

import view.Buttons.ImportButton;
import view.LowLevelPanels.BigRootPanel;

import model.xml.AnnotationsExtractor;

public class WorkspaceWindow extends JFrame{
		private AnnotationsExtractor annotationsExtractor=  AnnotationsExtractor.getInstance();
		private LoadControl loadControl = LoadControl.getInstance();
		private GuiControl guiControl = GuiControl.getInstance();
		private Container pane;
		private MainWindow mainWindow;
		
	    public WorkspaceWindow(String name, MainWindow mainWindow){
	    	super(name);
	    	this.mainWindow=mainWindow;
	    	Dimension d = new Dimension(450,500);
	    	this.setPreferredSize(d);
//	    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	pane=this.getContentPane();
	    	pane.setLayout(new BorderLayout());
//	    	this.setIconImage(getImage("logo_small"));
	    	this.pack();
	    	
	    	final DefaultListModel workspaceDirs=guiControl.getDirectories();
	    	JList wokspaceDirsList = new JList(workspaceDirs);
	    	
	    	MouseListener mouseListener = new MouseAdapter() {
			      public void mouseClicked(MouseEvent mouseEvent) {
			        JList theList = (JList) mouseEvent.getSource();
			        if (mouseEvent.getClickCount() == 2) {
//			        	LoadControl loadControl=LoadControl.getInstance();
//		    			loadControl.removeHistory();
		    			
			        	int index = theList.locationToIndex(mouseEvent.getPoint());
			        	selected(index,workspaceDirs);
			          
			        }
			      }
			    };
	    
		    wokspaceDirsList.addMouseListener(mouseListener);	    	
	    	JScrollPane jScrollPane = new JScrollPane(wokspaceDirsList);
	    	this.add(jScrollPane,BorderLayout.CENTER);
	    	
	    	WindowListener windowListener = new WindowAdapter() {
	    		public void windowClosing( WindowEvent event ) {
	    			setImportedToFalse();
	    		}
	    	};
	    	this.addWindowListener(windowListener);

	    	
	    	this.setVisible(true);
   	
	    }
	    
	    private void setImportedToFalse() {
			mainWindow.setImportedToFalse();
		}

	    private void selected(int index, DefaultListModel workspaceDirs){
	    	
	        if (index >= 0) {
	        	System.out.println("clicked on "+workspaceDirs.get(index));
//	        	AnnotationsExtractor annotationsExtractor= AnnotationsExtractor.getInstance();
//				annotationsExtractor.extract(new File(""+workspaceDirs.get(index)));
				loadControl.extractAnnotationsFrom(index,workspaceDirs);
//				annotationsExtractor.initialize();
//				mainWindow.setVisible(true); 
//				mainWindow.resetTree();
				if(LoadControl.getInstance().isRootFound()){
					mainWindow.importSuccesful();
				}else{
					setImportedToFalse();
				}
				this.setVisible(false);  
            }			
	    }
	    
}
