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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.FileUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.tool.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.Application;
import org.universaal.tools.packaging.tool.parts.MPA;
import org.universaal.tools.packaging.tool.util.Configurator;
import org.universaal.tools.packaging.tool.util.Dialog;
import org.universaal.tools.packaging.tool.util.XSDParser;
import org.universaal.tools.packaging.tool.validators.FileV;

/**
 * 
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano Lenzi</a>
 * @version $LastChangedRevision$ ( $LastChangedDate$ )
 */
public class StartPage extends PageImpl {

	private File destination;
	private Text name;
	//private Combo mainPart;
	
	private GUI g = GUI.getInstance();
	private List<IProject> parts;

	protected StartPage(String pageName) {
		super(pageName, "This is the starting page for the universAAL Application Packager");
	}

	public void createControl(final Composite parent) { 

		container = new Composite(parent, SWT.NULL);
		setControl(container);

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		gd = new GridData(GridData.FILL_HORIZONTAL);
		
		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);		
		gd2.horizontalSpan = 2;
		
		parts = g.getParts();

		Label label1 = new Label(container, SWT.NULL);
		label1.setText("This wizard will guide you in creating a .UAPP file to upload your Application to the uStore.");
		label1.setLayoutData(gd2);
		
		Label label2 = new Label(container, SWT.NULL);
		label2.setText("Before starting it you should select all the parts you would like to include (CTRL-click on projects).");
		label2.setLayoutData(gd2);
		
		Label shadow_sep_h = new Label(container, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
		shadow_sep_h.setLayoutData(gd2);
		
		Label label4 = new Label(container, SWT.NULL);
		label4.setText("At now you have selected "+parts.size()+" projects to be included in this Application:");
		label4.setLayoutData(gd2);
		
		
		for(int i = 0; i < parts.size(); i++){				
			Label part = new Label(container, SWT.NULL);
			part.setText("\tPart "+(i+1)+": "+parts.get(i).getName()); 
			part.setLayoutData(gd2);
			
			FontData[] fD = part.getFont().getFontData();
			fD[0].setStyle(SWT.BOLD);
			part.setFont(new Font(container.getDisplay(), fD[0]));		
		}

		/*
		if(parts.size() == 0){
			Label label5 = new Label(container, SWT.NULL);
			label5.setText("No parts have been selected, please select desidered parts and restart the procedure.");

			FontData[] fD = label5.getFont().getFontData();
			fD[0].setStyle(SWT.BOLD);
			label5.setFont(new Font(container.getDisplay(), fD[0]));	

			Label label50 = new Label(container, SWT.NULL);
			label50.setText("");

			setPageComplete(false);
		}
		*/
		
		/*
		Label label20 = new Label(container, SWT.NULL);
		label20.setText("");
		label20.setLayoutData(gd2);
		
		Label lm = new Label(container, SWT.NULL);
		lm.setText("Select the Main part from witch generic data will be loaded");
		lm.setLayoutData(gd2);
		
		mainPart = new Combo(container, SWT.READ_ONLY);
		for(int i = 0; i < parts.size(); i++){				
			mainPart.add(parts.get(i).getName());
		}
		mainPart.select(0);
		mainPart.setLayoutData(gd2);
		*/
		Label label60 = new Label(container, SWT.NULL);
		label60.setText("");
		label60.setLayoutData(gd2);
		
		Label shadow_sep_h2 = new Label(container, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
		shadow_sep_h2.setLayoutData(gd2);
		
		
		
		Label label7 = new Label(container, SWT.NULL);
		label7.setText("Please specify where the .UAPP file will be created...");
		label7.setLayoutData(gd2);

		name = new Text(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		name.setLayoutData(gd);		
		name.addVerifyListener(new FileV());

		Button b1 = new Button(container, SWT.PUSH);
		b1.setText("Browse");
		b1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				Dialog d = new Dialog();
				destination = d.open(parent.getShell(), new String[]{"*.uapp"}, false, "UAPP file path...");			
				if(destination != null){
					Boolean overwrite = false;
					if(!destination.getAbsolutePath().endsWith(".uapp"))
						destination = new File(destination+".uapp");
					
					File tmp = new File(destination.getAbsolutePath());
					if(tmp.exists()) {
						overwrite = MessageDialog.openConfirm(parent.getShell(),
							"File exists!", "The file "+destination.getAbsolutePath()+" already exists.\n\n" +
							"Would you like to overwrite it ?");
						if(!overwrite){
							this.widgetSelected(e);
						}
					} 
					if(destination != null){
						name.setText(destination.getAbsolutePath());
							
						if(destination.isAbsolute() && parts.size() > 0){
							setPageComplete(true);
						}
					}
					
				} else {
					if(name.getText() != ""){
						destination = new File(name.getText());
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});	

		Label label10 = new Label(container, SWT.NULL);
		label10.setText("");
		label10.setLayoutData(gd2);
		
		Label label8 = new Label(container, SWT.NULL);
		label8.setText("If your part selection is correct, please press the Next button to start the creation of the Application.");
		label8.setLayoutData(gd2);
	}

	@Override
	public boolean nextPressed() {
		//app.setMainPart(mainPart.getText());
		if(destination != null){
			g.setDestination(destination.getAbsolutePath());
			app.setDestination(destination.getAbsolutePath());
		/*
		
		if ( destination.exists() ) {
			System.out.println("Recovery enabled");
		    File parent = destination.getParentFile();
		    File recovery = new File( parent, ".org.uAAL.tool.packager.recovery");
		    GUI.getInstance().recoveryStorage = recovery;
		    if ( recovery.exists() ) {
				try {
				    ObjectInputStream ois = new ObjectInputStream( new FileInputStream( recovery ) );
				    MPA recoveredStatus = (MPA) ois.readObject();
				    multipartApplication.setApplication(recoveredStatus.getAAL_UAPP());
				} catch (Exception e) {		
				    e.printStackTrace();
				}
		    } else {
		    	System.out.println("[WARNING] No recovery data found even if package exists");
		    }
		} else try {
			System.out.println(destination.getCanonicalPath()+" non esiste!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
			return true;
		} else return false;
	}
}