package org.universaal.tools.packaging.api;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

public abstract class WizardPageMod extends WizardPage {

	public WizardPageMod(String pageName) {
		super(pageName);
	}

	public WizardPageMod(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	public void createControl(Composite parent) {
	}

	public boolean nextPressed(){
		return true;
	}	

	public boolean backPressed(){
		return true;
	}	
}