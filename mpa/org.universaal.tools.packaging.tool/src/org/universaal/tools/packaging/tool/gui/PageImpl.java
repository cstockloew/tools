package org.universaal.tools.packaging.tool.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.api.Page;
import org.universaal.tools.packaging.tool.actions.SampleAction;
import org.universaal.tools.packaging.tool.parts.Application;
import org.universaal.tools.packaging.tool.parts.MPA;

public abstract class PageImpl extends WizardPage implements Page {

	protected Composite container;
	protected GridData gd;

	private MPA multipartApplication;
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

	public void setMPA(MPA mpa) {
		multipartApplication = mpa;
		app = multipartApplication.getApplication();
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

	public void addPageCustom(boolean sec, IWizardPage page){

		if(!sec){
			SampleAction.gui.addPage(page);

			IWizardPage[] pages = SampleAction.gui.getPages();
			IWizardPage newPages[] = new IWizardPage[pages.length+1];

			//System.out.println("pages "+pages.length+" newPages "+newPages.length);

			newPages[3] = pages[pages.length-1];
			pages[pages.length-1] = null;

			for(int i = 0; i < pages.length; i++){
				int j = 1;
				//System.out.println("newPages.length - j - i "+(newPages.length - j - i)+" pages.length - 1 - i "+(pages.length - 1 - i));
				if(newPages[newPages.length - j - i] != null){
					//System.out.println("newPages.length - j - i "+(newPages.length - j - i));
					j = 2;
				}
				newPages[newPages.length - j - i] = pages[pages.length - 1 - i];
			}

			for(int i = 0; i < newPages.length; i++){
				if(newPages[i] != null)
					System.out.println(""+newPages[i].getName());
				else
					System.out.println("null: "+i);
			}

			SampleAction.gui.setPages(new ArrayList<IWizardPage>(Arrays.asList(newPages)));
		}
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