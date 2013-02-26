package org.universaal.tools.packaging.tool.gui;

import java.io.File;
import java.net.URI;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.tool.parts.Android;
import org.universaal.tools.packaging.tool.parts.Application;
import org.universaal.tools.packaging.tool.parts.ContainerUnit;
import org.universaal.tools.packaging.tool.parts.ContainerUnit.Container;
import org.universaal.tools.packaging.tool.parts.DeploymentUnit;
import org.universaal.tools.packaging.tool.parts.Embedding;
import org.universaal.tools.packaging.tool.parts.OS;
import org.universaal.tools.packaging.tool.parts.Platform;
import org.universaal.tools.packaging.tool.util.POMParser;

public class PagePartDU extends PageImpl {

	private IProject artifact;
	private POMParser p;
	private int partNumber;

	private Combo os1, platform1, cu1, emb1;
	private Text feat1;
	private Label emb1_lbl, krf1_lbl;
	private Button ckbOS1, ckbPL1, ckbCU1;

	protected PagePartDU(String pageName, int pn) {
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

		Label exp = new Label(container, SWT.NULL);
		exp.setText("You can choose alternatively an OS, a Platform or a Container");
		Label blk = new Label(container, SWT.NULL);
		blk.setText("");

		Label os = new Label(container, SWT.NULL);
		os.setText("Select this checkbox to add an OS as deployment unit");
		ckbOS1 = new Button(container, SWT.CHECK);
		ckbOS1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				if(ckbOS1.getSelection()){
					ckbPL1.setSelection(false);
					ckbCU1.setSelection(false); 
					//TODO
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Label pl = new Label(container, SWT.NULL);
		pl.setText("Select this checkbox to add a Platform as deployment unit");
		ckbPL1 = new Button(container, SWT.CHECK);
		ckbPL1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				if(ckbPL1.getSelection()){
					ckbOS1.setSelection(false);
					ckbCU1.setSelection(false);
					//TODO
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Label cu = new Label(container, SWT.NULL);
		cu.setText("Select this checkbox to add a specific Container as deployment unit");
		ckbCU1 = new Button(container, SWT.CHECK);
		ckbCU1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				if(ckbCU1.getSelection()){
					ckbPL1.setSelection(false);
					ckbOS1.setSelection(false);
					//TODO
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});		

		List<DeploymentUnit> dus = app.getParts().get(partNumber).getDeploymentUnits();
		/*for(int i = 0; i < dus.size(); i++){
			dus.get(i).getCu();
			dus.get(i).getId();
			OS osadsf = dus.get(i).getOsType();
			dus.get(i).getPlatformType();
		}*/

		while(dus.size() <= 2){
			dus.add(new DeploymentUnit(Application.defaultString, new ContainerUnit(new Android(Application.defaultString, Application.defaultString, URI.create(Application.defaultURL)))));
			//dus.add(new DeploymentUnit(id, osType));
			//dus.add(new DeploymentUnit(id, platformType));
		}

		Label label1 = new Label(container, SWT.NULL);
		os1 = new Combo(container, SWT.READ_ONLY);
		mandatory.add(os1);
		label1.setText("OS requirement");
		for(int i = 0; i < OS.values().length; i++)
			os1.add(OS.values()[i].toString());
		if(dus.get(0).getOsType() != null && !dus.get(0).getOsType().toString().isEmpty())
			os1.setText(dus.get(0).getOsType().toString());			
		else
			os1.setText(OS.WINDOWS.toString());			
		os1.setLayoutData(gd);

		Label label2 = new Label(container, SWT.NULL);
		platform1 = new Combo(container, SWT.READ_ONLY);
		mandatory.add(platform1);
		label2.setText("Platform requirement");
		for(int i = 0; i < Platform.values().length; i++)
			platform1.add(Platform.values()[i].toString());
		if(dus.get(0).getPlatformType() != null && !dus.get(0).getPlatformType().toString().isEmpty())
			platform1.setText(dus.get(0).getPlatformType().toString());		
		else
			platform1.setText(Platform.JAVA.toString());
		platform1.setLayoutData(gd);

		Label label3 = new Label(container, SWT.NULL);
		cu1 = new Combo(container, SWT.READ_ONLY);
		mandatory.add(cu1);
		label3.setText("Container requirement");
		for(int i = 0; i < Container.values().length; i++)
			cu1.add(Container.values()[i].toString());
		if(dus.get(0).getCu().getContainer() != null && !dus.get(0).getCu().getContainer().toString().isEmpty())
			cu1.setText(dus.get(0).getCu().getContainer().toString());			
		else
			cu1.setText(Container.FELIX.toString());
		cu1.setLayoutData(gd);

		cu1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				if(cu1.getText().equals(Container.ANDROID.toString())){
					emb1.setVisible(false);
					emb1_lbl.setVisible(false);
					krf1_lbl.setVisible(false);
					feat1.setVisible(false);
				}
				else if(cu1.getText().equals(Container.KARAF.toString())){
					emb1.setVisible(true);
					emb1_lbl.setVisible(true);
					krf1_lbl.setVisible(true);
					feat1.setVisible(true);
				}
				else{
					emb1.setVisible(false);
					emb1_lbl.setVisible(false);
					krf1_lbl.setVisible(false);
					feat1.setVisible(false);
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {				
			}
		});

		// embedding only applicable for KARAF value
		emb1_lbl = new Label(container, SWT.NULL);
		emb1 = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(emb1);
		emb1_lbl.setText("Embedding");
		for(int i = 0; i < Embedding.values().length; i++)
			emb1.add(Embedding.values()[i].toString());
		if(dus.get(0).getCu().getEmbedding() != null && !dus.get(0).getCu().getEmbedding().toString().isEmpty())
			emb1.setText(dus.get(0).getCu().getEmbedding().toString());			
		else
			emb1.setText(Embedding.ANY_CONTAINER.toString());
		emb1.setLayoutData(gd);

		// features only applicable for KARAF value
		krf1_lbl = new Label(container, SWT.NULL);
		feat1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(feat1);
		krf1_lbl.setText("Karaf features, comma separated");
		if(dus.get(0).getCu().getFeatures() != null && !dus.get(0).getCu().getFeatures().toString().isEmpty())
			feat1.setText(dus.get(0).getCu().getFeatures().toString());	
		else
			feat1.setText("example mvn:org.universAAL.middleware/mw.acl.interfaces");
		feat1.setLayoutData(gd);

		// ANDROID part


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