package org.universaal.studio.sketch;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;

public class StartView extends ViewPart {

	public static final String ID = "org.universaal.studio.sketch.StartView"; //$NON-NLS-1$

	public StartView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		{
			Label lblWhatDoYou = new Label(container, SWT.NONE);
			lblWhatDoYou.setText("What do you want to create?");
		}
		new Label(container, SWT.NONE);
		{
			Button btnAalService = new Button(container, SWT.NONE);
			btnAalService.setText("AAL Service");
		}
		{
			Label lblAnAalService = new Label(container, SWT.WRAP);
			GridData gd_lblAnAalService = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_lblAnAalService.widthHint = 299;
			lblAnAalService.setLayoutData(gd_lblAnAalService);
			lblAnAalService.setText("Create an AAL service providing value to the end user. AAL services can be composed from existing components and services, but can also include new components");
		}
		{
			Button btnPlatformComponent = new Button(container, SWT.NONE);
			btnPlatformComponent.setText("Platform component");
		}
		{
			Label lblCreateAPlatfomr = new Label(container, SWT.WRAP);
			GridData gd_lblCreateAPlatfomr = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_lblCreateAPlatfomr.widthHint = 316;
			lblCreateAPlatfomr.setLayoutData(gd_lblCreateAPlatfomr);
			lblCreateAPlatfomr.setText("Create a platfomr component that extends the universAAL platform. Components can be reused when creating AAL services.");
		}

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

}
