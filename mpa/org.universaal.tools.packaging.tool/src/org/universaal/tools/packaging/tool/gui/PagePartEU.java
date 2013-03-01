package org.universaal.tools.packaging.tool.gui;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.universaal.tools.packaging.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.ExecutionUnit;
import org.universaal.tools.packaging.tool.util.POMParser;

public class PagePartEU extends PageImpl {

	private IProject artifact;
	private POMParser p;
	private int partNumber;

	protected PagePartEU(String pageName, int pn) {
		super(pageName, "Specify execution units per part");
		this.partNumber = pn;
	}

	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);	

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		List<ExecutionUnit> eus = app.getParts().get(partNumber).getExecutionUnits();
		
		setPageComplete(true); // optional
	}

	public void setArtifact(IProject artifact){
		this.artifact = artifact;
		p = new POMParser(new File(artifact.getFile("pom.xml").getLocation()+""));
	}

	@Override
	public void nextPressed() {
		// TODO Auto-generated method stub

	}

	//capability
	//describes single offering, mostly used for devices and platforms
	//name.value
}