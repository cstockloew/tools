package org.universaal.tools.packaging.tool.gui;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.universaal.tools.packaging.tool.util.POMParser;

public class PagePart extends PageImpl {

	private IProject artifact;
	private POMParser p;
	private int partNumber;

	protected PagePart(String pageName, int pn) {
		super(pageName, "Specify details for the MPA you are creating.");
		this.partNumber = pn;
	}

	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);	

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		/*List<DeploymentUnit> dus = app.getParts().get(partNumber).getDeploymentUnits();
		List<ExecutionUnit> eus = app.getParts().get(partNumber).getExecutionUnits();
		List<Capability> pcs = app.getParts().get(partNumber).getPartCapabilities();
		List<Requirement> prs = app.getParts().get(partNumber).getPartRequirements();*/

		setPageComplete(true); //REMOVE
	}

	public void setArtifact(IProject artifact){
		this.artifact = artifact;
		p = new POMParser(new File(artifact.getFile("pom.xml").getLocation()+""));
	}

	//capability
	//describes single offering, mostly used for devices and platforms
	//name.value
}