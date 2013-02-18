package org.universaal.tools.packaging.tool.gui;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
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
import org.universaal.tools.packaging.tool.parts.App.LicenseSet;
import org.universaal.tools.packaging.tool.parts.App.SLA;
import org.universaal.tools.packaging.tool.parts.License;
import org.universaal.tools.packaging.tool.parts.LicenseCategory;

public class PageLicenses extends PageImpl {

	private Text slaLink, slaName, licLink, licName;
	private Combo licCategory;

	private SLA sla;
	private License lic;
	private boolean addLicense = false, pageAdded = false, onlyLicense = false;
	private int partNumber;

	protected PageLicenses(String pageName, int pn) {
		super(pageName, "Add SLA and license(s) for you MPA", pn);
		this.partNumber = pn;
	}

	protected PageLicenses(String pageName, boolean onlyLicense, int pn) {

		super(pageName, "Add SLA and license(s) for you MPA", pn);
		this.onlyLicense = onlyLicense;
		this.partNumber = pn;
	}

	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);
		mandatory.clear();

		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 2;

		List<LicenseSet> ls = multipartApplication.getApplications().get(partNumber).getApplication().getLicenses();
		LicenseSet l = multipartApplication.getApplications().get(partNumber).getApplication().new LicenseSet();		

		lic = new License();

		l.getLicenseList().add(lic);
		ls.add(l);

		if(!onlyLicense){
			sla = multipartApplication.getApplications().get(partNumber).getApplication().new SLA();

			Label l1 = new Label(container, SWT.NULL);
			slaLink = new Text(container, SWT.BORDER | SWT.SINGLE);
			mandatory.add(slaLink);
			l1.setText("SLA link");
			slaLink.setText(sla.getLink().toString());			
			slaLink.setLayoutData(gd);		

			Label l2 = new Label(container, SWT.NULL);
			slaName = new Text(container, SWT.BORDER | SWT.SINGLE);
			mandatory.add(slaName);
			l2.setText("SLA name");
			slaName.setText(sla.getName());			
			slaName.setLayoutData(gd);	

			slaLink.addKeyListener(new KeyListener() {

				public void keyReleased(KeyEvent e) {
					setPageComplete(validate());
				}

				public void keyPressed(KeyEvent e) {
					try {
						sla.setLink(new URI(slaLink.getText()));
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			});
			slaName.addKeyListener(new KeyListener() {

				public void keyReleased(KeyEvent e) {
					setPageComplete(validate());
				}

				public void keyPressed(KeyEvent e) {
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
		l3.setText("License category");
		licCategory.setText(lic.getCategory().toString());
		licCategory.setLayoutData(gd);	

		Label l4 = new Label(container, SWT.NULL);
		licLink = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(licLink);
		l4.setText("License link");
		licLink.setText(ls.get(ls.size() - 1).getLicenseList().get(l.getLicenseList().size() - 1).getLink().toString());			
		licLink.setLayoutData(gd);	

		Label l5 = new Label(container, SWT.NULL);
		licName = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(licName);
		l5.setText("License Name");
		licName.setText(ls.get(ls.size() - 1).getLicenseList().get(l.getLicenseList().size() - 1).getName());			
		licName.setLayoutData(gd);		

		licCategory.addKeyListener(new KeyListener() {

			public void keyReleased(KeyEvent e) {
				setPageComplete(validate());
			}

			public void keyPressed(KeyEvent e) {
				lic.setCategory(LicenseCategory.valueOf(licCategory.getText()));
			}
		});
		licLink.addKeyListener(new KeyListener() {

			public void keyReleased(KeyEvent e) {
				setPageComplete(validate());
			}

			public void keyPressed(KeyEvent e) {
				try {
					lic.setLink(new URI(licLink.getText()));
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
		licName.addKeyListener(new KeyListener() {

			public void keyReleased(KeyEvent e) {
				setPageComplete(validate());
			}

			public void keyPressed(KeyEvent e) {
				lic.setName(licName.getText());
			}
		});

		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		final Button b = new Button(container, SWT.PUSH);
		b.setText("Add another license");
		Label t = new Label(container, SWT.NULL);
		t.setText("License "+multipartApplication.getApplications().get(partNumber).getApplication().getLicenses().size());
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
		l9.setText("(of "+multipartApplication.getApplications().get(partNumber).getApplication().getLicenses().size()+")");
	}

	@Override
	public IWizardPage getNextPage() {

		if(addLicense){
			addPageCustom(pageAdded, new PageLicenses(Page.PAGE_LICENSE+PageImpl.otherLicenses++, true, partNumber));
			pageAdded = true;
		}

		return super.getNextPage();
	}
}