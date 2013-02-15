//package org.universaal.tools.packaging.tool.gui;
//
//import java.io.File;
//
//import org.eclipse.core.resources.IProject;
//import org.eclipse.jface.resource.ImageDescriptor;
//import org.eclipse.jface.wizard.WizardPage;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.Combo;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Event;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Listener;
//import org.eclipse.swt.widgets.Text;
//import org.universaal.tools.packaging.tool.parts.DeploymentUnit;
//import org.universaal.tools.packaging.tool.parts.DeploymentUnit.ContainerUnit;
//import org.universaal.tools.packaging.tool.parts.ExecutionUnit;
//import org.universaal.tools.packaging.tool.parts.Part;
//import org.universaal.tools.packaging.tool.parts.universaal.aal_mpa.v1_0.OsType;
//import org.universaal.tools.packaging.tool.parts.universaal.aal_mpa.v1_0.PlatformType;
//import org.universaal.tools.packaging.tool.util.POMParser;
//
//public class GUIpage extends WizardPage {
//
//	private Composite container;
//	private IProject artifact;
//
//	private Combo osList, platformList, containerList;
//
//	private Part part;
//	private DeploymentUnit du;
//	private ContainerUnit cu;
//	private ExecutionUnit eu;
//
//	protected GUIpage(String pageName, String title, ImageDescriptor titleImage, IProject artifact) {
//		super(pageName, title, titleImage);
//		this.artifact = artifact;
//	}
//
//	public void createControl(Composite parent) {		
//
//		container = new Composite(parent, SWT.NULL);
//		GridLayout layout = new GridLayout();
//		container.setLayout(layout);
//
//		layout.numColumns = 2;
//		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
//
//		POMParser p = new POMParser(new File(artifact.getFile("pom.xml").getLocation()+""));
//
//		Label label1 = new Label(container, SWT.NULL);
//		Text text1 = new Text(container, SWT.BORDER | SWT.SINGLE);
//		label1.setText("Artifact ID");
//		text1.setText(p.getArtifactID());			
//		text1.setLayoutData(gd);
//
//		Label label2 = new Label(container, SWT.NULL);
//		Text text2 = new Text(container, SWT.BORDER | SWT.SINGLE);
//		label2.setText("Group ID");
//		text2.setText(p.getGroupID());
//		text2.setLayoutData(gd);
//
//		Label label3 = new Label(container, SWT.NULL);
//		Text text3 = new Text(container, SWT.BORDER | SWT.SINGLE);
//		label3.setText("URL");
//		text3.setText(p.getUrl());
//		text3.setLayoutData(gd);
//
//		Label label4 = new Label(container, SWT.NULL);
//		Text text4 = new Text(container, SWT.BORDER | SWT.SINGLE);
//		label4.setText("Version");
//		text4.setText(p.getVersion());
//		text4.setLayoutData(gd);
//
//		Label label5 = new Label(container, SWT.NULL);
//		Text text5 = new Text(container, SWT.BORDER | SWT.SINGLE);
//		label5.setText("Packaging");
//		text5.setText(p.getPackaging());
//		text5.setLayoutData(gd);
//
//		Label label6 = new Label(container, SWT.NULL);
//		Text text6 = new Text(container, SWT.BORDER | SWT.SINGLE);
//		label6.setText("Description");
//		text6.setText(p.getDescription());
//		text6.setLayoutData(gd);
//
//		Label label8 = new Label(container, SWT.NULL);
//		Text text8 = new Text(container, SWT.BORDER | SWT.SINGLE);
//		label8.setText("Name");
//		text8.setText(p.getName());
//		text8.setLayoutData(gd);
//
//		//label.setText("CHECK");
//		//text.setText(artifacts.get(i).getName());
//
//		/*text.addKeyListener(new KeyListener() {
//
//				public void keyPressed(KeyEvent e) {
//				}
//
//				public void keyReleased(KeyEvent e) {
//					if (!text.getText().isEmpty()) {
//						setPageComplete(true);
//					}
//				}
//
//			});*/			
//
//		// Required to avoid an error in the system
//		setControl(container);
//		setPageComplete(true);
//		//setPageComplete(false);
//
//		part = new Part();
//		part.setPartId(p.getGroupID()+p.getArtifactID());
//
//		du = new DeploymentUnit();		
//		du.setId(p.getGroupID()+p.getArtifactID());
//
//		osList = new Combo(container, SWT.NULL);
//		for(int i = 0; i < OsType.values().length; i++)
//			osList.add(OsType.values()[i].value());
//		osList.addListener(SWT.Selection, new Listener() {
//
//			public void handleEvent(Event event) {
//				du.setOsUnit(OsType.fromValue(osList.getItem(osList.getSelectionIndex())));
//			}
//		});
//
//		platformList = new Combo(container, SWT.NULL);
//		for(int i = 0; i < PlatformType.values().length; i++)
//			platformList.add(PlatformType.values()[i].value());
//		platformList.addListener(SWT.Selection, new Listener() {
//
//			public void handleEvent(Event event) {
//				du.setPlatformUnit(PlatformType.fromValue(platformList.getItem(platformList.getSelectionIndex())));					
//			}
//		});		
//
//		cu = new ContainerUnit();
//		containerList = new Combo(container, SWT.NULL);
//		cu.setEquinox(value);
//		cu.setFelix(value);
//		cu.setKaraf(value);
//		cu.setTomcat(value);
//		cu.getKaraf().setEmbedding(value);
//		cu.getKaraf().setEmbeddingName(value);
//		cu.getKaraf().setFeatures(value);	
//
//		du.setContainerUnit(cu);	
//
//		eu = new ExecutionUnit();		
//		eu.setConfigFiles(value);
//		eu.setDeploymentUnit(value);
//		eu.setSpaceStartLevel(value);
//
//		part.getExecutionUnit().add(eu);
//		part.getDeploymentUnit().add(du);	
//	}
//}