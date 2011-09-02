package utility;

import java.net.URL;

import javax.swing.ImageIcon;

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.internal.util.BundleUtility;
import org.osgi.framework.Bundle;


public  class Constants {
	private Constants(){
		
	}
	
	public static final URL DELETE_PANEL_ICON = getResourceURL("icons/DeleteIcon1.png");
	public static final URL DELETE_ELEMENT_ICON = getResourceURL("icons/DeleteIcon1.png");
	public static final URL ARROW_DOWN_PANEL_ICON = getResourceURL("icons/ArrowDownIcon.png");
	public static final URL ARROW_UP_PANEL_ICON = getResourceURL("icons/ArrowUpIcon.png");
	public static final URL ARROW_DOWN_ELEMENT_ICON = getResourceURL("icons/ArrowDownIcon.png");
	public static final URL ARROW_UP_ELEMENT_ICON = getResourceURL("icons/ArrowUpIcon.png");
	public static final URL ROLL_ELEMENTS_ICON = getResourceURL("icons/maxElement.png");
	public static final URL ROLLOPEN_ELEMENTS_ICON = getResourceURL("icons/minElement.png");
	public static final URL MAXIMIZE_ELEMENT_ICON = getResourceURL("icons/maxElement.png");
	public static final URL MINIMIZE_ELEMENT_ICON = getResourceURL("icons/minElement.png");
	public static final URL E_SYMBOL = getResourceURL("icons/E_Symbol.png");
	public static final URL P_SYMBOL = getResourceURL("icons/P_Symbol.png");
	public static final URL LP_SYMBOL = getResourceURL("icons/LP_Symbol.png");
	public static final URL OPEN_FROM_DIRECTORY_ICON = getResourceURL("icons/openFromDirectoryIcon.jpg");
	public static final URL OPEN_FROM_WORKSPACE_ICON = getResourceURL("icons/openFromWorkspaceIcon.jpg");
	public static final URL EXPORT_ICON = getResourceURL("icons/exportIcon.png");
	public static final URL EXPERT_ICON = getResourceURL("icons/expertIcon.png");
	public static final URL NORMAL_ICON = getResourceURL("icons/normalIcon.png");
	
	
	
	
	
	
	
	
	
	
	
	
	public static final URL TEAM = getResourceURL( "icons/developerTeam.jpg" );
		
	
	
	
	
	
	
	
	
	
	
	public static URL getResourceURL(String path) {
        Bundle bundle = Platform.getBundle("CE"); //Plugin ID
        URL fullPathString = BundleUtility.find(bundle, path);
        return fullPathString;
	}
	
}
