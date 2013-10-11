package org.universaal.tools.packaging.tool.gui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.tool.api.Page;
import org.universaal.tools.packaging.tool.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.License;
import org.universaal.tools.packaging.tool.parts.LicenseCategory;
import org.universaal.tools.packaging.tool.parts.LicenseSet;
import org.universaal.tools.packaging.tool.parts.SLA;
import org.universaal.tools.packaging.tool.util.Dialog;
import org.universaal.tools.packaging.tool.util.XSDParser;
import org.universaal.tools.packaging.tool.validators.AlphabeticV;
import org.universaal.tools.packaging.tool.validators.UriV;

public class PageLicenses extends PageImpl {

	private TextExt slaLink, slaName;
	private List<TextExt> licLink = new ArrayList<TextExt>(), licName = new ArrayList<TextExt>();
	private List<Combo> licCategory = new ArrayList<Combo>();
	private List<Button> buttons = new ArrayList<Button>();
	private ScrolledComposite sc1;
	private LicenseSet ls;
	private GridData gd2, gd3;
	private boolean addLicense = false/*, onlyLicense = false*/;

	private File slaFile, licFile;
	private final String ERROR_MESSAGE = "Unrecognized value!";
	
	protected PageLicenses(String pageName) {
		super(pageName, "Add SLA and license(s) for your Application - each artifact should be licensed under different license.");
	}

	protected PageLicenses(String pageName, boolean onlyLicense) {

		super(pageName, "Add SLA and license(s) for you Application");
		//this.onlyLicense = onlyLicense;
	}

	public void createControl(final Composite parent) {

		// XSDParser XSDtooltip = XSDParser.get(XSD);
		sc1 = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.BORDER);
	      
		container = new Composite(sc1, SWT.NONE);
		setControl(sc1);
		
		sc1.setExpandVertical(true);
		sc1.setExpandHorizontal(true);
		
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		//gd.horizontalSpan = 2;
		
		gd2 = new GridData(GridData.FILL, GridData.CENTER, true, false);		
		gd2.horizontalSpan = 2;
		
		gd3 = new GridData(GridData.FILL, GridData.CENTER, true, false);		
		gd3.horizontalSpan = 3;
		
		ls = app.getApplication().getLicenses();
		
		if(ls.getLicenseList().size() == 0){
			//System.out.println("Adding empty license");
			ls.getLicenseList().add(new License());
		}
		
