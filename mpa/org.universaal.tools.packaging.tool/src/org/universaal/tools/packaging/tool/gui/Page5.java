package org.universaal.tools.packaging.tool.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.tool.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.ApplicationManagement.RemoteManagement;
import org.universaal.tools.packaging.tool.util.POMParser;
import org.universaal.tools.packaging.tool.validators.AlphabeticV;

public class Page5 extends PageImpl {

	private Text contact;
	private List<Text> artifacts;
	private List<Text> protocols;
	private List<Text> versions;

	protected Page5(String pageName) {
		super(pageName, "Specify details for assistance");

		artifacts = new ArrayList<Text>();
		protocols = new ArrayList<Text>();
		versions = new ArrayList<Text>();
	}

	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);	

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		List<IProject> parts = GUI.getInstance().getParts();

		List<RemoteManagement> remoteM = app.getAppManagement().getRemoteManagement();
		while(remoteM.size() < parts.size()){
			remoteM.add(app.getAppManagement().new RemoteManagement());
		}

		Label l1 = new Label(container, SWT.NULL);
		contact = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(contact);
		l1.setText("Contact Person");
		contact.setText(app.getAppManagement().getContact());		
		contact.addVerifyListener(new AlphabeticV());
		contact.setLayoutData(gd);	
		contact.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getAppManagement().setContact(contact.getText());
			}
		});

		Label shadow_sep_h = new Label(container, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
		shadow_sep_h.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label shadow_sep_h1 = new Label(container, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
		shadow_sep_h1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		for(int k = 0; k < parts.size(); k++){

			POMParser p = new POMParser(new File(parts.get(k).getFile("pom.xml").getLocation()+""));

			Label l2 = new Label(container, SWT.NULL);
			l2.setText("Artifact #"+(k+1)+" ID");
			Text artifact = new Text(container, SWT.BORDER | SWT.SINGLE);
			//mandatory.add(artifact);
			artifact.setText(p.getArtifactID());			
			artifacts.add(artifact);
			contact.addVerifyListener(new AlphabeticV());
			artifact.addKeyListener(new FullListener());
			artifact.setLayoutData(gd);

			Label l3 = new Label(container, SWT.NULL);
			l3.setText("Protocols for assistance, comma separated");
			Text protocol = new Text(container, SWT.BORDER | SWT.SINGLE);
			protocol.setText("");
			//mandatory.add(protocol);			
			protocols.add(protocol);
			protocol.addKeyListener(new FullListener());
			protocol.setLayoutData(gd);

			Label l4 = new Label(container, SWT.NULL);
			l4.setText("Version");
			Text version = new Text(container, SWT.BORDER | SWT.SINGLE);
			//mandatory.add(version);
			version.setText(p.getVersion());
			versions.add(version);
			version.addKeyListener(new FullListener());
			version.setLayoutData(gd);			

			if(k != (parts.size() - 1)){
				Label shadow_sep_h2 = new Label(container, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
				shadow_sep_h2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				Label shadow_sep_h3 = new Label(container, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);		
				shadow_sep_h3.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			}
		}

		setPageComplete(validate());
	}

	@Override
	public boolean nextPressed() {

		for(int j = 0; j < artifacts.size(); j++){

			app.getAppManagement().getRemoteManagement().get(j).getSoftware().setArtifactID(artifacts.get(j).getText());
			app.getAppManagement().getRemoteManagement().get(j).getSoftware().getVersion().setVersion(versions.get(j).getText());

			String[] ps = protocols.get(j).getText().split(",");
			for(int i = 0; i < ps.length; i++)
				if(ps[i] != null)
					app.getAppManagement().getRemoteManagement().get(j).getProtocols().add(ps[i]);
		}

		return true;
	}
}