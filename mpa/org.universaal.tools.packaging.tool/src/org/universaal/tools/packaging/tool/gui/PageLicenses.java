package org.universaal.tools.packaging.tool.gui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.api.Page;
import org.universaal.tools.packaging.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.App.LicenseSet;
import org.universaal.tools.packaging.tool.parts.App.SLA;
import org.universaal.tools.packaging.tool.parts.License;
import org.universaal.tools.packaging.tool.parts.LicenseCategory;
import org.universaal.tools.packaging.tool.util.Dialog;
import org.universaal.tools.packaging.tool.validators.AlphabeticV;
import org.universaal.tools.packaging.tool.validators.UriV;

public class PageLicenses extends PageImpl {

	private Text slaLink, slaName, licLink, licName;
	private Combo licCategory;

	private SLA sla;
	private License lic;
	private boolean addLicense = false, onlyLicense = false;

	private File f1, f2;

	private final String ERROR_MESSAGE = "Please verify the value";

	protected PageLicenses(String pageName) {
		super(pageName, "Add SLA and license(s) for your MPA - each artifact should be licensed under different license.");
	}

	protected PageLicenses(String pageName, boolean onlyLicense) {

		super(pageName, "Add SLA and license(s) for you MPA");
		this.onlyLicense = onlyLicense;
	}

	public void createControl(final Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);

		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		//gd.horizontalSpan = 2;

		List<LicenseSet> ls = app.getApplication().getLicenses();
		LicenseSet l = app.getApplication().new LicenseSet();		

		lic = new License();

		l.getLicenseList().add(lic);
		ls.add(l);

		if(!onlyLicense){
			sla = app.getApplication().new SLA();

			Label l1 = new Label(container, SWT.NULL);
			slaLink = new Text(container, SWT.BORDER | SWT.SINGLE);
			mandatory.add(slaLink);
			l1.setText("* SLA link");
			slaLink.setText(sla.getLink().toString());			
			slaLink.addVerifyListener(new UriV());
			slaLink.setLayoutData(gd);		

			Button b1 = new Button(container, SWT.PUSH);
			b1.setText("Browse");
			b1.addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e) {
					Dialog d = new Dialog();
					f1 = d.open(parent.getShell(), new String[]{"*.*"}, true, "Select a SLA file...");				
					try {
						slaLink.setText(f1.toURI().toURL()+"");
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
				}

				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});	

			Label l2 = new Label(container, SWT.NULL);
			slaName = new Text(container, SWT.BORDER | SWT.SINGLE);
			mandatory.add(slaName);
			l2.setText("* SLA name");
			slaName.setText(sla.getName());			
			slaName.addVerifyListener(new AlphabeticV());
			slaName.setLayoutData(gd);

			Label empty1 = new Label(container, SWT.NULL);
			empty1.setText("");

			slaLink.addKeyListener(new FullListener());
			slaName.addKeyListener(new QL() {

				@Override
				public void keyReleased(KeyEvent e) {
					sla.setName(slaName.getText());					
				}
			});
			l.setSla(sla);
		}

		Label l3 = new Label(container, SWT.NULL);
		licCategory = new Combo (container, SWT.READ_ONLY);
		LicenseCategory[] licCat = LicenseCategory.values();
		for(int i = 0; i < licCat.length; i++){
			licCategory.add(licCat[i].toString());
		}
		mandatory.add(licCategory);
		l3.setText("* License category");
		licCategory.setText(lic.getCategory().toString());
		licCategory.setLayoutData(gd);	

		Label empty2 = new Label(container, SWT.NULL);
		empty2.setText("");

		Label l4 = new Label(container, SWT.NULL);
		licLink = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(licLink);
		l4.setText("* License link");
		licLink.setText(ls.get(ls.size() - 1).getLicenseList().get(l.getLicenseList().size() - 1).getLink().toString());			
		licLink.addVerifyListener(new UriV());
		licLink.setLayoutData(gd);	

		Button b2 = new Button(container, SWT.PUSH);
		b2.setText("Browse");
		b2.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				Dialog d = new Dialog();
				f2 = d.open(parent.getShell(), new String[]{"*.*"}, true, "Select a license file...");				
				try {
					licLink.setText(f2.toURI().toURL()+"");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});	

		Label l5 = new Label(container, SWT.NULL);
		licName = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(licName);
		l5.setText("* License Name");
		licName.setText(ls.get(ls.size() - 1).getLicenseList().get(l.getLicenseList().size() - 1).getName());		
		licName.addVerifyListener(new AlphabeticV());
		licName.setLayoutData(gd);	

		Label empty3 = new Label(container, SWT.NULL);
		empty3.setText("");

		licCategory.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				lic.setCategory(LicenseCategory.valueOf(licCategory.getText()));				
			}
		});		
		licLink.addKeyListener(new FullListener());
		licName.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				lic.setName(licName.getText());

			}
		});

		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		final Button b = new Button(container, SWT.PUSH);
		b.setText("Add another license");
		Label t = new Label(container, SWT.NULL);
		t.setText("License "+app.getApplication().getLicenses().size());
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
		l9.setText("(of "+app.getApplication().getLicenses().size()+")");

		Label empty4 = new Label(container, SWT.NULL);
		empty4.setText("");
	}

	@Override
	public boolean nextPressed() {

		if(!onlyLicense && slaLink.getText() != null && !slaLink.getText().isEmpty()){
			URI link = null;
			try{
				link = URI.create(removeBlanks(slaLink.getText()));
			}
			catch(Exception ex){
				ex.printStackTrace();
				slaLink.setText(ERROR_MESSAGE);

				return false;
			}
			if(link != null)
				sla.setLink(link);
		}
		if(licLink.getText() != null && !licLink.getText().isEmpty()){
			URI link = null;
			try{
				link = URI.create(removeBlanks(licLink.getText()));
			}
			catch(Exception ex){
				ex.printStackTrace();
				licLink.setText(ERROR_MESSAGE);

				return false;
			}
			if(link != null)
				lic.setLink(URI.create(removeBlanks(licLink.getText())));
		}

		if(addLicense){
			PageLicenses pl = new PageLicenses(Page.PAGE_LICENSE+PageImpl.otherLicenses++, true);
			pl.setMPA(multipartApplication);
			pl.setPageComplete(false);
			addPageCustom(this, pl);
		}

		return true;
	}
}