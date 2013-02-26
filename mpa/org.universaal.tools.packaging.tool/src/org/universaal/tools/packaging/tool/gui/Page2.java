package org.universaal.tools.packaging.tool.gui;

import java.net.URI;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class Page2 extends PageImpl {

	private Text certificate, person, email, organization, phone, address, web;
	private boolean addLicense;

	protected Page2(String pageName) {
		super(pageName, "Specify contact details");
	}

	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 3;
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 2;

		Label l1 = new Label(container, SWT.NULL);
		certificate = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(certificate);
		l1.setText("Security certificate");
		certificate.setText(app.getApplication().getApplicationProvider().getCertificate().toString());			
		certificate.setLayoutData(gd);				

		Label l2 = new Label(container, SWT.NULL);
		person = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(person);
		l2.setText("Contact person");
		person.setText(app.getApplication().getApplicationProvider().getContactPerson());			
		person.setLayoutData(gd);				

		Label l3 = new Label(container, SWT.NULL);
		email = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(email);
		l3.setText("Contact e-mail");
		email.setText(app.getApplication().getApplicationProvider().getContactPerson());			
		email.setLayoutData(gd);	

		Label l4 = new Label(container, SWT.NULL);
		organization = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(organization);
		l4.setText("Organization name");
		organization.setText(app.getApplication().getApplicationProvider().getOrganizationName());			
		organization.setLayoutData(gd);

		Label l5 = new Label(container, SWT.NULL);
		phone = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(phone);
		l5.setText("Phone number");
		phone.setText(app.getApplication().getApplicationProvider().getPhone());			
		phone.setLayoutData(gd);

		Label l6 = new Label(container, SWT.NULL);
		address = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(address);
		l6.setText("Street address");
		address.setText(app.getApplication().getApplicationProvider().getStreetAddress());			
		address.setLayoutData(gd);

		Label l7 = new Label(container, SWT.NULL);
		web = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(web);
		l7.setText("Website");
		web.setText(app.getApplication().getApplicationProvider().getWebAddress().toString());			
		web.setLayoutData(gd);

		certificate.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				try{
					app.getApplication().getApplicationProvider().setCertificate(URI.create(certificate.getText()));
				}
				catch(Exception ex){
					ex.printStackTrace();
				}				
			}
		});
		person.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getApplication().getApplicationProvider().setContactPerson(person.getText());				
			}
		});	
		email.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getApplication().getApplicationProvider().setEmail(email.getText());				
			}
		});	
		organization.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getApplication().getApplicationProvider().setOrganizationName(organization.getText());				
			}
		});		
		phone.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getApplication().getApplicationProvider().setPhone(phone.getText());				
			}
		});
		address.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				app.getApplication().getApplicationProvider().setStreetAddress(address.getText());				
			}
		});
		web.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				try{
					app.getApplication().getApplicationProvider().setWebAddress(URI.create(web.getText()));
				}
				catch(Exception ex){
					ex.printStackTrace();
				}				
			}
		});

		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		final Label l8 = new Label(container, SWT.NULL);
		final Button b = new Button(container, SWT.PUSH);
		l8.setText("License(s) will be added in next page(s)");
		b.setText("Add license(s)");
		b.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				addLicense = !addLicense;
				if(!addLicense){
					b.setText("Add license(s)");
				}
				else{
					b.setText("Remove license adding");
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		b.setLayoutData(gd);
		Label l9 = new Label(container, SWT.NULL);
		l9.setText("(in: "+app.getApplication().getLicenses().size()+")");
	}

	@Override
	public void nextPressed(){

	}

	@Override
	public IWizardPage getNextPage() {

		if(addLicense)
			return super.getNextPage(); // SLA and licences

		return super.getNextPage().getNextPage(); // Capabilities
	}
}