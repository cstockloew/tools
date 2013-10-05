package org.universaal.tools.packaging.tool.gui;

import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolTip;
import org.universaal.tools.packaging.tool.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.Capability;
import org.universaal.tools.packaging.tool.parts.Container;
import org.universaal.tools.packaging.tool.parts.ExecutionUnit;
import org.universaal.tools.packaging.tool.parts.MiddlewareVersion;
import org.universaal.tools.packaging.tool.parts.Space;
import org.universaal.tools.packaging.tool.util.XSDParser;
import org.universaal.tools.packaging.tool.validators.AlphabeticV;
import org.universaal.tools.packaging.tool.validators.IntegerV;

public class PagePartPC extends PageImpl {

	private int partNumber;

	private Combo /*targetSpace,*/ mw_version, targetContainerName, targetContainerVersion;
	private TextExt /*targetSpaceVersion,*/ targetOntologies/*, targetContainerVersion/*, targetDeploymentTool*/;
	private Button ckbMoreReqs;
	
	protected PagePartPC(String pageName, int pn) {
		super(pageName, "Part "+(pn+1)+"/"+GUI.getInstance().getPartsCount()+
				" - Specify capabilities per part");
		this.partNumber = pn;
	}

	public void createControl(Composite parent) {

		XSDParser XSDtooltip = XSDParser.get(XSD_VERSION);
		
		container = new Composite(parent, SWT.NULL);
		setControl(container);	

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		Properties capabilities = app.getAppParts().get(partNumber).getPartCapabilities();

		/*
		
		Label l1 = new Label(container, SWT.NULL);
		targetSpace = new Combo (container, SWT.READ_ONLY);
		Space[] spaceV = Space.values();
		for(int i = 0; i < spaceV.length; i++){
			targetSpace.add(spaceV[i].toString());
		}
		mandatory.add(targetSpace);
		l1.setText("* Target Space");
		targetSpace.setText(capabilities.getProperty(Capability.MANDATORY_TARGET_SPACE));
		targetSpace.setLayoutData(gd);	

		Label l2 = new Label(container, SWT.NULL);
		targetSpaceVersion = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(targetSpaceVersion);
		l2.setText("* Target Space Version");
		targetSpaceVersion.setText(capabilities.getProperty(Capability.MANDATORY_TARGET_SPACE_VERSION));			
		targetSpaceVersion.addVerifyListener(new IntegerV());
		targetSpaceVersion.setLayoutData(gd);	

		*/

		Label l3 = new Label(container, SWT.NULL);
		mw_version = new Combo(container, SWT.READ_ONLY);
		mandatory.add(mw_version);
		l3.setText("* Middleware Version");
		for(int i = 0; i < RequirementsDefinitions.get().listRequirements("MW_Version").size(); i++)
			mw_version.add(RequirementsDefinitions.get().listRequirements("MW_Version").get(i));
		mw_version.setText(capabilities.getProperty(Capability.MANDATORY_MW_VERSION));			
		mw_version.setLayoutData(gd);	

		Label l4 = new Label(container, SWT.NULL);
		targetOntologies = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(targetOntologies);
		l4.setText("Ontologies, comma separated");
		targetOntologies.setText(capabilities.getProperty(Capability.MANDATORY_ONTOLOGIES));			
		targetOntologies.addVerifyListener(new AlphabeticV());
		targetOntologies.setLayoutData(gd);	
		targetOntologies.addTooltip(XSDtooltip.find("app.applicationOntology"));
		
		Label l5 = new Label(container, SWT.NULL);
		targetContainerName = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(targetContainerName);
		l5.setText("Target Container Name");
		for(int i = 0; i < RequirementsDefinitions.get().listRequirements("Container_Name").size(); i++)
			targetContainerName.add(RequirementsDefinitions.get().listRequirements("Container_Name").get(i));
		targetContainerName.setText(capabilities.getProperty(Capability.MANDATORY_TARGET_CONTAINER_NAME));			
		targetContainerName.setLayoutData(gd);	

		Label l6 = new Label(container, SWT.NULL);
		targetContainerVersion = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(targetContainerVersion);
		l6.setText("Target Container Version");
		for(int i = 0; i < RequirementsDefinitions.get().listRequirements("Container_Version").size(); i++)
			targetContainerVersion.add(RequirementsDefinitions.get().listRequirements("Container_Version").get(i));
		targetContainerVersion.setText(capabilities.getProperty(Capability.MANDATORY_TARGET_CONTAINER_VERSION));			
		targetContainerVersion.setLayoutData(gd);	
		
		Label l7 = new Label(container, SWT.NULL);
		l7.setText("Add custom requirements");
		ckbMoreReqs = new Button(container, SWT.CHECK);
		ckbMoreReqs.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {

				/*
				if(ckbMoreReqs.getSelection()){
					ckbPL1.setSelection(false);
					ckbCU1.setSelection(false); 

					disableControls(new ArrayList<Control>(Arrays.asList(platform1, cu1, emb1, andN, ckbKar, andD, andURI)));
				}

				if(!ckbMoreReqs.getSelection() && !ckbPL1.getSelection() && !ckbCU1.getSelection())
					setPageComplete(false);
				else
					setPageComplete(true);
				*/
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		/*
		Label l7 = new Label(container, SWT.NULL);
		targetDeploymentTool = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(targetDeploymentTool);
		l7.setText("* Target Deployment Tool");
		targetDeploymentTool.setText(capabilities.getProperty(Capability.MANDATORY_TARGET_DEPLOYMENT_TOOL));			
		targetDeploymentTool.addVerifyListener(new AlphabeticV());
		targetDeploymentTool.setLayoutData(gd);	
		targetDeploymentTool.addTooltip(Tooltips.DEPLOYMENT_TOOLTIP);
		*/
		
		/*
		final ToolTip t = Tooltips.getDeploymentToolTooltip();
		targetDeploymentTool.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {
				t.setVisible(false);				
			}

			public void focusGained(FocusEvent e) {
				t.setVisible(true);	
			}
		});
		 */
		
		/*
		targetSpace.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getAppParts().get(partNumber).setCapability(Capability.MANDATORY_TARGET_SPACE, targetSpace.getText());				
			}
		});
		*/
		
		/*
		targetSpace.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				app.getAppParts().get(partNumber).setCapability(Capability.MANDATORY_TARGET_SPACE, targetSpace.getText());				
				setPageComplete(validate());
			}
		});
		
		targetSpaceVersion.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getAppParts().get(partNumber).setCapability(Capability.MANDATORY_TARGET_SPACE_VERSION, targetSpaceVersion.getText());				
				setPageComplete(validate());
			}
		});
		
		*/
		
		/*
		mw_version.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getAppParts().get(partNumber).setCapability(Capability.MANDATORY_MW_VERSION, mw_version.getText());				
			}
		});*/
		mw_version.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				app.getAppParts().get(partNumber).setCapability(Capability.MANDATORY_MW_VERSION, mw_version.getText());				
				setPageComplete(validate());
			}
		});
		
		targetOntologies.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getAppParts().get(partNumber).setCapability(Capability.MANDATORY_ONTOLOGIES, targetOntologies.getText());				
			}
		});
		/*
		targetContainerName.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getAppParts().get(partNumber).setCapability(Capability.MANDATORY_TARGET_CONTAINER_NAME, targetContainerName.getText());				
			}
		});
		*/
		targetContainerName.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				app.getAppParts().get(partNumber).setCapability(Capability.MANDATORY_TARGET_CONTAINER_NAME, targetContainerName.getText());				
				setPageComplete(validate());
			}
		});
		
		targetContainerVersion.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				app.getAppParts().get(partNumber).setCapability(Capability.MANDATORY_TARGET_CONTAINER_VERSION, targetContainerVersion.getText());				
				setPageComplete(validate());
			}
		});
		/*
		targetDeploymentTool.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getAppParts().get(partNumber).setCapability(Capability.MANDATORY_TARGET_DEPLOYMENT_TOOL, targetDeploymentTool.getText());				
				setPageComplete(validate());
			}
		});
		*/
	}

	@Override
	public IWizardPage getNextPage(){
		if (ckbMoreReqs.getSelection()){
			//System.out.println(app.getAppRequirements().getRequirementsList().size());
			return super.getNextPage();
		}
		else{
			app.getAppRequirements().clear();
			//System.out.println(app.getAppRequirements().getRequirementsList().size());
			return super.getNextPage().getNextPage();
		}
	}
	
	@Override
	public boolean nextPressed() {
		serializeMPA();
		return true;
	}
	
	public void setArtifact(IProject artifact){
		//this.artifact = artifact;
		//p = new POMParser(new File(artifact.getFile("pom.xml").getLocation()+""));
	}
}