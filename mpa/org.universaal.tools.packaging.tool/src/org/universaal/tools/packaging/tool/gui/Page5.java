package org.universaal.tools.packaging.tool.gui;

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.tool.parts.ApplicationManagement.RemoteManagement;

public class Page5 extends PageImpl {

	private Text contact, artifactID1, artifactID2, artifactID3, prot1, prot2, prot3, v1, v2, v3;

	protected Page5(String pageName) {
		super(pageName, "Specify details for the MPA you are creating.");
	}

	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		setControl(container);	

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		List<RemoteManagement> remoteM = app.getManagement().getRemoteManagement();
		while(remoteM.size() <= 2){
			remoteM.add(app.getManagement().new RemoteManagement());
		}

		Label l1 = new Label(container, SWT.NULL);
		contact = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(contact);
		l1.setText("Contact Person");
		contact.setText(app.getManagement().getContact());			
		contact.setLayoutData(gd);	
		contact.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getManagement().setContact(contact.getText());
			}
		});

		Label l2 = new Label(container, SWT.NULL);
		artifactID1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(artifactID1);
		l2.setText("Artifact #1 ID");
		artifactID1.setText(remoteM.get(0).getSoftware().getArtifactID());			
		artifactID1.setLayoutData(gd);	
		artifactID1.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getManagement().getRemoteManagement().get(0).getSoftware().setArtifactID(artifactID1.getText());
			}
		});

		Label l3 = new Label(container, SWT.NULL);
		prot1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(prot1);
		l3.setText("Protocols, comma separated");
		List<String> protocols1 = remoteM.get(0).getProtocol();
		String prots1 = "";
		for(int i = 0; i < protocols1.size(); i++)
			prots1 = prots1.concat(protocols1.get(i)+" ");
		prot1.setText(prots1);			
		prot1.setLayoutData(gd);	
		prot1.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				//multipartApplication.getApplications().get(partNumber).getManagement().getRemoteManagement().get(0).setProtocol(prot1.getText());
			}
		});

		Label l4 = new Label(container, SWT.NULL);
		v1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(v1);
		l4.setText("Version");
		v1.setText(remoteM.get(0).getSoftware().getVersion().getVersion());			
		v1.setLayoutData(gd);	
		v1.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getManagement().getRemoteManagement().get(0).getSoftware().getVersion().setVersion(v1.getText());
			}
		});

		Label l5 = new Label(container, SWT.NULL);
		artifactID2 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(artifactID1);
		l5.setText("Artifact #2 ID");
		artifactID2.setText(remoteM.get(1).getSoftware().getArtifactID());			
		artifactID2.setLayoutData(gd);	
		artifactID2.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getManagement().getRemoteManagement().get(1).getSoftware().setArtifactID(artifactID2.getText());
			}
		});

		Label l6 = new Label(container, SWT.NULL);
		prot2 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(prot1);
		l6.setText("Protocols, comma separated");
		List<String> protocols2 = remoteM.get(1).getProtocol();
		String prots2 = "";
		for(int i = 0; i < protocols2.size(); i++)
			prots2 = prots2.concat(protocols2.get(i)+" ");
		prot2.setText(prots2);					
		prot2.setLayoutData(gd);	
		prot2.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				//multipartApplication.getApplications().get(partNumber).getManagement().getRemoteManagement().get(1).setProtocol(prot2.getText());
			}
		});

		Label l7 = new Label(container, SWT.NULL);
		v2 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(v1);
		l7.setText("Version");
		v2.setText(remoteM.get(1).getSoftware().getVersion().getVersion());			
		v2.setLayoutData(gd);	
		v2.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getManagement().getRemoteManagement().get(1).getSoftware().getVersion().setVersion(v2.getText());
			}
		});

		Label l8 = new Label(container, SWT.NULL);
		artifactID3 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(artifactID1);
		l8.setText("Artifact #3 ID");
		artifactID3.setText(remoteM.get(2).getSoftware().getArtifactID());			
		artifactID3.setLayoutData(gd);	
		artifactID3.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getManagement().getRemoteManagement().get(2).getSoftware().setArtifactID(artifactID3.getText());
			}
		});

		Label l9 = new Label(container, SWT.NULL);
		prot3 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(prot1);
		l9.setText("Protocols, comma separated");
		List<String> protocols3 = remoteM.get(2).getProtocol();
		String prots3 = "";
		for(int i = 0; i < protocols3.size(); i++)
			prots3 = prots3.concat(protocols3.get(i)+" ");
		prot3.setText(prots2);			
		prot3.setLayoutData(gd);	
		prot3.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				//multipartApplication.getApplications().get(partNumber).getManagement().getRemoteManagement().get(2).setProtocol(prot3.getText());
			}
		});

		Label l10 = new Label(container, SWT.NULL);
		v3 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(v1);
		l10.setText("Version");
		v3.setText(remoteM.get(2).getSoftware().getVersion().getVersion());			
		v3.setLayoutData(gd);	
		v3.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getManagement().getRemoteManagement().get(2).getSoftware().getVersion().setVersion(v3.getText());
			}
		});
	}

	@Override
	public IWizardPage getNextPage(){

		if(!prot1.getText().isEmpty()){
			String[] ps = prot1.getText().split(",");
			for(int i = 0; i < ps.length; i++)
				if(ps[i] != null)
					app.getManagement().getRemoteManagement().get(0).getProtocol().add(ps[i]);
		}
		if(!prot2.getText().isEmpty()){
			String[] ps = prot2.getText().split(",");
			for(int i = 0; i < ps.length; i++)
				if(ps[i] != null)
					app.getManagement().getRemoteManagement().get(1).getProtocol().add(ps[i]);
		}
		if(!prot3.getText().isEmpty()){
			String[] ps = prot3.getText().split(",");
			for(int i = 0; i < ps.length; i++)
				if(ps[i] != null)
					app.getManagement().getRemoteManagement().get(2).getProtocol().add(ps[i]);
		}

		return super.getNextPage();
	}

	@Override
	public void nextPressed() {
		// TODO Auto-generated method stub

	}
}