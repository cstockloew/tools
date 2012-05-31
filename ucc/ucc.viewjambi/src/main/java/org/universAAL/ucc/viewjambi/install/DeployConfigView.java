package org.universAAL.ucc.viewjambi.install;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.universAAL.ucc.viewjambi.common.SubWindow;
import org.universAAL.ucc.viewjambi.impl.MainWindow;
import org.universAAL.ucc.viewjambi.juic.Ui_DeployConfigView;


import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.gui.*;

public class DeployConfigView extends SubWindow {

    private static Ui_DeployConfigView ui = new Ui_DeployConfigView();
    
    String[] deployNodes = {"node1", "node2", "node3", "node4"};
    HashMap layout/*<String, List>*/ = new HashMap(); /* <ApplicationPartId, deployNodesId> */
    //HashMap deployConfig = new HashMap();  // user selected configuration
    // when the final screen "next" is pressed, the layout contains the updated config
    
    QStringListModel listModel = new QStringListModel(this); 
    
    int currentScreen = 0; // pointer to the application part; one part has one screen
    ArrayList screens = new ArrayList(); // index - appId
     

    /**
     * 
     * @param path The path of the extracted files from .uaal
     */
    public DeployConfigView(String path) {
      super(DeployConfigView.ui);
      
      // call MW Deploy Manager to get the deploy layout based on the MPA manifest
      // To integrate with MW: layout = checkDeployLayout
      layout = getDeployLayout(path);
      
      // create screens for the list of app part ID
      Iterator itr = layout.keySet().iterator();
      while (itr.hasNext())  {
    	  screens.add((String)itr.next());
      }
      
      // test
      for (int i=0; i<screens.size(); i++) 
    	  System.out.println("the " + i + "th screen is for " + screens.get(i));
      
      
      
      // initialization of the ui components
     ui.radioButton_all.clicked.connect(this, "onAllNodes()");
     //ui.radioButton_all.setChecked(true);
     //onAllNodes();
     ui.radioButton_selected.clicked.connect(this, "onSelectedNodes()");
      
      
      ui.pushButton_add.setCheckable(true);
      ui.pushButton_add.clicked.connect(this, "addNode()");
      ui.pushButton_remove.setCheckable(true); 
      ui.pushButton_remove.clicked.connect(this, "removeNode()");    
      
      ui.pushButton_cancel.clicked.connect(this, "cancel()");
      ui.pushButton_next.clicked.connect(this, "nextScreen()");
      ui.pushButton_previous.clicked.connect(this, "previousScreen()");
      
      // initialize the first screen
      initGuiForAppPart(0);
          
    }
   
    /**
     * initialize the GUI for one part
     * 
     */
   private void initGuiForAppPart(int screenId) {
	   ui.lineEdit_appId.setText((String) screens.get(screenId));
	   currentScreen = screenId;
	   ui.lineEdit_appId.setReadOnly(true); // disable text input (display only)
	   ui.radioButton_all.setChecked(true);
	   onAllNodes();
   }
    
    private void addNode()  {
    	String nodeId = QInputDialog.getItem(this, "Add node", "Please select a node to add", Arrays.asList(deployNodes));    	
    	// check the uniqueness of the node (i.e., if the nodeId already exists)
    	if (exists(nodeId)) {
    		QMessageBox.warning(this, "Error", "The node " + nodeId + " already added!");
    		return;
    	}
    	addSelectedNode(nodeId);
    }
    
    private void addSelectedNode(String nodeId) {
    	System.out.println("add a selected node: " + nodeId); 
    	int row;
    	
    	if (listModel.rowCount()==0) row = 0; 
    		else  {
    			if (ui.listView.currentIndex()==null) 
    				row = listModel.rowCount();    			
    			else row = ui.listView.currentIndex().row();
    		}
    	
    	listModel.insertRow(row);
    			
    	QModelIndex index = listModel.index(row, 0);     	
        listModel.setData(index, nodeId); 
        System.out.println("The list view after insert - the number of items: " + listModel.rowCount());
        ui.listView.setCurrentIndex(index); 
    }
    
    private void removeNode()  {    	
    	if (ui.listView.currentIndex()==null) {
    		QMessageBox.warning(this, "Error", "You should select one node to remove!");
    		return;
    	}
    	int row = ui.listView.currentIndex().row();
    	System.out.println("remove a node at row: " + row + " with id: " + listModel.data(row, 0));
    	listModel.removeRow(row);   	
    }
       
    private void onAllNodes() {
    	listModel.setStringList((List<String>) layout.get(screens.get(currentScreen)));
    	ui.listView.setModel(listModel);
    	ui.listView.show();
    }
    
    private void onSelectedNodes() {
    	List<String> list = new Vector<String>();
    	listModel.setStringList(list);
    	ui.listView.setModel(listModel);
    }
    
    private boolean exists(String nodeId)  {
    	List data = listModel.stringList(); 
        Iterator itr = data.iterator(); 
        while(itr.hasNext()) {
            String element = (String) itr.next(); 
            if (element.equals(nodeId)) {
            	return true;
            }
        } 
    	
    	return false;
    }
    
    private void cancel()  {
    	MainWindow.getInstance().removeSubWindow(this);
    }
    
    private void nextScreen() {
    	// save the current screen value first
    	layout.put(screens.get(currentScreen), listModel.stringList());
    	//TODO: show the saved results
    	// initialize the next screen if any
    	if (currentScreen==screens.size()-1)  {
    		// already the last screen - start to call DeployManager!
    		QMessageBox.information(this, "Info", "start deploying the multi-part application...");
    		// TODO: change to option panel like function.. QInputDialog.
    		//TODO write all the deploy configure setting to a configFile
    		// saveDeployConfigToFile();
    		//TODO call MW deploy manager requestToInstall(zip, configFile)
    		return;
    	}
    	initGuiForAppPart(currentScreen+1);
    }
    
    private void previousScreen()  {
    	if (currentScreen==0)  {
    		// already the first screen 
    		QMessageBox.information(this, "Info", "Already the first screen - can not go back!");
    		return;
    	}
    	initGuiForAppPart(currentScreen-1);
    }
    
   /**
    * call DeployManager to check deploy layout and 
    * @param path
    * @return
    */
    private HashMap getDeployLayout(String path)  {
    	HashMap layouts = new HashMap();
    	// TODO: get the mpa file
    	
    	
    	// TO integrate with the MW use:
    	// layout = checkDeployLayout(mpaFile);
    	// create screens using the layout (the list of appId)
    	
    	// here only initialize data for test
        String appId = "ApplicationPart1";
        String[] deployNode1 = {"node1", "node2", "node3", "node4"};
        layouts.put(appId, Arrays.asList(deployNode1));
        //screens.add(appId);
        
        appId = "ApplicationPart2";
        String[] deployNode2 = {"node1", "node2", "node3", "node4", "node5"};
        layouts.put(appId, Arrays.asList(deployNode2));
        //screens.add(appId);
        
        appId = "ApplicationPart3";
        String[] deployNode3 = {"node2", "node5", "node6"};
        layouts.put(appId, Arrays.asList(deployNode3));
        //screens.add(appId);
        
        return layouts;
    }
}
