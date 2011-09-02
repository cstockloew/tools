package view.MainPanels;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import controller.LoadControl;

@SuppressWarnings("serial")
public class MainWindow extends JFrame{

	private XMLPanel xmlPanel;
	private TreePanel treePanel;
	private CentralPanel cp;
	private Container pane;
	private boolean imported=false;
	private UpPanel up;

    public MainWindow(String name){
    	super(name);
    	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    	this.setPreferredSize(d);
//    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	pane=this.getContentPane();
    	pane.setLayout(new BorderLayout());
//    	this.setIconImage(getImage("logo_small"));
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
   
    public void init(){
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
//    	JMenuBar menuBar = new JMenuBar();
//    	JMenu fileMenu = new JMenu( "File" );
//    	menuBar.add( fileMenu );
//    	JMenu helpMenu = new JMenu( "Help" );
//    	menuBar.add( helpMenu );
//    	this.setJMenuBar( menuBar );
    	
    }
    
    public void clearView(){
    	cp.setVisible(false);
		treePanel.setVisible(false);
		up.setButtonsNotEnabled();
    }
    
//    public void resetTree(){
//    	treePanel.setVisible(false);
//    	treePanel=new TreePanel();
//    	pane.add(treePanel,BorderLayout.LINE_START);
//    	treePanel.setVisible(true);
//		
//    }
    
    public void importSuccesful(){
    	cp.setVisible(true);
    	treePanel.setVisible(false);
    	treePanel=new TreePanel();
    	pane.add(treePanel,BorderLayout.LINE_START);
    	treePanel.setVisible(true);
    	imported=true;
    	up.setButtonsEnabled();
    }
    public boolean isImported(){
    	return imported;
    }
    
    public void setImportedToFalse(){
    	imported=false;
    }
    
    public void makeXmlPanelVisible(){
    	cp.setVisible(false);
    	pane.add(xmlPanel,BorderLayout.CENTER);
    	xmlPanel.refresh();
    	xmlPanel.setVisible(true);
    	
    }
    

    public void makeXmlPanelUnVisible(){
    	xmlPanel.setVisible(false);
    	pane.add(cp,BorderLayout.CENTER);
    	cp.setVisible(true);
    }
 

}
