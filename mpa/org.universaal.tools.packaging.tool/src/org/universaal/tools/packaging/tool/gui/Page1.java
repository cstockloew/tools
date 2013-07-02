/*
        Copyright 2007-2014 CNR-ISTI, http://isti.cnr.it
        Institute of Information Science and Technologies
        of the Italian National Research Council

        See the NOTICE file distributed with this work for additional
        information regarding copyright ownership

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
 */
package org.universaal.tools.packaging.tool.gui;

import java.io.File;
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
import org.universaal.tools.packaging.tool.impl.PageImpl;
import org.universaal.tools.packaging.tool.impl.PageImpl.QL;
import org.universaal.tools.packaging.tool.util.Dialog;
import org.universaal.tools.packaging.tool.validators.AlphabeticV;
import org.universaal.tools.packaging.tool.validators.FileV;
import org.universaal.tools.packaging.tool.validators.IntegerV;
import org.universaal.tools.packaging.tool.validators.UriV;

/**
 * Second page of the wizard
 * 
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano Lenzi</a>
 * @version $LastChangedRevision$ ( $LastChangedDate$ )
 */
public class Page1 extends PageImpl {

	private Text name, id, description, tags, version_major, version_minor, version_micro, version_build, app_profile, menuName, serviceUri, iconFile;
	private File sourcePNG;
	private Button b1;
	
	protected Page1(String pageName) {
		super(pageName, "Specify details of the Application you are creating");
	}

	public void createControl(final Composite parent) { 

		container = new Composite(parent, SWT.NULL);
		setControl(container);

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		Label label1 = new Label(container, SWT.NULL);
		name = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(name);
		label1.setText("* Application name");
		name.setText(app.getApplication().getName());			
		name.addVerifyListener(new AlphabeticV());
		name.setLayoutData(gd);				

		Label label2 = new Label(container, SWT.NULL);
		id = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(id);
		label2.setText("* Application ID");
		id.setText(app.getApplication().getAppID());
		id.addVerifyListener(new AlphabeticV());
		id.setLayoutData(gd);

		Label label3 = new Label(container, SWT.NULL);
		description = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(description);
		label3.setText("* Description");
		description.setText(app.getApplication().getDescription());
		description.addVerifyListener(new AlphabeticV());
		description.setLayoutData(gd);		

		Label label4 = new Label(container, SWT.NULL);
		tags = new Text(container, SWT.BORDER | SWT.SINGLE);
		label4.setText("Insert tags (if any) separated by comma");
		tags.setText(app.getApplication().getTags());
		tags.addVerifyListener(new AlphabeticV());
		tags.setLayoutData(gd);

		Label label5 = new Label(container, SWT.NULL);
		version_major = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(version_major);
		label5.setText("* Major version");
		version_major.setText(app.getApplication().getVersion().getMajor());
		version_major.addVerifyListener(new IntegerV());
		version_major.setLayoutData(gd);

		Label label6 = new Label(container, SWT.NULL);
		version_minor = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(version_minor);
		label6.setText("* Minor version");
		version_minor.setText(app.getApplication().getVersion().getMinor());
		version_minor.addVerifyListener(new IntegerV());
		version_minor.setLayoutData(gd);

		Label label7 = new Label(container, SWT.NULL);
		version_micro = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(version_micro);
		label7.setText("* Micro version");
		version_micro.setText(app.getApplication().getVersion().getMicro());
		version_micro.addVerifyListener(new IntegerV());
		version_micro.setLayoutData(gd);

		Label label8 = new Label(container, SWT.NULL);
		version_build = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(version_build);
		label8.setText("Build");
		version_build.setText(app.getApplication().getVersion().getBuild());
		version_build.addVerifyListener(new AlphabeticV());
		version_build.setLayoutData(gd);

		Label label9 = new Label(container, SWT.NULL);
		app_profile = new Text(container, SWT.BORDER | SWT.SINGLE);
		label9.setText("Application profile (if you are unsure do not modify!)");
		app_profile.setText(app.getApplication().getApplicationProfile());
		app_profile.addVerifyListener(new AlphabeticV());
		app_profile.setLayoutData(gd);

		Label label10 = new Label(container, SWT.NULL);
		label10.setText("\nMenu Entry\n");
		
		Label label100 = new Label(container, SWT.NULL);
		label100.setText("");
		
		FontData[] fD = label10.getFont().getFontData();
		fD[0].setStyle(SWT.BOLD);
		label10.setFont(new Font(container.getDisplay(), fD[0]));	
		
		Label label11 = new Label(container, SWT.NULL);
		label11.setText("Menu name");
				
		menuName = new Text(container, SWT.BORDER | SWT.SINGLE);
		menuName.setText(app.getApplication().getMenuEntry().getMenuName());
		menuName.addVerifyListener(new AlphabeticV());
		menuName.setLayoutData(gd);
		
		Label label12 = new Label(container, SWT.NULL);
		label12.setText("* Service URI");
		
		serviceUri = new Text(container, SWT.BORDER | SWT.SINGLE );
		serviceUri.setText(app.getApplication().getMenuEntry().getServiceUri().toASCIIString());
		serviceUri.setLayoutData(gd);
		serviceUri.addVerifyListener(new UriV());
		
		Label label13 = new Label(container, SWT.NULL);
		label13.setText("Menu Entry Icon (PNG)");
		
		iconFile = new Text(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		iconFile.setLayoutData(gd);
		iconFile.addVerifyListener(new FileV());
		
		Label label133 = new Label(container, SWT.NULL);
		label133.setText("");
		
		b1 = new Button(container, SWT.PUSH);
		b1.setText("Browse");
		b1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				Dialog d = new Dialog();
				sourcePNG = d.open(parent.getShell(), new String[]{"*.png"}, false, "PNG Icon");			

				if(!sourcePNG.getAbsolutePath().endsWith(".png"))
					sourcePNG = new File(sourcePNG+".png");

				iconFile.setText(sourcePNG.getAbsolutePath());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});	
		
