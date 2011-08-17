/*
	Copyright 2011 SINTEF, http://www.sintef.no
	
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
package org.universaal.tools.uploadopensourceplugin.email;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
/**
 * Class that handles the opening of the default mail-client and also generating
 * an email that can be sent to the AAL Studio team containing all important 
 * information about a project.
 * @author Adrian
 *
 */
public class SendEmail {

	private final static String EMAIL = "universAAL@something.org";
	private final static String SUBJECT = "New universAAL Project";
	
	private Desktop desktop;
	private File file;
	private String uri;
	private IProject project;

	/**
	 * Constructor that finds the currently selected project, and then the file
	 * "aalapp.xml" at its root.
	 */
	public SendEmail(){
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		ISelectionService service = PlatformUI.getWorkbench().
				getActiveWorkbenchWindow().getSelectionService();
		IStructuredSelection structured = (IStructuredSelection) service
				.getSelection("org.eclipse.jdt.ui.PackageExplorer");
		IProject project=null;
		Object element;
		IPath path;

		element = structured.getFirstElement();

		if(element instanceof IResource){
			project = ((IResource)element).getProject();
		}else if (element instanceof PackageFragment){
			IJavaProject jProject = ((PackageFragment)element).getJavaProject();
			project = jProject.getProject();
		}else if (element instanceof IJavaElement){
			IJavaProject jProject = ((IJavaElement)element).getJavaProject();
			project = jProject.getProject();
		}

		String string = project.getLocation().toPortableString();

		this.file = new File(string+"/aalapp.xml");
		
		if(file!=null){
			generateURI();
		}
		
		
	}

	/**
	 * Constructor that takes the currently selected projects as input, and then
	 * finds the file "aalapp.xml" at its root.
	 * @param project - The currently selected project in the package explorer.
	 */
	public SendEmail(IProject project){
		this.project = project;
		String string = project.getLocation().toPortableString();
		this.file = new File(string+"/aalapp.xml");
		if(file!=null){
			generateURI();
		}
		
	}

	/**
	 * Generates an URI that is used to fill out address fields and the body of
	 * the email. The method expects that the URI string is later encoded using
	 * e.g. the multi-parameter constructors for the URI class.
	 */
	private void generateURI(){
		try{
			// Reads the full content of the xml file as a string, and builds the content of a mailto URI
		    BufferedReader reader = new BufferedReader( new FileReader (file));
		    String str  = null;
		    StringBuilder strBuilder = new StringBuilder();
		    String ls = System.getProperty("line.separator");
		    while( ( str = reader.readLine() ) != null ) {
		        strBuilder.append( str );
		        strBuilder.append( ls );
		    }
		    uri = EMAIL+"?SUBJECT="+SUBJECT+"&BODY="+strBuilder.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the default mail client and if the field this.uri is set, attempts 
	 * to fill in address field and body of the email. If the URI is malformed,
	 * it instead opens a blank email.
	 */
	public void sendEmail(){
		if(Desktop.isDesktopSupported()){
			desktop = Desktop.getDesktop();
			
			//First tries to send construct the URI including the message body.
			try {
				if(uri!=null)
					desktop.mail(new URI("mailto", uri, null)); // Using multi-parameter constructor to get encoding
				else
					desktop.mail();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				
				//If the string contained illegal characters, instead of 
				//including the body of the mail, it only fills out the address
				//and subject fields.
				MessageDialog.openInformation(PlatformUI.getWorkbench().
						getActiveWorkbenchWindow().getShell(), "Malformed URI",
						"The URI used to generate email body was malformed. \n" +
						"Does your aalapp.xml contain any very special characters?");
				e.printStackTrace();
				try {
					String uri = "mailto:"+EMAIL+"?SUBJECT="+SUBJECT;
					desktop.mail(new URI(uri));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e.printStackTrace();
				}
				
			}
		}
	}

}
