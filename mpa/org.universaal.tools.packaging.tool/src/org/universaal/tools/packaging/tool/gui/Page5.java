package org.universaal.tools.packaging.tool.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.universaal.tools.packaging.tool.parts.ApplicationManagement.RemoteManagement;
import org.universaal.tools.packaging.tool.parts.Artifact;
import org.universaal.tools.packaging.tool.parts.Version;

public class Page5 extends PageImpl {

	protected Page5(String pageName) {
		super(pageName, "Specify details for the MPA you are creating.");
	}

	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		setControl(container);	

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		app.getManagement().setContact("");
		RemoteManagement e = app.getManagement().new RemoteManagement();
		e.setProtocol("");
		Artifact software = new Artifact();
		software.setArtifactID("");
		Version version = new Version();
		software.setVersion(version);
		e.setSoftware(software);
		app.getManagement().getRemoteManagement().add(e);
	}
}