		checkMenuEntry();
		
		name.addKeyListener(new FullListener());
		id.addKeyListener(new FullListener());
		description.addKeyListener(new FullListener());
		version_major.addKeyListener(new FullListener());
		version_minor.addKeyListener(new FullListener());
		version_micro.addKeyListener(new FullListener());
		version_build.addKeyListener(new FullListener());
		app_profile.addKeyListener(new FullListener());
		
		menuName.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				checkMenuEntry();
				setPageComplete(validate());
			}
		});	
		serviceUri.addKeyListener(new FullListener());
				
		loadDefaultValues();
		
	}

	private void checkMenuEntry(){
		if(menuName.getText().trim().length() == 0){
			serviceUri.setText("");
			serviceUri.setEnabled(false);
			mandatory.remove(serviceUri);
			
			iconFile.setText("");
			iconFile.setEnabled(false);
			
			b1.setEnabled(false);
		} else {
			serviceUri.setEnabled(true);
			mandatory.add(serviceUri);
			
			iconFile.setEnabled(true);

			b1.setEnabled(true);
		}
	}
	
	private void loadDefaultValues() {
		if ( app.getApplication() != null ) {
		    name.setText( app.getApplication().getName() );
		    id.setText( app.getApplication().getAppID() );
		    description.setText( app.getApplication().getDescription() );
		    version_minor.setText( app.getApplication().getVersion().getMajor() );
		    version_minor.setText( app.getApplication().getVersion().getMinor() );
		    version_micro.setText( app.getApplication().getVersion().getMicro() );
		    app_profile.setText( app.getApplication().getApplicationProfile() );
		}	    
	}

	@Override
	public boolean nextPressed() {		

		try{
			app.getApplication().setName(name.getText());	
			app.getApplication().setAppID(id.getText());		
			app.getApplication().setDescription(description.getText());		
			app.getApplication().getVersion().setMajor(version_major.getText());	
			app.getApplication().getVersion().setMinor(version_minor.getText());	
			app.getApplication().getVersion().setMicro(version_micro.getText());		
			app.getApplication().getVersion().setBuild(version_build.getText());	
			app.getApplication().setApplicationProfile(app_profile.getText());
			if(menuName.getText().trim().length() > 0){
				app.getApplication().getMenuEntry().setMenuName(menuName.getText().trim());
				app.getApplication().getMenuEntry().setServiceUri(URI.create(serviceUri.getText()));
				if(iconFile.getText().trim().length()>0)
					app.getApplication().getMenuEntry().setIconFile(sourcePNG);
			}	
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return true;
	}
}