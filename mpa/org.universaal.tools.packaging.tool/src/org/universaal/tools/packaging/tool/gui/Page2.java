package org.universaal.tools.packaging.tool.gui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.OtherChannel;
import org.universaal.tools.packaging.tool.validators.AlphabeticV;
import org.universaal.tools.packaging.tool.validators.PhoneV;
import org.universaal.tools.packaging.tool.validators.UriV;

public class Page2 extends PageImpl {

	private Text certificate, person, email, organization, phone, address, web, othChNm1, othChnDtl1, othChNm2, othChnDtl2;

	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	protected Page2(String pageName) {
		super(pageName, "Specify contact details");
	}

	public void createControl(final Composite parent) {

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
		certificate.addVerifyListener(new UriV());
		certificate.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));	

		Button b1 = new Button(container, SWT.PUSH);
		b1.setText("Browse");
		b1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				org.universaal.tools.packaging.tool.util.Dialog d = new org.universaal.tools.packaging.tool.util.Dialog(); 
				File sc = d.open(parent.getShell(), new String[]{"*.pem", "*.cer", "*.crt", "*.der", "*.p7b", "*.p7c", "*.p12", "*.pfx"}, true, "Select a security certificate...");
				/* WIKIPEDIA
			    .pem – (Privacy Enhanced Mail) Base64 encoded DER certificate, enclosed between "-----BEGIN CERTIFICATE-----" and "-----END CERTIFICATE-----"
			    .cer, .crt, .der – usually in binary DER form, but Base64-encoded certificates are common too (see .pem above)
			    .p7b, .p7c – PKCS#7 SignedData structure without data, just certificate(s) or CRL(s)
			    .p12 – PKCS#12, may contain certificate(s) (public) and private keys (password protected)
			    .pfx – PFX, predecessor of PKCS#12 (usually contains data in PKCS#12 format, e.g., with PFX files generated in IIS)
				 */
				try {
					certificate.setText(sc.toURI().toURL()+"");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});					

		Label l2 = new Label(container, SWT.NULL);
		person = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(person);
		l2.setText("Contact person");
		person.setText(app.getApplication().getApplicationProvider().getContactPerson());			
		person.addVerifyListener(new AlphabeticV());
		person.setLayoutData(gd);				

		Label l3 = new Label(container, SWT.NULL);
		email = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(email);
		l3.setText("* Contact e-mail");
		email.setText(app.getApplication().getApplicationProvider().getContactPerson());	
		//email.addVerifyListener(new MailV()); //TODO not working
		email.setLayoutData(gd);	

		Label l4 = new Label(container, SWT.NULL);
		organization = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(organization);
		l4.setText("Organization name");
		organization.setText(app.getApplication().getApplicationProvider().getOrganizationName());			
		organization.addVerifyListener(new AlphabeticV());
		organization.setLayoutData(gd);

		Label l5 = new Label(container, SWT.NULL);
		phone = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(phone);
		l5.setText("Phone number");
		phone.setText(app.getApplication().getApplicationProvider().getPhone());			
		organization.addVerifyListener(new PhoneV());
		phone.setLayoutData(gd);

		Label l6 = new Label(container, SWT.NULL);
		address = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(address);
		l6.setText("Street address");
		address.setText(app.getApplication().getApplicationProvider().getStreetAddress());		
		address.addVerifyListener(new AlphabeticV());
		address.setLayoutData(gd);

		Label l7 = new Label(container, SWT.NULL);
		web = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(web);
		l7.setText("Website");
		web.setText(app.getApplication().getApplicationProvider().getWebAddress().toString());			
		web.addVerifyListener(new UriV());
		web.setLayoutData(gd);

		Label l8 = new Label(container, SWT.NULL);
		othChNm1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(web);
		l8.setText("Other contact #1 - Identifier (tel., e-mail, ...)");
		othChNm1.setText("");			
		othChNm1.addVerifyListener(new AlphabeticV());
		othChNm1.setLayoutData(gd);

		Label l9 = new Label(container, SWT.NULL);
		othChnDtl1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(web);
		l9.setText("Other contact #1 - Details (tel. number, e-mail address, ...)");
		othChnDtl1.setText("");			
		othChnDtl1.addVerifyListener(new AlphabeticV());
		othChnDtl1.setLayoutData(gd);

		Label l10 = new Label(container, SWT.NULL);
		othChNm2 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(web);
		l10.setText("Other contact #2 - Identifier (fax, other...)");
		othChNm2.setText("");			
		othChNm2.addVerifyListener(new AlphabeticV());
		othChNm2.setLayoutData(gd);

		Label l11 = new Label(container, SWT.NULL);
		othChnDtl2 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(web);
		l11.setText("Other contact #2 - Details (fax number, other...)");
		othChnDtl2.setText("");			
		othChnDtl2.addVerifyListener(new AlphabeticV());
		othChnDtl2.setLayoutData(gd);

		certificate.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				//				try{
				//					//app.getApplication().getApplicationProvider().setCertificate(URI.create(removeBlanks(certificate.getText())));
				//				}
				//				catch(Exception ex){
				//					ex.printStackTrace();
				//				}				
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
				//				try{
				//					// app.getApplication().getApplicationProvider().setWebAddress(URI.create(removeBlanks(web.getText())));
				//				}
				//				catch(Exception ex){
				//					ex.printStackTrace();
				//				}				
			}
		});
		othChNm1.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				otherContacts(1);
			}
		});
		othChnDtl1.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				otherContacts(2);
			}
		});
		othChNm2.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				otherContacts(3);
			}
		});
		othChnDtl2.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				otherContacts(4);				
			}
		});	
	}

	private void otherContacts(int choose){

		switch (choose) {
		case 1:
			//1.1
			if(othChNm1.getText().isEmpty() && othChnDtl1.getText().isEmpty()){
				mandatory.remove(othChNm1);
				mandatory.remove(othChnDtl1);
			}
			else{
				if(mandatory(othChNm1))
					mandatory.add(othChNm1);
				if(mandatory(othChnDtl1))
					mandatory.add(othChnDtl1);
			}
			break;

		case 2:
			//1.2
			if(othChnDtl1.getText().isEmpty() && othChNm1.getText().isEmpty()){
				mandatory.remove(othChnDtl1);
				mandatory.remove(othChNm1);					
			}
			else{
				if(mandatory(othChNm1))
					mandatory.add(othChNm1);
				if(mandatory(othChnDtl1))
					mandatory.add(othChnDtl1);
			}
			break;

		case 3:
			//2.1
			if(othChNm2.getText().isEmpty() && othChnDtl2.getText().isEmpty()){
				mandatory.remove(othChNm2);
				mandatory.remove(othChnDtl2);
			}
			else{
				if(mandatory(othChNm2))
					mandatory.add(othChNm2);
				if(mandatory(othChnDtl2))
					mandatory.add(othChnDtl2);
			}
			break;

		case 4: 
			//2.2
			if(othChnDtl2.getText().isEmpty() && othChNm2.getText().isEmpty()){
				mandatory.remove(othChnDtl2);
				mandatory.remove(othChNm2);
			}
			else{
				if(mandatory(othChNm2))
					mandatory.add(othChNm2);
				if(mandatory(othChnDtl2))
					mandatory.add(othChnDtl2);
			}
			break;

		default:
			break;
		}

		setPageComplete(validate());
	}

	private boolean mandatory(Object o){
		for(int i = 0; i < mandatory.size(); i++)
			if(mandatory.get(i).equals(o))
				return false;

		return true;
	}

	@Override
	public boolean nextPressed(){

		try{
			if(!email.getText().matches(EMAIL_PATTERN)){

				FontData[] fD = email.getFont().getFontData();
				fD[0].setStyle(SWT.COLOR_DARK_RED);
				fD[0].setStyle(SWT.BOLD);
				email.setFont(new Font(container.getDisplay(), fD[0]));		

				return false;
			}			

			if(!othChNm1.getText().isEmpty() && !othChnDtl1.getText().isEmpty())
				app.getApplication().getApplicationProvider().getOtherChannels().add(new OtherChannel(othChNm1.getText(), othChnDtl1.getText()));

			if(!othChNm2.getText().isEmpty() && !othChnDtl2.getText().isEmpty())
				app.getApplication().getApplicationProvider().getOtherChannels().add(new OtherChannel(othChNm2.getText(), othChnDtl2.getText()));

			if(!certificate.getText().isEmpty()){
				app.getApplication().getApplicationProvider().setCertificate(URI.create(removeBlanks(certificate.getText())));
			}

			if(web.getText() != null && !web.getText().isEmpty())
				app.getApplication().getApplicationProvider().setWebAddress(URI.create(removeBlanks(web.getText())));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return true;
	}
}