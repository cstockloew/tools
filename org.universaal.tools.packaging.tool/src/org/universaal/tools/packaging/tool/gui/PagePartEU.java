package org.universaal.tools.packaging.tool.gui;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.ExecutionUnit;
import org.universaal.tools.packaging.tool.util.Dialog;
import org.universaal.tools.packaging.tool.validators.FileV;
import org.universaal.tools.packaging.tool.validators.IntegerV;

public class PagePartEU extends PageImpl {

	//private IProject artifact;
	//private POMParser p;
	private int partNumber;

	private Text configFile1, configFile2, configFile3, configFile4, ssl1, ssl2, ssl3, ssl4;
	private File f1, f2, f3, f4;

	protected PagePartEU(String pageName, int pn) {
		super(pageName, "Specify execution units per part");
		this.partNumber = pn;
	}

	public void createControl(final Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);	

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 3;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		List<ExecutionUnit> eus = app.getParts().get(partNumber).getExecutionUnits();

		Label l1 = new Label(container, SWT.NULL);
		configFile1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(cu1);
		l1.setText("Config file #1");
		if(!eus.isEmpty() && eus.get(0) != null && eus.get(0).getConfigFile() != null)
			configFile1.setText(eus.get(0).getConfigFile().getName());		
		else
			configFile1.setText("");
		configFile1.addVerifyListener(new FileV());
		configFile1.setLayoutData(gd);

