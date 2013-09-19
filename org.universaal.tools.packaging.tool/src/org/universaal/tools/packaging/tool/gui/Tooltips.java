package org.universaal.tools.packaging.tool.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.ui.PlatformUI;

public class Tooltips {
	final static String REQUIREMENT_TOOLTIP = "example: aal.device.features.audio EQUAL speaker";
	final static String DEPLOYMENT_TOOLTIP = "Use KARAF as OSGI container and uCC as Android container.";
	
/*
	public static ToolTip getRequirementTooltip(){
		ToolTip t = new ToolTip(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.BALLOON);
		t.setText("example: aal.device.features.audio EQUAL speaker");
		t.setVisible(false);

		return t;
	}

	public static ToolTip getDeploymentToolTooltip(){
		ToolTip t = new ToolTip(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.BALLOON);
		t.setText("Use KARAF as OSGI container and uCC as Android container.");
		t.setVisible(false);

		return t;
	}
*/
}