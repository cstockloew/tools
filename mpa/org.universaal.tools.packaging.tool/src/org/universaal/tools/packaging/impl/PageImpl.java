package org.universaal.tools.packaging.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.api.Page;
import org.universaal.tools.packaging.api.WizardPageMod;
import org.universaal.tools.packaging.tool.gui.GUI;
import org.universaal.tools.packaging.tool.parts.Application;
import org.universaal.tools.packaging.tool.parts.MPA;

public abstract class PageImpl extends WizardPageMod implements Page {

	protected Composite container;
	protected GridData gd;

	protected MPA multipartApplication;
	protected Application app;
	protected List<Control> mandatory;

	protected static int otherLicenses = 1;

	protected PageImpl(String pageName) {
		super(pageName);
		setTitle(pageName);		

		mandatory = new ArrayList<Control>();
		setPageComplete(false);
	}

	protected PageImpl(String pageName, String description){
		super(pageName);
		setDescription(description);
		setTitle(pageName);		

		mandatory = new ArrayList<Control>();
		setPageComplete(false);
	}

	@Override
	public abstract void nextPressed(); // to handle events just before moving to next page

	public void setMPA(MPA mpa) {
		multipartApplication = mpa;
		app = multipartApplication.getAAL_UAPP();
	}

	public boolean validate(){

		for(int i = 0; i < mandatory.size(); i++){
			if(mandatory.get(i) instanceof Text)
				if(((Text)mandatory.get(i)).getText().isEmpty())
					return false;
			if(mandatory.get(i) instanceof Combo)
				if(((Combo)mandatory.get(i)).getText().isEmpty())
					return false;
		}
		return true;
	}

	public boolean isValid(Control c1, Control c2, Control c3){

		if(c1 == null && c2 == null && c3 == null)
			return false;

		if(c1 != null){
			if(c1 instanceof Text)
				if(((Text)c1).getText().isEmpty())
					return false;
			if(c1 instanceof Combo)
				if(((Combo)c1).getText().isEmpty())
					return false;
		}

		if(c2 != null){
			if(c2 instanceof Text)
				if(((Text)c2).getText().isEmpty())
					return false;
			if(c2 instanceof Combo)
				if(((Combo)c2).getText().isEmpty())
					return false;
		}

		if(c3 != null){
			if(c3 instanceof Text)
				if(((Text)c3).getText().isEmpty())
					return false;
			if(c3 instanceof Combo)
				if(((Combo)c3).getText().isEmpty())
					return false;
		}

		return true;
	}

	public boolean isValid(Control c1){
		return isValid(c1, null, null);
	}

	public boolean isValid(Control c1, Control c2){
		return isValid(c1, c2, null);
	}

	public void addPageCustom(IWizardPage caller, IWizardPage newPage){ 

		GUI gui = GUI.getInstance();
		gui.addPage(newPage, gui.getPageNumber(caller)+1);
	}

	public void setArtifact(IProject p){}

	public abstract class QL implements KeyListener{

		public void keyPressed(KeyEvent e) {
			setPageComplete(validate());
		}

		public abstract void keyReleased(KeyEvent e);	
	}

	public class FullListener extends QL{

		@Override
		public void keyReleased(KeyEvent e) {
		}}
}