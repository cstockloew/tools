package org.universaal.tools.packaging.tool.gui;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.tool.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.Android;
import org.universaal.tools.packaging.tool.parts.Container;
import org.universaal.tools.packaging.tool.parts.ContainerUnit;
import org.universaal.tools.packaging.tool.parts.DeploymentUnit;
import org.universaal.tools.packaging.tool.parts.Embedding;
import org.universaal.tools.packaging.tool.parts.OS;
import org.universaal.tools.packaging.tool.parts.Platform;
import org.universaal.tools.packaging.tool.util.EffectivePOMContainer;
import org.universaal.tools.packaging.tool.util.KarafFeaturesGenerator;
import org.universaal.tools.packaging.tool.util.POMParser;
import org.universaal.tools.packaging.tool.validators.AlphabeticV;
import org.universaal.tools.packaging.tool.validators.UriV;

public class PagePartDU extends PageImpl {

	private IProject part;
	//private POMParser p;
	private int partNumber;
	private String value;

	private Combo os1, platform1, cu1, emb1;
	private Text andN, andD, andURI;
	private Button ckbOS1, ckbPL1, ckbCU1, ckbKar;

	protected PagePartDU(String pageName, int pn) {
		super(pageName, "Part "+(pn+1)+"/"+GUI.getInstance().getPartsCount()+
				" - Specify deployment requirements per part");
		this.partNumber = pn;

		value = "A";
		int charValue = value.charAt(0);
		value = String.valueOf( (char) (charValue - 1));
	}

	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);	

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		Label exp = new Label(container, SWT.NULL);
		exp.setText("You can choose alternatively an OS, a Platform or a Container.");
		Label blk = new Label(container, SWT.NULL);
		blk.setText("(only Karaf container is now fully supported)");

		FontData[] fD = exp.getFont().getFontData();
		fD[0].setStyle(SWT.BOLD);
		exp.setFont(new Font(container.getDisplay(), fD[0]));		

		Label os = new Label(container, SWT.NULL);
		os.setText("Select this checkbox to add an OS as deployment unit");
		ckbOS1 = new Button(container, SWT.CHECK);
		ckbOS1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				ckbPL1.setSelection(false);
				ckbOS1.setSelection(true);
				ckbCU1.setSelection(false);

				enableControls(new ArrayList<Control>(Arrays.asList(os1, platform1, cu1, emb1, ckbKar, andN, andD, andURI)));
				disableControls(new ArrayList<Control>(Arrays.asList(platform1, cu1, emb1, andN, ckbKar, andD, andURI)));
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Label l1 = new Label(container, SWT.NULL);
		l1.setText("Select this checkbox to add a Platform as deployment unit");
		ckbPL1 = new Button(container, SWT.CHECK);
		ckbPL1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				ckbPL1.setSelection(true);
				ckbOS1.setSelection(false);
				ckbCU1.setSelection(false);

				enableControls(new ArrayList<Control>(Arrays.asList(os1, platform1, cu1, emb1, ckbKar, andN, andD, andURI)));
				disableControls(new ArrayList<Control>(Arrays.asList(os1, cu1, emb1, ckbKar, andN, andD, andURI)));
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Label l2 = new Label(container, SWT.NULL);
		l2.setText("Select this checkbox to add a specific Container as deployment unit");
		ckbCU1 = new Button(container, SWT.CHECK);
		ckbCU1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				ckbPL1.setSelection(false);
				ckbOS1.setSelection(false);
				ckbCU1.setSelection(true);

				enableControls(new ArrayList<Control>(Arrays.asList(os1, platform1, cu1, emb1, ckbKar, andN, andD, andURI)));
				disableControls(new ArrayList<Control>(Arrays.asList(os1, platform1, andN, /*ckbKar,*/ andD, andURI)));
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});		

		Label label1 = new Label(container, SWT.NULL);
		os1 = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(os1);
		label1.setText("OS requirement");
		/*for(int i = 0; i < OS.values().length; i++)
			os1.add(OS.values()[i].toString());*/
		for(int i = 0; i < RequirementsDefinitions.get().listRequirements("OS_Requirement").size(); i++)
			os1.add(RequirementsDefinitions.get().listRequirements("OS_Requirement").get(i));
		
		/*
		if(!DUs.isEmpty() && DUs.get(0).getOsType() != null && !DUs.get(0).getOsType().toString().isEmpty())
			os1.setText(DUs.get(0).getOsType().toString());			
		else
			os1.select(0);
		*/		
		os1.setLayoutData(gd);

		Label label2 = new Label(container, SWT.NULL);
		platform1 = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(platform1);
		label2.setText("Platform requirement");
		/*for(int i = 0; i < Platform.values().length; i++)
			platform1.add(Platform.values()[i].toString());*/
		for(int i = 0; i < RequirementsDefinitions.get().listRequirements("Platform_Requirement").size(); i++)
			platform1.add(RequirementsDefinitions.get().listRequirements("Platform_Requirement").get(i));
		
		/*
		
		if(!DUs.isEmpty() && DUs.get(0).getPlatformType() != null && !DUs.get(0).getPlatformType().toString().isEmpty())
			platform1.setText(DUs.get(0).getPlatformType().toString());		
		else
			platform1.select(0);
		*/
		
		platform1.setLayoutData(gd);

		Label label3 = new Label(container, SWT.NULL);
		cu1 = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(cu1);
		label3.setText("Container requirement");
		/*for(int i = 0; i < Container.values().length; i++)
			cu1.add(Container.values()[i].toString());*/
		for(int i = 0; i < RequirementsDefinitions.get().listRequirements("Container_Name").size(); i++)
			cu1.add(RequirementsDefinitions.get().listRequirements("Container_Name").get(i));
		
		/*
		
		if(!DUs.isEmpty() && DUs.get(0).getCu().getContainer() != null && !DUs.get(0).getCu().getContainer().toString().isEmpty())
			cu1.setText(DUs.get(0).getCu().getContainer().toString());			
		else{
			cu1.select(0);
			enableControl(ckbKar);
		}
		
		*/
		
		cu1.setLayoutData(gd);

		cu1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {

				enableControls(new ArrayList<Control>(Arrays.asList(emb1, ckbKar, andN, andD, andURI)));

				if(cu1.getText().equals("android"))
					disableControls(new ArrayList<Control>(Arrays.asList(emb1, ckbKar)));

				else if(cu1.getText().equals("karaf"))
					disableControls(new ArrayList<Control>(Arrays.asList(andN, andD, andURI)));

				else
					disableControls(new ArrayList<Control>(Arrays.asList(emb1, ckbKar, andN, andD, andURI)));
			}

			public void widgetDefaultSelected(SelectionEvent e) {				
			}
		});

		// embedding only applicable for KARAF value
		Label l3 = new Label(container, SWT.NULL);
		emb1 = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(emb1);
		l3.setText("Embedding");
		for(int i = 0; i < Embedding.values().length; i++)
			emb1.add(Embedding.values()[i].toString());
		
		/*
		
		if(!DUs.isEmpty() && DUs.get(0).getCu().getEmbedding() != null && !DUs.get(0).getCu().getEmbedding().toString().isEmpty())
			emb1.setText(DUs.get(0).getCu().getEmbedding().toString());			
		else
			emb1.setText(Embedding.ANY_CONTAINER.toString());
		*/
		
		emb1.setLayoutData(gd);

		// ANDROID part
		Label l4 = new Label(container, SWT.NULL);
		andN = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(cu1);
		l4.setText("Android part name");
		
		/*
		
		if(!DUs.isEmpty() && DUs.get(0).getCu().getAndroidPart() != null && !DUs.get(0).getCu().getAndroidPart().getName().isEmpty())
			andN.setText(DUs.get(0).getCu().getAndroidPart().getName());		
		else
			andN.setText("");
		
		*/
		
		andN.addVerifyListener(new AlphabeticV());
		andN.setLayoutData(gd);

		Label l5 = new Label(container, SWT.NULL);
		andD = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(cu1);
		l5.setText("Android part description");
		
		/*
		if(!DUs.isEmpty() && DUs.get(0).getCu().getAndroidPart() != null && !DUs.get(0).getCu().getAndroidPart().getDescription().isEmpty())
			andD.setText(DUs.get(0).getCu().getAndroidPart().getDescription());		
		else
			andD.setText("");
		*/
		andD.addVerifyListener(new AlphabeticV());
		andD.setLayoutData(gd);

		Label l6 = new Label(container, SWT.NULL);
		andURI = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(cu1);
		l6.setText("Android part URI");
		
		/*
		if(!DUs.isEmpty() && DUs.get(0).getCu().getAndroidPart() != null && !DUs.get(0).getCu().getAndroidPart().getLocation().toString().isEmpty())
			andURI.setText(DUs.get(0).getCu().getAndroidPart().getLocation().toASCIIString());		
		else
			andURI.setText("");	
		*/
		andURI.addVerifyListener(new UriV());
		andURI.setLayoutData(gd);

		//		Label karFile = new Label(container, SWT.NULL);
		//		karFile.setText("Select this checkbox to create KAR file (valid if using Karaf container)");
		//		ckbKar = new Button(container, SWT.CHECK);

		Label empty1 = new Label(container, SWT.NULL);
		empty1.setText("");

		Label empty2 = new Label(container, SWT.NULL);
		empty2.setText("");

		Label waiting = new Label(container, SWT.NULL);
		waiting.setText("The generation of required stuff could take some time, please be patient...");


		//default configuration
		os1.select(0);
		platform1.select(0);
		cu1.select(0);
		emb1.setText(Embedding.anyContainer.toString());
		
		disableControls(new ArrayList<Control>(Arrays.asList(os1, platform1, /*cu1, emb1,*/ ckbKar, andN, andD, andURI)));
		setPageComplete(validate());
	}

	public void setArtifact(IProject part){
		this.part = part;
		//p = new POMParser(new File(part.getFile("pom.xml").getLocation()+""));
	}

	@Override
	public boolean nextPressed() {

		int charValue = value.charAt(0);
		String alph = String.valueOf( (char) (charValue + 1));
		value = alph;
		int numb = partNumber + 1;

		String id = "_"+numb+alph;

		if(ckbOS1.getSelection()){
			try{
				app.getAppParts().get(partNumber).getDeploymentUnits().get(partNumber).setDeploymentUnit(id, os1.getText(),DeploymentUnit.OS);
			} catch (IndexOutOfBoundsException e) {
				app.getAppParts().get(partNumber).getDeploymentUnits().add(partNumber, new DeploymentUnit(id, os1.getText(),DeploymentUnit.OS));
			}
		}
		else if(ckbPL1.getSelection()){
			try{
				app.getAppParts().get(partNumber).getDeploymentUnits().get(partNumber).setDeploymentUnit(id, platform1.getText(),DeploymentUnit.PLATFORM);
			} catch (IndexOutOfBoundsException e) {
				app.getAppParts().get(partNumber).getDeploymentUnits().add(partNumber, new DeploymentUnit(id, platform1.getText(),DeploymentUnit.PLATFORM));
			}
		}
		else if(ckbCU1.getSelection()){
			ContainerUnit cu = null;
			if(cu1.getText().equals(Container.KARAF)){

				KarafFeaturesGenerator krf = new KarafFeaturesGenerator();
				String karaf = krf.generate(this.part, true, partNumber);
				if(karaf != null && !karaf.isEmpty())
					cu = new ContainerUnit(emb1.getText(), karaf);
				else
					cu = new ContainerUnit(emb1.getText(), "");				
			}
			else if(cu1.getText().equals(Container.ANDROID)){
				if(andURI.getText() == null || andURI.getText().isEmpty()){
					EffectivePOMContainer.setDocument(part.getName());
					String fileName = EffectivePOMContainer.getArtifactId()+"-"+EffectivePOMContainer.getVersion()+".jar";
					//andURI.setText("file://../bin/part"+numb+"/"+fileName);	
					andURI.setText("bin/part"+numb+"/"+fileName);	
				}
				cu = new ContainerUnit(new Android(andN.getText(), andD.getText(), URI.create(removeBlanks(andURI.getText()))));
			}
			else if(!cu1.getText().equals(Container.KARAF.toString()) && !cu1.getText().equals(Container.ANDROID)){
				cu = new ContainerUnit(cu1.getText());
			}
			try{
				app.getAppParts().get(partNumber).getDeploymentUnits().get(partNumber).setDeploymentUnit(id, cu);
			} catch (IndexOutOfBoundsException e){
				app.getAppParts().get(partNumber).getDeploymentUnits().add(partNumber, new DeploymentUnit(id, cu));
			}
				
		}

		//serializeMPA();
		return true;
	}

	@Override
	public void setVisible(boolean visible){
		super.setVisible(visible);
		if(visible) loadData();
	}
	
	private void loadData(){
		
		List<DeploymentUnit> DUs = app.getAppParts().get(partNumber).getDeploymentUnits();

		try{
			if(DUs.get(partNumber).getType().equals(DeploymentUnit.OS)) ckbOS1.notifyListeners(SWT.Selection, new Event());
			else if(DUs.get(partNumber).getType().equals(DeploymentUnit.PLATFORM)) ckbPL1.notifyListeners(SWT.Selection, new Event());
			else if(DUs.get(partNumber).getType().equals(DeploymentUnit.CONTAINER)) ckbCU1.notifyListeners(SWT.Selection, new Event());
			
		} catch (Exception e){
			if(app.getAppRequirements().deploymentUnitType.equals(DeploymentUnit.OS)) ckbOS1.notifyListeners(SWT.Selection, new Event());
			else if(app.getAppRequirements().deploymentUnitType.equals(DeploymentUnit.PLATFORM)) ckbPL1.notifyListeners(SWT.Selection, new Event());
			else if(app.getAppRequirements().deploymentUnitType.equals(DeploymentUnit.CONTAINER)) ckbCU1.notifyListeners(SWT.Selection, new Event());
		}
				
		try{
			os1.setText(DUs.get(partNumber).getUnit());			
		} catch (Exception e) {
			if(!app.getAppRequirements().OS_Requirements.isEmpty()) os1.setText(app.getAppRequirements().OS_Requirements);
				
		}
		
		try{
			platform1.setText(DUs.get(partNumber).getUnit());			
		} catch (Exception e) {
			if(!app.getAppRequirements().Platform_Requirement.isEmpty()) platform1.setText(app.getAppRequirements().Platform_Requirement);
		}
		
		try{
			cu1.setText(DUs.get(partNumber).getCu().getContainer().toString());		
		} catch (Exception e) {
			cu1.setText(app.getAppRequirements().Container_Name);
			if(!app.getAppRequirements().Container_Name.isEmpty()) enableControl(ckbKar);
		}
		
		try{
			emb1.setText(DUs.get(partNumber).getCu().getEmbedding().toString());			
		} catch (Exception e) {
			emb1.setText(app.getAppRequirements().embedding);
		}
		
		try{
			andN.setText(DUs.get(partNumber).getCu().getAndroidPart().getName());		
		} catch (Exception e) {
			andN.setText(app.getAppRequirements().android.getName());
		}
		
		try{
			andD.setText(DUs.get(partNumber).getCu().getAndroidPart().getDescription());		
		} catch (Exception e) {
			andD.setText(app.getAppRequirements().android.getDescription());
		}
		
		try{
			andURI.setText(DUs.get(partNumber).getCu().getAndroidPart().getLocation().toASCIIString());		
		} catch (Exception e) {
			andURI.setText(app.getAppRequirements().android.getLocation().toASCIIString());
		}
	}
	
	private void disableControl(Control c){
		if(c != null)
			c.setEnabled(false);
	}

	private void disableControls(List<Control> list){
		if(list != null && !list.isEmpty())
			for(int i = 0; i < list.size(); i++)
				if(list.get(i) != null)
					list.get(i).setEnabled(false);
	}

	private void enableControl(Control c){
		if(c != null)
			c.setEnabled(true);
	}

	private void enableControls(List<Control> list){
		if(list != null && !list.isEmpty())
			for(int i = 0; i < list.size(); i++)
				if(list.get(i) != null)
					list.get(i).setEnabled(true);
	}
}