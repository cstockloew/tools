package org.universaal.tools.configurationExtractor.view.MainPanels;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import org.universaal.tools.configurationExtractor.controller.LoadControl;

/**
 * 
 * @author Ilja
 * The main frame which contains all views and functions of CE
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame{

	private XMLPanel xmlPanel;
	private TreePanel treePanel;
	private CentralPanel cp;
	private Container pane;
	private boolean imported=false;
	private UpPanel up;

	/**
	 * Constructor
	 * @param name title of frame
	 */
    public MainWindow(String name){
    	super(name);
    	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    	this.setPreferredSize(d);
    	pane=this.getContentPane();
    	pane.setLayout(new BorderLayout());
    	init();
    	this.pack();
    	
    	WindowListener windowListener = new WindowAdapter() {
    		public void windowClosing( WindowEvent event ) {
    			if(imported){
    				cp.setVisible(false);
    				treePanel.setVisible(false);
	    			LoadControl loadControl=LoadControl.getInstance();
	    			loadControl.removeHistory();
	    			imported=false;
	    			
    		    }
    		}
    	};
    	this.addWindowListener(windowListener);
    }
   /*
    * Helper method for initialization of frame 
    */
    private void init(){
    	cp = new CentralPanel();
    	pane.add(cp,BorderLayout.CENTER);
     	cp.setVisible(false);
     	up=new UpPanel(this);
    	pane.add(up,BorderLayout.NORTH);
    	xmlPanel = XMLPanel.getInstance();
    	pane.add(xmlPanel,BorderLayout.LINE_END);
    	xmlPanel.setVisible(false);
    	treePanel=new TreePanel();
    	treePanel.setVisible(false);
    	pane.add(treePanel,BorderLayout.LINE_START);
    	imported=false;
    }
    
    /**
     * This method makes a GUI clear. Only import buttons are available. 
     */
    public void clearView(){
    	cp.setVisible(false);
		treePanel.setVisible(false);
		up.setButtonsNotEnabled();
    }

    /**
     * This method will be called after successful import of use case.
     */
    public void importSuccesful(){
    	cp.setVisible(true);
    	treePanel.setVisible(false);
    	treePanel=new TreePanel();
    	pane.add(treePanel,BorderLayout.LINE_START);
    	treePanel.setVisible(true);
    	imported=true;
    	up.setButtonsEnabled();
    }
    /**
     * Checks whether import was successful performed
     * @return true if import was successful,otherwise returns false.
     */
    public boolean isImported(){
    	return imported;
    }
    /**
     * Set import to false
     */
    public void setImportedToFalse(){
    	imported=false;
    }
    
    /**
     * Makes XML Editor visible and Central Panel invisible
     */
    public void makeXmlPanelVisible(){
    	cp.setVisible(false);
    	pane.add(xmlPanel,BorderLayout.CENTER);
    	xmlPanel.refresh();
    	xmlPanel.setVisible(true);
    	
    }
    
    /**
     * Makes XML Editor invisible and Central Panel visible
     */
    public void makeXmlPanelInVisible(){
    	xmlPanel.setVisible(false);
    	pane.add(cp,BorderLayout.CENTER);
    	cp.setVisible(true);
    }
 

}
