package org.universAAL.ucc.viewjambi.install;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.universAAL.middleware.interfaces.mpa.model.Part;
import org.universAAL.middleware.interfaces.PeerCard;
import org.universAAL.middleware.managers.api.InstallationResults;
import org.universAAL.middleware.managers.api.UAPPPackage;
import org.universAAL.ucc.viewjambi.common.SubWindow;
import org.universAAL.ucc.viewjambi.impl.Activator;
import org.universAAL.ucc.viewjambi.impl.MainWindow;
import org.universAAL.ucc.viewjambi.juic.Ui_DeployConfigView;

import com.trolltech.qt.gui.*;

public class DeployConfigView extends SubWindow {

	private static Ui_DeployConfigView ui = new Ui_DeployConfigView();
    
    int currentScreen = 0; // pointer to the application part; one part has one screen
    ArrayList screens = new ArrayList(); // index - Part
    String deployPath;
    String serviceId;
    MpaParser mpa;
    Map<String, PeerCard> peers;
    // the layout selected by the user - final version and sent to the MW for installation (one peer only for one part)
    Map<PeerCard, Part> mpaLayout = new HashMap<PeerCard, Part>();  
    // layout selected by the user <Part, PeerId>- working version: ensures that each part has only one peer
    Map<Part, String> layout = new HashMap<Part, String>(); 

    public DeployConfigView(String path, MpaParser mpa, Map peers, String sId) {
    	super(DeployConfigView.ui);
    	this.deployPath = path;
    	this.mpa = mpa;
    	this.peers = peers;
    	this.serviceId = sId;
    	
    	// get Parts and associate them with ScreenId
    	List<Part> parts = mpa.getApplicationPart().getPart();
    	for (int i=0; i<parts.size(); i++)
    		screens.add(parts.get(i));
    	   	
    	// initialization of the ui components
        ui.pushButton_cancel.clicked.connect(this, "cancel()");
                ui.pushButton_previous.clicked.connect(this, "previousScreen()");
        ui.pushButton_next.clicked.connect(this, "nextScreen()");
        // initialize the ComboBox using data from peers (peer Id/String?)
        Set keys = peers.keySet();
        for (Iterator i = keys.iterator(); i.hasNext();) {
          String peerId = (String) i.next();
          ui.comboBox.addItem(peerId);
        }
        ui.comboBox.highlighted.connect(this, "peerSelected(String)");
        // initialize the first screen
        initGuiForAppPart(0);
            
    }
    
    /**
     * initialize the GUI for one part
     * 
     */
   private void initGuiForAppPart(int screenId) {
	   ui.lineEdit.setText(((Part) screens.get(screenId)).getPartId());
	   currentScreen = screenId;
	   ui.lineEdit.setReadOnly(true); // disable text input (display only)
	   	   
	   if (screenId==0) 
		   ui.pushButton_previous.setDisabled(true);
	   else 
		   ui.pushButton_previous.setDisabled(false);
	   // set the previously selected value to the screen if exists
	   Part cPart = (Part) screens.get(currentScreen);
	   if (layout.containsKey(cPart)) {
		   String peerId = layout.get(cPart);
		   int cIndex=0;
		   for (int i=0; i<ui.comboBox.count(); i++) {
			   if (ui.comboBox.itemText(i).equals(peerId)) {
				   cIndex = i;
				   break;
			   }				   
		   }
		   ui.comboBox.setCurrentIndex(cIndex);
	   }
		   
	   
   }
   
   private void cancel()  {
   	MainWindow.getInstance().removeSubWindow(this);
   }
   
