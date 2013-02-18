package org.universaal.tools.packaging.tool.gui;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.universaal.tools.packaging.tool.parts.Capability;
import org.universaal.tools.packaging.tool.parts.DeploymentUnit;
import org.universaal.tools.packaging.tool.parts.ExecutionUnit;
import org.universaal.tools.packaging.tool.parts.Requirement;
import org.universaal.tools.packaging.tool.util.POMParser;

public class PagePart extends PageImpl {

	private IProject artifact;
	private POMParser p;

	private int partNumber;

	protected PagePart(String pageName, int pn) {
		super(pageName, "Specify details for the MPA you are creating.", pn);
		this.partNumber = pn;
	}

	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		setControl(container);

		List<DeploymentUnit> dus = multipartApplication.getApplications().get(partNumber).getPart().getDeploymentUnits();
		List<ExecutionUnit> eus = multipartApplication.getApplications().get(partNumber).getPart().getExecutionUnits();
		List<Capability> pcs = multipartApplication.getApplications().get(partNumber).getPart().getPartCapabilities();
		List<Requirement> prs = multipartApplication.getApplications().get(partNumber).getPart().getPartRequirements();
	}

	public void setArtifact(IProject artifact){
		this.artifact = artifact;
		p = new POMParser(new File(artifact.getFile("pom.xml").getLocation()+""));
	}
	
	//capability
	//describes single offering, mostly used for devices and platforms
	//name.value
	
}