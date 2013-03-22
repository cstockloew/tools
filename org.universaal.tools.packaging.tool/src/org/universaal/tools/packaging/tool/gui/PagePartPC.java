package org.universaal.tools.packaging.tool.gui;

import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.Capability;
import org.universaal.tools.packaging.tool.parts.Container;
import org.universaal.tools.packaging.tool.parts.MiddlewareVersion;
import org.universaal.tools.packaging.tool.parts.Space;
import org.universaal.tools.packaging.tool.validators.AlphabeticV;
import org.universaal.tools.packaging.tool.validators.IntegerV;

public class PagePartPC extends PageImpl {

	//private IProject artifact;
	//private POMParser p;
	private int partNumber;

	private Combo targetSpace, mw_version, targetContainerName;
	private Text targetSpaceVersion, targetOntologies, targetContainerVersion, targetDeploymentTool;

	protected PagePartPC(String pageName, int pn) {
		super(pageName, "Specify capabilities per part");
		this.partNumber = pn;
	}

	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);	

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		Properties capabilities = app.getParts().get(partNumber).getPartCapabilities();

		Label l1 = new Label(container, SWT.NULL);
		targetSpace = new Combo (container, SWT.READ_ONLY);
		Space[] spaceV = Space.values();
		for(int i = 0; i < spaceV.length; i++){
			targetSpace.add(spaceV[i].toString());
		}
		mandatory.add(targetSpace);
		l1.setText("* Target Space");
		targetSpace.setText(capabilities.getProperty(Capability.Mandatory.TARGET_SPACE.toString()));
		targetSpace.setLayoutData(gd);	

		Label l2 = new Label(container, SWT.NULL);
		targetSpaceVersion = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(targetSpaceVersion);
		l2.setText("* Target Space Version");
		targetSpaceVersion.setText(capabilities.getProperty(Capability.Mandatory.TARGET_SPACE_VERSION.toString()));			
		targetSpaceVersion.addVerifyListener(new IntegerV());
		targetSpaceVersion.setLayoutData(gd);	

		Label l3 = new Label(container, SWT.NULL);
		mw_version = new Combo(container, SWT.READ_ONLY);
		mandatory.add(mw_version);
		l3.setText("* Middleware Version");
		for(int i = 0; i < MiddlewareVersion.getMWversion().length; i++)
			mw_version.add(MiddlewareVersion.getMWversion()[i]);
		mw_version.setText(capabilities.getProperty(Capability.Mandatory.MW_VERSION.toString()));			
		mw_version.setLayoutData(gd);	

		Label l4 = new Label(container, SWT.NULL);
		targetOntologies = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(targetOntologies);
		l4.setText("Ontologies, comma separated");
		targetOntologies.setText(capabilities.getProperty(Capability.Mandatory.ONTOLOGIES.toString()));			
		targetOntologies.addVerifyListener(new AlphabeticV());
		targetOntologies.setLayoutData(gd);	

		Label l5 = new Label(container, SWT.NULL);
		targetContainerName = new Combo(container, SWT.READ_ONLY);
		mandatory.add(targetContainerName);
		l5.setText("* Target Container Name");
		for(int i = 0; i < Container.values().length; i++)
			targetContainerName.add(Container.values()[i].toString());
		targetContainerName.setText(capabilities.getProperty(Capability.Mandatory.TARGET_SPACE_VERSION.toString()));			
		targetContainerName.setLayoutData(gd);	

		Label l6 = new Label(container, SWT.NULL);
		targetContainerVersion = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(targetContainerVersion);
		l6.setText("* Target Container Version");
		targetContainerVersion.setText(capabilities.getProperty(Capability.Mandatory.TARGET_CONTAINER_VERSION.toString()));			
		targetContainerVersion.addVerifyListener(new IntegerV());
		targetContainerVersion.setLayoutData(gd);	

		Label l7 = new Label(container, SWT.NULL);
		targetDeploymentTool = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(targetDeploymentTool);
		l7.setText("* Target Deployment Tool");
		targetDeploymentTool.setText(capabilities.getProperty(Capability.Mandatory.TARGET_DEPLOYMENT_TOOL.toString()));			
		targetDeploymentTool.addVerifyListener(new AlphabeticV());
		targetDeploymentTool.setLayoutData(gd);	

		targetSpace.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getParts().get(partNumber).setCapability(Capability.Mandatory.TARGET_SPACE.toString(), targetSpace.getText());				
			}
		});
		targetSpaceVersion.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getParts().get(partNumber).setCapability(Capability.Mandatory.TARGET_SPACE_VERSION.toString(), targetSpaceVersion.getText());				
			}
		});
		mw_version.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getParts().get(partNumber).setCapability(Capability.Mandatory.MW_VERSION.toString(), mw_version.getText());				
			}
		});
		targetOntologies.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getParts().get(partNumber).setCapability(Capability.Mandatory.ONTOLOGIES.toString(), targetOntologies.getText());				
			}
		});
		targetContainerName.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getParts().get(partNumber).setCapability(Capability.Mandatory.TARGET_CONTAINER_NAME.toString(), targetContainerName.getText());				
			}
		});
		targetContainerVersion.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getParts().get(partNumber).setCapability(Capability.Mandatory.TARGET_CONTAINER_VERSION.toString(), targetContainerVersion.getText());				
			}
		});
		targetDeploymentTool.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getParts().get(partNumber).setCapability(Capability.Mandatory.TARGET_DEPLOYMENT_TOOL.toString(), targetDeploymentTool.getText());				
			}
		});
	}

	public void setArtifact(IProject artifact){
		//this.artifact = artifact;
		//p = new POMParser(new File(artifact.getFile("pom.xml").getLocation()+""));
	}
}