		//if(!onlyLicense){
			Label l1 = new Label(container, SWT.NULL);
			slaLink = new TextExt(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
			//mandatory.add(slaLink);
			//l1.setText("* SLA link");
			l1.setText("SLA file");
			slaLink.setText(ls.getSla().getLink().toString());			
			slaLink.addVerifyListener(new UriV());
			slaLink.setLayoutData(gd);		
			//System.out.println(sla.getLink().toString());
			
			Button b1 = new Button(container, SWT.PUSH);
			b1.setText("Browse");
			b1.addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e) {
					Dialog d = new Dialog();
					slaFile = d.open(parent.getShell(), new String[]{"*.*"}, true, "Select a SLA file...");	
					try {
						slaLink.setText(slaFile.toURI().toURL()+"");
					} catch (Exception e1) {
						//e1.printStackTrace();
					}
				}

				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});	

			Label l2 = new Label(container, SWT.NULL);
			slaName = new TextExt(container, SWT.BORDER | SWT.SINGLE);
			//mandatory.add(slaName);
			//l2.setText("* SLA name");
			l2.setText("SLA name");
			slaName.setText(ls.getSla().getName());			
			slaName.addVerifyListener(new AlphabeticV());
			slaName.setLayoutData(gd);

			Label empty1 = new Label(container, SWT.NULL);
			empty1.setText("");

			slaLink.addKeyListener(new FullListener());
			
			Label shadow_sep_h2 = new Label(container, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
			shadow_sep_h2.setLayoutData(gd3);
		//}

		for(int i=0; i < ls.getLicenseList().size(); i++){
			Label catLabel = new Label(container, SWT.NULL);
			catLabel.setText("License category");
			
			try{
				licCategory.get(i); 
			} catch(Exception e){
				licCategory.add(i, new Combo (container, SWT.READ_ONLY));
				LicenseCategory[] licCat = LicenseCategory.values();
				for(int j = 0; j < licCat.length; j++){
					licCategory.get(i).add(licCat[j].toString());
				}
			}
			
			licCategory.get(i).setLayoutData(gd2);
			licCategory.get(i).setText(ls.getLicenseList().get(i).getCategory().toString());
			
			Label licLinkLabel = new Label(container, SWT.NULL);
			licLinkLabel.setText("License Link");
			
			try{
				licLink.get(i);
			} catch(Exception e) {
				licLink.add(i, new TextExt (container, SWT.BORDER | SWT.SINGLE));
				
			}
			licLink.get(i).setText(ls.getLicenseList().get(i).getLink().toASCIIString());
			licLink.get(i).addKeyListener(new QL() {

				@Override
				public void keyReleased(KeyEvent e) {
					TextExt tmp = (TextExt) e.widget;
					int key = licLink.indexOf(tmp);
					try{
						ls.getLicenseList().get(key).setLink(new URI(licLink.get(key).getText()));
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
					}
					
				}
			});
			licLink.get(i).setLayoutData(gd);
			
			try{
				buttons.get(i);
			} catch(Exception e) {
				buttons.add(i, new Button (container, SWT.PUSH));
			}
			buttons.get(i).setText("Browse");
			buttons.get(i).addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e) {
					Button tmp = (Button) e.widget;
					int key = buttons.indexOf(tmp);
					Dialog d = new Dialog();
					licFile = d.open(parent.getShell(), new String[]{"*.*"}, true, "Select a license file...");				
					if(licFile!=null)
					try {
						licLink.get(key).setText(licFile.toURI().toURL()+"");
						ls.getLicenseList().get(key).setLink(licFile.toURI());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
					}
				}

				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});	
			
			Label shadow_sep_3 = new Label(container, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
			shadow_sep_3.setLayoutData(gd3);
		}
		
		final Button b = new Button(container, SWT.PUSH);
		b.setText("Add another license");
		b.setLayoutData(gd);
		
		Label empty2 =new Label(container, SWT.NULL);
		empty2.setText("");
		empty2.setLayoutData(gd2);
		//		t.setText("License "+app.getApplication().getLicenses().size());
		b.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				if(!licLink.get(licLink.size()-1).getText().isEmpty()){
					ls.getLicenseList().add(new License());
					
					Label catLabel = new Label(container, SWT.NULL);
					catLabel.setText("License category");
					
					licCategory.add(new Combo (container, SWT.READ_ONLY));
					LicenseCategory[] licCat = LicenseCategory.values();
					for(int j = 0; j < licCat.length; j++){
						licCategory.get(licCategory.size()-1).add(licCat[j].toString());
					}
	
					
					licCategory.get(licCategory.size()-1).setLayoutData(gd2);
					licCategory.get(licCategory.size()-1).setText(ls.getLicenseList().get(licCategory.size()-1).getCategory().toString());
					
					
					Label licLinkLabel = new Label(container, SWT.NULL);
					licLinkLabel.setLayoutData(gd);
					licLinkLabel.setText("License Link");
					licLink.add(new TextExt (container, SWT.BORDER | SWT.SINGLE));
					licLink.get(licLink.size()-1).setLayoutData(gd);
					licLink.get(licLink.size()-1).addKeyListener(new QL() {

						@Override
						public void keyReleased(KeyEvent e) {
							TextExt tmp = (TextExt) e.widget;
							int key = licLink.indexOf(tmp);
							try{
								ls.getLicenseList().get(key).setLink(new URI(licLink.get(key).getText()));
							} catch (URISyntaxException e1) {
								// TODO Auto-generated catch block
							}
							
						}
					});
					buttons.add(new Button (container, SWT.PUSH));
					buttons.get(buttons.size()-1).setText("Browse");
					buttons.get(buttons.size()-1).addSelectionListener(new SelectionListener() {
	
						public void widgetSelected(SelectionEvent e) {
							Button tmp = (Button) e.widget;
							int key = buttons.indexOf(tmp);
							Dialog d = new Dialog();
							licFile = d.open(parent.getShell(), new String[]{"*.*"}, true, "Select a license file...");				
							try {
								licLink.get(key).setText(licFile.toURI().toURL()+"");
								ls.getLicenseList().get(key).setLink(licFile.toURI());
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								//e1.printStackTrace();
							}
						}
	
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});	
					
					Label shadow_sep = new Label(container, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
					shadow_sep.setLayoutData(gd3);
					
					container.layout(true);
					sc1.setContent(container);
					sc1.setMinSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		sc1.setContent(container);
		sc1.setMinSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		/*
		licCategory = new Combo (container, SWT.READ_ONLY);
		LicenseCategory[] licCat = LicenseCategory.values();
		for(int i = 0; i < licCat.length; i++){
			licCategory.add(licCat[i].toString());
		}
		//mandatory.add(licCategory);
		//l3.setText("* License category");
		l3.setText("License category");
		licCategory.setText(lic.getCategory().toString());
		licCategory.setLayoutData(gd);	

		Label empty2 = new Label(container, SWT.NULL);
		empty2.setText("");

		Label l4 = new Label(container, SWT.NULL);
		licLink = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(licLink);
		//l4.setText("* License link");
		l4.setText("License file");
		licLink.setText(ls.get(ls.size() - 1).getLicenseList().get(l.getLicenseList().size() - 1).getLink().toString());			
		licLink.addVerifyListener(new UriV());
		licLink.setLayoutData(gd);	

		Button b2 = new Button(container, SWT.PUSH);
		b2.setText("Browse");
		b2.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				Dialog d = new Dialog();
				licFile = d.open(parent.getShell(), new String[]{"*.*"}, true, "Select a license file...");				
				try {
					licLink.setText(licFile.toURI().toURL()+"");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});	

		Label l5 = new Label(container, SWT.NULL);
		licName = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(licName);
		//l5.setText("* License Name");
		l5.setText("License Name");
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
		b.setText("Add another license (in next page)");
		//		Label t = new Label(container, SWT.NULL);
		//		t.setText("License "+app.getApplication().getLicenses().size());
		b.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				addLicense = !addLicense;
				if(!addLicense){
					b.setText("Add another license (in next page)");
				}
				else{
					b.setText("Do not add a new license");
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		b.setLayoutData(gd);
		//		Label l9 = new Label(container, SWT.NULL);
		//		l9.setText("(of "+app.getApplication().getLicenses().size()+")");

		Label empty4 = new Label(container, SWT.NULL);
		empty4.setText("");
		Label empty5 = new Label(container, SWT.NULL);
		empty5.setText("");
		Label empty6 = new Label(container, SWT.NULL);
		empty6.setText("");
		 */
		setPageComplete(validate());
	}

	@Override
	public boolean nextPressed() {
		URI link = null;
		try{
			link = URI.create(removeBlanks(slaLink.getText()));
		}
		catch(Exception ex){
			//ex.printStackTrace();
			slaLink.setText(ERROR_MESSAGE);

			return false;
		}
		if(link != null){
			if(!slaName.getText().isEmpty())
				ls.getSla().setName(slaName.getText());
				
			ls.getSla().setLink(link);
		}
		return true;
	}
	/*
	@Override
	public void setVisible(boolean visible){
		sc1.setVisible(visible);
		super.setVisible(visible);
	}
	*/
}