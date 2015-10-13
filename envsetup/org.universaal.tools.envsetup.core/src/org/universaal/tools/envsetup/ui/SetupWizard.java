/*	
	Copyright 2007-2016 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institute for Computer Graphics Research
	
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
package org.universaal.tools.envsetup.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.universaal.tools.envsetup.core.EclipseAdapter;
import org.universaal.tools.envsetup.core.Importer;
import org.universaal.tools.envsetup.core.MavenAdapter;
import org.universaal.tools.envsetup.core.RepoMgmt;
import org.universaal.tools.envsetup.core.RepoMgmt.Repo;

/**
 * 
 * @author Carsten Stockloew
 *
 */public class SetupWizard extends Wizard {
	private final String title = "Development Environment Setup";

	/**
	 * The only page of the wizard
	 */
	private SetupPage page;

	/**
	 * Default Constructor.
	 */
	public SetupWizard() {
		super();
		setNeedsProgressMonitor(true);
		// ImageDescriptor image =
		// AbstractUIPlugin.imageDescriptorFromPlugin("org.universaal.tools.newwizard.plugin",
		// //$NON-NLS-1$
		// "icons/ic-uAAL-hdpi.png"); //$NON-NLS-1$
		// setDefaultPageImageDescriptor(image);
		setWindowTitle(title);
	}

	@Override
	public void addPages() {
		page = new SetupPage();
		addPage(page);
	}

	@Override
	public boolean canFinish() {
		return page.isPageComplete();
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	@Override
	public boolean performFinish() {
		// get info from the wizard
		final boolean doAdMaven = page.btnAdMaven.getSelection();
		final boolean doAdEclipse = page.btnAdEclipse.getSelection();
		final boolean doImport = page.btnImport.getSelection();
		final String branch = page.cbBranch.getText();
		final String dir = page.txtDir.getText();
		final List<Repo> repos = new ArrayList<Repo>();
		for (String name : page.repoItems.keySet()) {
			if (page.repoItems.get(name).getSelection())
				repos.add(RepoMgmt.repos.get(name));
		}
		final String jdk = page.txtJDK.getText();

		// System.out.println("doAdMaven: " + doAdMaven);
		// System.out.println("doAdEclipse: " + doAdEclipse);
		// System.out.println("doImport: " + doImport);
		// System.out.println("branch: " + branch);
		// System.out.println("dir: " + dir);
		// System.out.println("repos:");
		// for (Repo r : repos) {
		// System.out.println(" - " + r.name);
		// }

		// this job performs the creation of the item
		Job job = new WorkspaceJob(SetupPage.title) {
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				try {
					boolean changed = false;
					if (doAdMaven) {
						new MavenAdapter().perform();
					}

					if (doAdEclipse) {
						changed = new EclipseAdapter().perform(jdk);
					}

					if (doImport) {
						for (Repo r : repos) {
							if (monitor.isCanceled())
								return Status.CANCEL_STATUS;
							new Importer().perform(r, branch, dir, monitor);
						}
					}

					if (changed) {
						// request a restart of eclipse
						System.out.println("Eclipse.ini has been changed, a restart of Eclipse is recommended.");
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								if (MessageDialog.openQuestion(getShell(), "Restart Eclipse",
										"Eclipse.ini has been changed, a restart of Eclipse is recommended. Do you want to restart now?")) {
									PlatformUI.getWorkbench().restart();
								} else {
									System.out.println("no restart..");
								}
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new OperationCanceledException(e.getMessage());
				}
				return Status.OK_STATUS;
			}
		};

		// Listener in case job fails
		job.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				final IStatus result = event.getResult();
				if (!result.isOK()) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							MessageDialog.openError(getShell(), "An error occurred", result // $NON-NLS-1$
									.getMessage());
						}
					});
				}
			}
		});

		// Because we don't need to monitor changes in workspace,
		// we directly perform the job
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
		return true;
	}
}