		Button b1 = new Button(container, SWT.PUSH);
		b1.setText("Browse");
		b1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				Dialog d = new Dialog();
				f1 = d.open(parent.getShell(), new String[]{"*.*"}, true, "Select a configuration file...");				
				configFile1.setText(f1.getName());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});	

		Label l11 = new Label(container, SWT.NULL);
		ssl1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		l11.setText("Space start level #1");
		if(!eus.isEmpty() && eus.get(0) != null)
			ssl1.setText(eus.get(0).getSpaceStartLevel()+"");
		else
			ssl1.setText("0");
		ssl1.addVerifyListener(new IntegerV());

		Label l12 = new Label(container, SWT.NULL);
		l12.setText("");


		Label l2 = new Label(container, SWT.NULL);
		configFile2 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(cu1);
		l2.setText("Config file #2");
		if(!eus.isEmpty() && eus.get(1) != null && eus.get(1).getConfigFile() != null)
			configFile2.setText(eus.get(1).getConfigFile().getName());		
		else
			configFile2.setText("");
		configFile2.addVerifyListener(new FileV());
		configFile2.setLayoutData(gd);

		Button b2 = new Button(container, SWT.PUSH);
		b2.setText("Browse");
		b2.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				Dialog d = new Dialog();
				f2 = d.open(parent.getShell(), new String[]{"*.*"}, true, "Select a configuration file...");				
				configFile2.setText(f2.getName());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});	

		Label l21 = new Label(container, SWT.NULL);
		ssl2 = new Text(container, SWT.BORDER | SWT.SINGLE);
		l21.setText("Space start level #2");
		if(!eus.isEmpty() && eus.get(1) != null)
			ssl2.setText(eus.get(1).getSpaceStartLevel()+"");
		else
			ssl2.setText("0");
		ssl2.addVerifyListener(new IntegerV());

		Label l22 = new Label(container, SWT.NULL);
		l22.setText("");


		Label l3 = new Label(container, SWT.NULL);
		configFile3 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(cu1);
		l3.setText("Config file #3");
		if(!eus.isEmpty() && eus.get(2) != null && eus.get(2).getConfigFile() != null)
			configFile3.setText(eus.get(2).getConfigFile().getName());		
		else
			configFile3.setText("");
		configFile3.addVerifyListener(new FileV());
		configFile3.setLayoutData(gd);

		Button b3 = new Button(container, SWT.PUSH);
		b3.setText("Browse");
		b3.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				Dialog d = new Dialog();
				f3 = d.open(parent.getShell(), new String[]{"*.*"}, true, "Select a configuration file...");				
				configFile3.setText(f3.getName());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});	

		Label l31 = new Label(container, SWT.NULL);
		ssl3 = new Text(container, SWT.BORDER | SWT.SINGLE);
		l31.setText("Space start level #3");
		if(!eus.isEmpty() && eus.get(2) != null)
			ssl3.setText(eus.get(2).getSpaceStartLevel()+"");
		else
			ssl3.setText("0");
		ssl3.addVerifyListener(new IntegerV());

		Label l32 = new Label(container, SWT.NULL);
		l32.setText("");


		Label l4 = new Label(container, SWT.NULL);
		configFile4 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(cu1);
		l4.setText("Config file #4");
		if(!eus.isEmpty() && eus.get(3) != null && eus.get(3).getConfigFile() != null)
			configFile4.setText(eus.get(3).getConfigFile().getName());		
		else
			configFile4.setText("");
		configFile4.addVerifyListener(new FileV());
		configFile4.setLayoutData(gd);

		Button b4 = new Button(container, SWT.PUSH);
		b4.setText("Browse");
		b4.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				Dialog d = new Dialog();
				f4 = d.open(parent.getShell(), new String[]{"*.*"}, true, "Select a configuration file...");				
				configFile4.setText(f4.getName());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});	

		Label l41 = new Label(container, SWT.NULL);
		ssl4 = new Text(container, SWT.BORDER | SWT.SINGLE);
		l41.setText("Space start level #4");
		if(!eus.isEmpty() && eus.get(3) != null)
			ssl4.setText(eus.get(3).getSpaceStartLevel()+"");
		else
			ssl4.setText("0");
		ssl4.addVerifyListener(new IntegerV());

		Label l42 = new Label(container, SWT.NULL);
		l42.setText("");


		setPageComplete(true); // optional
	}

	public void setArtifact(IProject artifact){
		//this.artifact = artifact;
		//p = new POMParser(new File(artifact.getFile("pom.xml").getLocation()+""));
	}

	@Override
	public boolean nextPressed() {

		String id = app.getParts().get(partNumber).getDeploymentUnits().get(0).getId();

		try{
			if(f1 != null){
				if(app.getParts().get(partNumber).getExecutionUnits().size() == 0)
					app.getParts().get(partNumber).getExecutionUnits().add(new ExecutionUnit(id, f1, Integer.parseInt(ssl1.getText())));
				else
					app.getParts().get(partNumber).getExecutionUnits().set(0, new ExecutionUnit(id, f1, Integer.parseInt(ssl1.getText())));
			}
			if(f2 != null){
				if(app.getParts().get(partNumber).getExecutionUnits().size() == 1)
					app.getParts().get(partNumber).getExecutionUnits().add(new ExecutionUnit(id, f2, Integer.parseInt(ssl2.getText())));
				else
					app.getParts().get(partNumber).getExecutionUnits().set(1, new ExecutionUnit(id, f2, Integer.parseInt(ssl2.getText())));
			}
			if(f3 != null){
				if(app.getParts().get(partNumber).getExecutionUnits().size() == 2)
					app.getParts().get(partNumber).getExecutionUnits().add(new ExecutionUnit(id, f3, Integer.parseInt(ssl3.getText())));
				else
					app.getParts().get(partNumber).getExecutionUnits().set(2, new ExecutionUnit(id, f3, Integer.parseInt(ssl3.getText())));
			}
			if(f4 != null){
				if(app.getParts().get(partNumber).getExecutionUnits().size() == 3)
					app.getParts().get(partNumber).getExecutionUnits().add(new ExecutionUnit(id, f4, Integer.parseInt(ssl4.getText())));
				else
					app.getParts().get(partNumber).getExecutionUnits().set(3, new ExecutionUnit(id, f4, Integer.parseInt(ssl4.getText())));
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return true;
	}

	//capability
	//describes single offering, mostly used for devices and platforms
	//name.value
}