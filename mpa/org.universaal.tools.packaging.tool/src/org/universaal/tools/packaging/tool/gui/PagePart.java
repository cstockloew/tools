package org.universaal.tools.packaging.tool.gui;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.universaal.tools.packaging.tool.util.POMParser;

public class PagePart extends PageImpl {

	private IProject artifact;
	private POMParser p;

	protected PagePart(String pageName) {
		super(pageName, "Specify details for the MPA you are creating.");
	}

	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		setControl(container);
	}

	public void setArtifact(IProject artifact){
		this.artifact = artifact;
		p = new POMParser(new File(artifact.getFile("pom.xml").getLocation()+""));
	}
}