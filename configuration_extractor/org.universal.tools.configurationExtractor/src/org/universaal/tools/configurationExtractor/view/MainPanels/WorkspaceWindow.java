package org.universaal.tools.configurationExtractor.view.MainPanels;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.universaal.tools.configurationExtractor.controller.GuiControl;
import org.universaal.tools.configurationExtractor.controller.LoadControl;

/**
 * 
 * @author Ilja
 * This frame contains a list with all directories of eclipse workspace  
 */
@SuppressWarnings("serial")
public class WorkspaceWindow extends JFrame{
		private LoadControl loadControl = LoadControl.getInstance();
		private GuiControl guiControl = GuiControl.getInstance();
		private Container pane;
		private MainWindow mainWindow;
		private int index=-1;
		
		/**
		 * Constructor
		 * @param name of the frame
		 * @param mainWindow mainwindow frame
		 */
	    public WorkspaceWindow(String name, MainWindow mainWindow){
	    	super(name);
	    	this.mainWindow=mainWindow;
	    	Dimension d = new Dimension(450,500);
	    	this.setPreferredSize(d);
	    	pane=this.getContentPane();
	    	pane.setLayout( new BoxLayout(pane, BoxLayout.X_AXIS) );
	    	this.pack();
	    	
	    	final DefaultListModel workspaceDirs=guiControl.getDirectories();
	    	JList wokspaceDirsList = new JList(workspaceDirs);
	    	
	    	MouseListener mouseListener = new MouseAdapter() {
			      public void mouseClicked(MouseEvent mouseEvent) {
			        JList theList = (JList) mouseEvent.getSource();
			        index = theList.locationToIndex(mouseEvent.getPoint());
			        
			        if (mouseEvent.getClickCount() == 2) {
			        	index = theList.locationToIndex(mouseEvent.getPoint());
			        	selected(index,workspaceDirs);
			          
			        }
			      }
			    };
	    
		    wokspaceDirsList.addMouseListener(mouseListener);	    	
	    	JScrollPane jScrollPane = new JScrollPane(wokspaceDirsList);
	    	pane.add(jScrollPane);
	    	
	    	WindowListener windowListener = new WindowAdapter() {
	    		public void windowClosing( WindowEvent event ) {
	    			setImportedToFalse();
	    			index=-1;
	    		}
	    	};
	    	this.addWindowListener(windowListener);
	    	this.setVisible(true);

	    	JButton openButton   = new JButton(" Open  ");
	    	JButton cancelButton = new JButton("Cancel");
    	
	    	openButton.addActionListener( new ActionListener() {
				  @Override public void actionPerformed( ActionEvent e ) {
					  if(index != -1){
						  selected(index,workspaceDirs);
					  }
					  index=-1;
				  }
				  } );
	    	
	    	cancelButton.addActionListener( new ActionListener() {
				  @Override public void actionPerformed( ActionEvent e ) {
					  setInvisible();
					  setImportedToFalse();
					  index=-1;
				  }
				  } );
	    	
	    	
	    	
	    	JPanel buttonsPanel= new JPanel();
	    	buttonsPanel.setLayout( new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS) );
	    	buttonsPanel.add(openButton);
	    	buttonsPanel.add(cancelButton);
	    	
	    	pane.add(buttonsPanel);
	    }
	    
	    /*
	     * Helper method, which should be called, if import has aborted 
	     */
	    private void setImportedToFalse() {
			mainWindow.setImportedToFalse();
		}

	    /*
	     * Makes frame invisible
	     */
	    private void setInvisible(){
	    	this.setVisible(false);
	    }
	    
	    /*
	     * Selected directory can be imported
	     */
	    private void selected(int index, DefaultListModel workspaceDirs){
	    	
	        if (index >= 0) {
	        	System.out.println("clicked on "+workspaceDirs.get(index));
				loadControl.extractAnnotationsFrom(index,workspaceDirs);
				if(LoadControl.getInstance().isRootFound()){
					mainWindow.importSuccesful();
				}else{
					setImportedToFalse();
				}
				this.setVisible(false);  
            }			
	    }
	    
}
