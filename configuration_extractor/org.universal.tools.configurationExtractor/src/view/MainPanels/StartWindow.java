/*package view.MainPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class StartWindow extends JFrame{
		private AnnotationsExtractor annotationsExtractor=  AnnotationsExtractor.getInstance();
		private LoadControl loadControl = LoadControl.getInstance();
		private GuiControl guiControl = GuiControl.getInstance();
		private Container pane;
		private JFrame mainWindow;
		
	    public StartWindow(String name, JFrame mainWindow){
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
			        	int index = theList.locationToIndex(mouseEvent.getPoint());
			        	selected(index,workspaceDirs);
			          
			        }
			      }
			    };
			    
		    wokspaceDirsList.addMouseListener(mouseListener);	    	
	    	JScrollPane jScrollPane = new JScrollPane(wokspaceDirsList);

	    	ImportButton importButton= new ImportButton(mainWindow,this);
	    	
	    	JLabel jl1=new JLabel("If your source directory is in workspace you could select it immediately");
	    	
	    	jl1.setForeground(Color.BLUE);
	    	JLabel jl2=new JLabel("in other cases please use the import button ");
	    	jl2.setForeground(Color.BLUE);
	    	JPanel buttonPanel= new JPanel();
	    	buttonPanel.add(jl2,BorderLayout.LINE_START);
	    	buttonPanel.add(importButton,BorderLayout.LINE_END);
	    	pane.add(jl1,BorderLayout.NORTH);
	    	pane.add(jScrollPane,BorderLayout.CENTER);
	    	pane.add(buttonPanel,BorderLayout.SOUTH);
    	
	    }

	    private void selected(int index, DefaultListModel workspaceDirs){
	    	
	        if (index >= 0) {
	        	System.out.println("clicked on "+workspaceDirs.get(index));
//	        	AnnotationsExtractor annotationsExtractor= AnnotationsExtractor.getInstance();
//				annotationsExtractor.extract(new File(""+workspaceDirs.get(index)));
				loadControl.extractAnnotationsFrom(index,workspaceDirs);
//				annotationsExtractor.initialize();
				mainWindow.setVisible(true); 
				this.setVisible(false);  
            }			
	    }
	    
}
*/