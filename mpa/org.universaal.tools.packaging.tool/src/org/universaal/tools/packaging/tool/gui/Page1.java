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

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.universaal.tools.packaging.tool.impl.PageImpl;

import org.universaal.tools.packaging.tool.parts.Capability;
import org.universaal.tools.packaging.tool.util.Dialog;
import org.universaal.tools.packaging.tool.util.XSDParser;
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

	private TextExt name, id, description, tags, version_major, version_minor, version_micro, version_build, app_profile, menuName, serviceUri, iconFile;
	private Combo customIcon;
	private File sourcePNG;
	private Button b1, b2;
	private String iconFormat = "png", formatName;
	
	protected Page1(String pageName) {
		super(pageName, "Specify details of the Application you are creating");
	}

	public void createControl(final Composite parent) { 

		XSDParser XSDtooltip = XSDParser.get(XSD);
		
		container = new Composite(parent, SWT.NULL);
		setControl(container);

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		gd = new GridData(GridData.FILL_HORIZONTAL);
		GridData gd2 = new GridData(GridData.FILL, GridData.CENTER, true, false);		
		gd2.horizontalSpan = 2;

		Label label1 = new Label(container, SWT.NULL);
		name = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(name);
		label1.setText("* Application name");
		name.setText(app.getApplication().getName());			
		name.addVerifyListener(new AlphabeticV());
		name.setLayoutData(gd);	
		name.addTooltip(XSDtooltip.find("app.name"));
				
		Label label2 = new Label(container, SWT.NULL);
		id = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(id);
		label2.setText("* Application ID");
		id.setText(app.getApplication().getAppID());
		id.addVerifyListener(new AlphabeticV());
		id.setLayoutData(gd);
		id.addTooltip(XSDtooltip.find("app.appId"));
		
		Label label3 = new Label(container, SWT.NULL);
		description = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(description);
		label3.setText("* Description");
		description.setText(app.getApplication().getDescription());
		description.addVerifyListener(new AlphabeticV());
		description.setLayoutData(gd);		
		description.addTooltip(XSDtooltip.find("app.description"));
		
		Label label4 = new Label(container, SWT.NULL);
		tags = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		label4.setText("Insert tags (if any) separated by comma");
		tags.setText(app.getApplication().getTags());
		tags.addVerifyListener(new AlphabeticV());
		tags.setLayoutData(gd);
		tags.addTooltip(XSDtooltip.find("app.tags"));
		
		Label label5 = new Label(container, SWT.NULL);
		version_major = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(version_major);
		label5.setText("* Major version");
		version_major.setText(app.getApplication().getVersion().getMajor());
		version_major.addVerifyListener(new IntegerV());
		version_major.setLayoutData(gd);

		Label label6 = new Label(container, SWT.NULL);
		version_minor = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(version_minor);
		label6.setText("* Minor version");
		version_minor.setText(app.getApplication().getVersion().getMinor());
		version_minor.addVerifyListener(new IntegerV());
		version_minor.setLayoutData(gd);

		Label label7 = new Label(container, SWT.NULL);
		version_micro = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(version_micro);
		label7.setText("* Micro version");
		version_micro.setText(app.getApplication().getVersion().getMicro());
		version_micro.addVerifyListener(new IntegerV());
		version_micro.setLayoutData(gd);

		Label label8 = new Label(container, SWT.NULL);
		version_build = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(version_build);
		label8.setText("Build");
		version_build.setText(app.getApplication().getVersion().getBuild());
		version_build.addVerifyListener(new AlphabeticV());
		version_build.setLayoutData(gd);
		version_build.addTooltip(XSDtooltip.find("versionType.build"));
		
		Label label9 = new Label(container, SWT.NULL);
		app_profile = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		label9.setText("Application profile (if you are unsure do not modify!)");
		app_profile.setText(app.getApplication().getApplicationProfile());
		app_profile.addVerifyListener(new AlphabeticV());
		app_profile.setLayoutData(gd);
		app_profile.addTooltip(XSDtooltip.find("app.applicationProfile"));
		
		Label label10 = new Label(container, SWT.NULL);
		label10.setText("\nMenu Entry\n");
		label10.setLayoutData(gd2);
		
		FontData[] fD = label10.getFont().getFontData();
		fD[0].setStyle(SWT.BOLD);
		label10.setFont(new Font(container.getDisplay(), fD[0]));	
		
		Label label11 = new Label(container, SWT.NULL);
		label11.setText("Menu name");
				
		menuName = new TextExt(container, SWT.BORDER | SWT.SINGLE);
		menuName.setText(app.getApplication().getMenuEntry().getMenuName());
		menuName.addVerifyListener(new AlphabeticV());
		menuName.setLayoutData(gd);
		menuName.addTooltip(XSDtooltip.find("menuEntry.menuName"));
		
		Label label12 = new Label(container, SWT.NULL);
		label12.setText("* Service URI");
		
		serviceUri = new TextExt(container, SWT.BORDER | SWT.SINGLE );
		serviceUri.setText(app.getApplication().getMenuEntry().getServiceUri().toASCIIString());
		serviceUri.setLayoutData(gd);
		serviceUri.addVerifyListener(new UriV());
		serviceUri.addTooltip(XSDtooltip.find("menuEntry.serviceUri"));
		
		Label label13 = new Label(container, SWT.NULL);
		label13.setText("Menu Entry Icon (PNG 512x512 px A/R 1:1)");
		
		iconFile = new TextExt(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		iconFile.setLayoutData(gd);
		iconFile.addVerifyListener(new FileV());
		iconFile.addTooltip(XSDtooltip.find("icon.path"));
		
		Label label133 = new Label(container, SWT.NULL);
		label133.setText("");
		
		b1 = new Button(container, SWT.PUSH);
		b1.setText("Browse from files");
		b1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				
				Dialog d = new Dialog();
				sourcePNG = d.open(parent.getShell(), new String[]{"*."+iconFormat}, false, "Select a PNG Icon");
				if ( sourcePNG ==  null ) {
					return;
				}
				
				ImageInputStream iis = null;
				
				try {
					iis = ImageIO.createImageInputStream(sourcePNG);
					Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
				
					while(readers.hasNext()){
						ImageReader reader = readers.next();
						try {
							formatName = reader.getFormatName();
							/* DEBUG
							System.out.printf("formatName: %s%n", formatName);
							*/
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				
					BufferedImage image = null;
				
					image = ImageIO.read(sourcePNG);
					if(image != null && formatName.equalsIgnoreCase(iconFormat)){
						
						double width = image.getWidth();
						double height = image.getHeight();
						double aspect_ratio = width/height;
						
						/* DEBUG
						System.out.println("Height : "+ height);
						System.out.println("Width : "+ width);
						System.out.println("A/R : "+ aspect_ratio);
						*/
						
						if (width != 512 || height != 512){
							app.getApplication().getMenuEntry().setIconScale(true);
							if (aspect_ratio == 1.0)
								MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Invalid size", "The selected image will be scaled due to invalid pixel size.\n\nThe optimal size is 512x512 pixels (A/R 1:1)");
							else
								MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Invalid size and aspect ratio", "The selected image will be scaled and stretched due to invalid pixel size and aspect ratio.\n\nThe optimal size is 512x512 pixels (A/R 1:1)");
						}
						
						app.getApplication().getMenuEntry().setIsCustomIcon(false);
						customIcon.deselectAll();
						iconFile.setText(sourcePNG.getAbsolutePath());
						
					} else MessageDialog.openError(null, "Invalid format", "The selected file is not a valid PNG file");
				
					image = null;
				} catch (IOException e2) {
					e2.printStackTrace();
				}

			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});	
		
	
		Label void1 = new Label(container, SWT.NULL);
		void1.setText("");
		
		Label b2l = new Label(container, SWT.NULL);
		b2l.setText("or select from custom icons:");

		Label void2 = new Label(container, SWT.NULL);
		void2.setText("");
		
		customIcon = new Combo(container, SWT.READ_ONLY);
		
		InputStream is = getClass().getResourceAsStream("/lib/ui.handler.gui.swing-2.0.0-sources.jar");
        
        try {
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry ze;
            
            while ((ze = zis.getNextEntry()) != null) {
            	if (ze.getName().contains("icons") && ze.getName().contains(".png")) {
	                customIcon.add(ze.getName());
            	}
            }
            is.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
        customIcon.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				
				app.getApplication().getMenuEntry().setIsCustomIcon(true);
				if(customIcon.getText().trim().length() > 0) iconFile.setText("");
				setPageComplete(validate());
			}
		});
        		
		/*
		InputStream imgs = getClass().getResourceAsStream("/icons/app/Health.png");
        System.out.println("-- img stream:"+imgs.toString());
        BufferedImage image = null;
		try {
			image = ImageIO.read(imgs);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        if(image!=null){
        	
        	double width = image.getWidth();
			double height = image.getHeight();
			double aspect_ratio = width/height;
			System.out.println("Height : "+ height);
			System.out.println("Width : "+ width);
			System.out.println("A/R : "+ aspect_ratio);
        }
        
        Image simpleImg = new Image(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getDisplay(), imgs); 
        
        /*
        
        b2 = new Button(container, SWT.PUSH);
		b2.setText("Browse from custom icons");
		b2.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
		
				InputStream is = getClass().getResourceAsStream("/lib/ui.handler.gui.swing-2.0.0-sources.jar");
		        
		        try {
		            ZipInputStream zis = new ZipInputStream(is);
		            ZipEntry ze;

		            boolean imgLoaded = false;
		            while ((ze = zis.getNextEntry()) != null) {
		            	if (ze.getName().contains("icons") && ze.getName().contains(".png")) {
			                System.out.print("File : " + ze.getName());
			                
			                String path = "/"+ze.getName();
			                URL url = getClass().getResource(path);
			                System.out.println(" - url: "+url);
			                InputStream imgs = getClass().getResourceAsStream(path);
			                System.out.println("-- img stream:"+imgs.toString());
			                BufferedImage image = ImageIO.read(imgs);
			                Image simpleImg = new Image(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getDisplay(), imgs); 
			                if(image!=null){
			                	double width = image.getWidth();
								double height = image.getHeight();
								double aspect_ratio = width/height;
								System.out.println("Height : "+ height);
								System.out.println("Width : "+ width);
								System.out.println("A/R : "+ aspect_ratio);
			                }
			            }
		            }
		            is.close();
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }
		    }
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});	
		*/
		    
		
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
			}
		});	
		serviceUri.addKeyListener(new FullListener());
			
		loadDefaultValues();
		checkMenuEntry();
		
	}
	
	private void checkMenuEntry(){
		if(menuName.getText().trim().length() == 0){
			
			iconFile.setText("");
			iconFile.setEnabled(false);
			
			b1.setEnabled(false);
			customIcon.deselectAll();
			customIcon.setEnabled(false);
			
			if(mandatory.contains(serviceUri)) mandatory.remove(serviceUri);
			serviceUri.setText("");
			serviceUri.setEnabled(false);
					
		} else {
			
			iconFile.setEnabled(true);

			b1.setEnabled(true);
			customIcon.setEnabled(true);
			
			serviceUri.setEnabled(true);
			if(!mandatory.contains(serviceUri)) mandatory.add(serviceUri);
			
			
		}
		
		setPageComplete(validate());		
		
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
				else if(customIcon.getText().trim().length()>0){
					app.getApplication().getMenuEntry().setIconPath(new URI(customIcon.getText().trim().toString().replaceAll("/", ".")));
				}
					
					
			}	
			
			serializeMPA();
			
		} catch(Exception ex){
			ex.printStackTrace();
		}

		return true;
	}

}