   private void nextScreen() {  		
	   // save the current screen value 
	   layout.put((Part) screens.get(currentScreen), ui.comboBox.currentText());
   		//show the saved results
	   	System.out.println("[DeployConfigView.nextScreen] save a selection: " + ((Part)(screens.get(currentScreen))).getPartId() +
	   			"/" + ui.comboBox.currentText());
   		// initialize the next screen if any
   		if (currentScreen==screens.size()-1)  {
   			// already the last screen - start to call DeployManager!
   			String[] options = {"YES - continue to deploy", "NO - Go back"};
   			String op = QInputDialog.getItem(this, "Deploy", "This is the last application part to configure. Start deploying?", Arrays.asList(options), 0, false);
   			if (options[0].equals(op)) {
   				// check the validity of the configuration layout
   				if (!checkLayout()) {
   					// the selection is not valid   					
   					QMessageBox.warning(this, "Configure error", "The configuration is not valid. Please try again!");
   					return;
   				}   				
   				MainWindow.getInstance().removeSubWindow(this);
   				System.out.println("Start deploying...");
   				//save the deploy configuration
   				for(Part part : layout.keySet()){
   					PeerCard card = peers.get(layout.get(part));
   					System.out.println("[DeployConfigView.nextScreen] save the layouts: " + card.getPeerID()
   							+ ":" + layout.get(part) + "/" + part.getPartId());
   					mpaLayout.put(card, part);
   				}
   				ProgressThread progress = new ProgressThread();
				progress.start();
   				// call MW deploy manager requestToInstall
				UAPPPackage uapp = new UAPPPackage(serviceId, mpa.getAppId(), (new File(deployPath)).toURI(), mpaLayout);
				InstallationResults results = Activator.getInstaller().requestToInstall(uapp);
   				progress.finished=true;
   				switch (results)  {
				case SUCCESS: 
					QMessageBox.information(this, "Installation result", "The multi-part application has been successfully installed!");
					System.out.println("[DeployConfigView.nextScreen] The multi-part application has been successfully installed!");
					break;
					
				case FAILURE:
					QMessageBox.warning(this, "Installation result", "The installation of the multi-part application has been failed!");
					System.out.println("[DeployConfigView.nextScreen] The installation of the multi-part application has been failed!");
					break;
					
				case NO_AALSPACE_JOINED:
					QMessageBox.warning(this, "Installation result", "Error in the installation of the multi-part application: no AALspace joined!");
					System.out.println("[DeployConfigView.nextScreen] Error in the installation of the multi-part application: no AALspace joined!");
					break;
					
				case UAPP_URI_INVALID:
					QMessageBox.warning(this, "Installation result", "Error in the installation of the multi-part application: UAPP uri is invalid!");
					System.out.println("[DeployConfigView.nextScreen] Error in the installation of the multi-part application: UAPP uri is invalid!");
					break;
					
				case DELEGATED:
					QMessageBox.information(this, "Installation result", "The installation of the multi-part application is delegated...");
					System.out.println("[DeployConfigView.nextScreen] The installation of the multi-part application is delegated...");
					break;
					
				case LOCALLY_DELEGATED:
					QMessageBox.information(this, "Installation result", "The installation of the multi-part application is locally delegated...");
					System.out.println("[DeployConfigView.nextScreen] The installation of the multi-part application is locally delegated...");
					break;
					
				case NOT_A_DEPLOYMANAGER:
					QMessageBox.warning(this, "Installation result", "The installation of the multi-part application: not a deploy manager!");
					System.out.println("[DeployConfigView.ok] The installation of the multi-part application: not a deploy manager!");
					break;
					
				case MPA_FILE_NOT_VALID:
					QMessageBox.warning(this, "Installation result", "The installation of the multi-part application: the MPA file is not valid!");
					System.out.println("[DeployConfigView.ok] The installation of the multi-part application: the MPA file is not valid!");
					break;
					
				default:
					break;
				}
   			} 
   		
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
   

   private void peerSelected(String peerId) {
	   Part part = (Part) screens.get(currentScreen);
	   //PeerCard card = peers.get(peerId);
	   System.out.println("[DedployConfigView.peerSelected] the selected peer is: " + peerId + "/" 
			   + peers.get(peerId).getPeerID() + " for part: " + part.getPartId());
	   // TODO: do we need to check validity of the selection here?	   
	   // save to the layout: each part appears just once in the layout
	   layout.put(part, peerId);
   }
   
  /**
   * check the layout:
   * - each Application part needs one peer to install
   * - each peer is compatible with the part DeploymentUnit specification
   * - each peer can only install one part
   * @return
   */
   private boolean checkLayout()  {
	   boolean valid = true;
	   
	   if (layout.size()==0) { 
		   System.out.println("[DeployConfigView.checkLayout] You have not selected any node to deploy!");
		   QMessageBox.information(this, "Validity check", "You have not selected any node to deploy!");
		   return false;
	   }
	   // check if the peer is compatible with the part DeploymentUnit
	   for(Part part: mpa.getApplicationPart().getPart()){
		   if (!layout.containsKey(part)) {
			   System.out.println("[DeployConfigView.checkLayout] You have not selected a node to deploy part " + part.getPartId() + "!");
			   QMessageBox.information(this, "Validity check", "[DeployConfigView.checkLayout] You have not selected a node to deploy part " + part.getPartId() + "!");
			   valid = false;
		   } else
		   if (!DeployStrategyView.checkDeployementUnit(part.getDeploymentUnit(), peers.get(layout.get(part)))) {
			   QMessageBox.information(this, "Validity check", "The part " + part.getPartId() + " can not be installed on the peer " + layout.get(part) + "!");
			   System.out.println("[DeployConfigView.checkLayout] The part " + part.getPartId() + " can not be installed on the peer " + layout.get(part) + "!");
			   valid = false;
		   }
		   else System.out.println("[DeployConfigView.checkLayout] The part " + part.getPartId() + " can be installed on the peer " + layout.get(part));
	   }   	   
	   // check if a peer is associated with more than one part -- no need for such check
/*	   for (String peerId: layout.values()) {
		   System.out.println("[DeployConfigView.checkLayout] check for peer " + peerId);
		   if (countPeer(peerId)>1) {
			   System.out.println("[DeployConfigView.checkLayout] " + peerId + " has been associated with more than one part!");
			   QMessageBox.information(this, "Validity check", peerId + " has been associated with more than one part!");
			   valid = false;
		   }
	   } */
	   return valid;
   }
   
   private int countPeer(String peerId) {
	   int count = 0;
	   for (Part part: layout.keySet()) {
		   if (layout.get(part).equals(peerId)) {
			   System.out.println("[DeployConfigView.countPeer] part "+ part.getPartId() + " is associated with " + peerId);
			   count++;
		   }
	   }
	   return count;
   }
}
