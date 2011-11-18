package org.universaal.tools.configurationExtractor;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TreeItem;
import org.universaal.tools.configurationExtractor.data.ConfigItem;
import org.universaal.tools.configurationExtractor.data.GeneralUCConfig;

/**
 * ConfigChecker checks the config items for validity
 * 
 * @author schwende
 */
public class ConfigChecker {

	/**
	 * Checks if the current configuration is valid.<br>
	 * If a problem is found, an error dialog is shown.
	 * 
	 * @param itemMap configuration items
	 * @param treeItems items shown in the tree view
	 * @return true if no problems were found
	 */
	public static boolean checkIfOk(Map<Integer, ConfigItem> itemMap, TreeItem[] treeItems) {
		// check if the main config item can be found
		if (! (itemMap.get(treeItems[0].getData()) instanceof GeneralUCConfig)) {
			showError("A fatal error occurred","The main config item is not at the correct location!");
			return false;
		}
		// check if the main config item contains subitems
		if (treeItems[0].getItemCount() > 0) {
			showError("The main config item may not have any subitems!");
			return false;
		}
		// check if the main config parameters are empty
		for (String param : GeneralUCConfig.getParameters()) {
			if (itemMap.get(treeItems[0].getData()).getParameter(param).isEmpty()) {
				showError("Parameter " + param + " for the general configuration is empty!");
				return false;
			}
		}
		
		ConfigItem panelItem, varItem;
		
		// loop through all panels
		for (int i = 1; i < treeItems.length; i++) {
			// check if the panel contains items
			if (treeItems[i].getItemCount() == 0) {
				showError("Panels may not be empty!");
				return false;
			} else {
				panelItem = itemMap.get(treeItems[i].getData());
				// check if the panel has a caption
				if (panelItem.getCaption().isEmpty()) {
					showError("Panels must have a caption!");
					return false;
				}
				// loop through items in the panel
				for (TreeItem treeItem : treeItems[i].getItems()) {
					varItem = itemMap.get(treeItem.getData());
					// check if there are empty parameters
					for (String param : varItem.getItemType().getParameters()) {
						if (varItem.getParameter(param).isEmpty()) {
							showError("Parameter " + param + " for " + varItem.getItemType() + " is empty!");
							return false;
						}
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Show an error message
	 * @param header Title of the dialog
	 * @param txt Message of the dialog
	 */
	public static void showError(String header, String txt) {				
		MessageBox messageDialog = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ERROR);
		messageDialog.setText(header);
		messageDialog.setMessage(txt);
		messageDialog.open();
	}

	/**
	 * Show an error message
	 * @param txt Message of the dialog
	 */
	public static void showError(String txt) {				
		MessageBox messageDialog = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ERROR);
		messageDialog.setText("A configuration error was found");
		messageDialog.setMessage(txt);
		messageDialog.open();
	}
	
}
