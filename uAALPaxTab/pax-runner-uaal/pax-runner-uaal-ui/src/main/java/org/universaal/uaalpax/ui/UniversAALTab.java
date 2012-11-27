/*	
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
	
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

package org.universaal.uaalpax.ui;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.pde.ui.launcher.AbstractLauncherTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.universaal.uaalpax.model.BundleChangeListener;
import org.universaal.uaalpax.model.BundleModel;
import org.universaal.uaalpax.model.HardcodedConfigProvider;
import org.universaal.uaalpax.model.UAALVersionProvider;
import org.universaal.uaalpax.shared.MavenDependencyResolver;

public class UniversAALTab extends AbstractLauncherTab implements BundleChangeListener {
	private WorkspaceProjectsBlock managerTable;
	private AllLibsBlock additionalLibsBlock;
	private VersionBlock versionBlock;
	private ILaunchConfiguration launchConfig;
	private UAALVersionProvider versionProvider;
	
	private BundleModel model;
	
	private MavenDependencyResolver dependencyResolver;
	
	private boolean m_initializing;
	
	public UniversAALTab() {
		m_initializing = false;
		dependencyResolver = new MavenDependencyResolver();
		versionProvider = new HardcodedConfigProvider();
		
		model = new BundleModel(dependencyResolver, versionProvider);
		model.addChangeListener(this);
	}
	
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		GridLayout gridLayout = new GridLayout();
		container.setLayout(gridLayout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		versionBlock = new VersionBlock(this, container, SWT.NONE);
		versionBlock.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Group featureGroup = new Group(container, SWT.NONE);
		featureGroup.setText("Features");
		featureGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
		featureGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		String[] features = new String[] { "UI-Framework", "CHE", "SE", "Foo ..." };
		
		SelectionListener featuresListener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.widget;
				onFeatureSelected(btn.getText(), btn.getSelection());
			}
		};
		
		for (String f : features) {
			Button btn = new Button(featureGroup, SWT.CHECK);
			btn.setText(f);
			btn.addSelectionListener(featuresListener);
		}
		
		SashForm sf = new SashForm(container, SWT.VERTICAL);
		sf.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		managerTable = new WorkspaceProjectsBlock(this, sf, SWT.NONE);
		additionalLibsBlock = new AllLibsBlock(this, sf, SWT.NONE);
		
		setControl(container);
		
		// order is important
		model.addPresenter(versionBlock);
		model.addPresenter(managerTable);
		model.addPresenter(additionalLibsBlock);
	}
	
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
	}
	
	public void initializeFrom(ILaunchConfiguration configuration) {
		m_initializing = true;
		try {
			launchConfig = configuration;
			model.updateModel(configuration);
		} finally {
			m_initializing = false;
		}
	}
	
	public MavenDependencyResolver getDependencyResolver() {
		return dependencyResolver;
	}
	
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		model.performApply(configuration);
	}
	
	public BundleModel getModel() {
		return model;
	}
	
	public String getName() {
		return "uAAL Runner";
	}
	
	@Override
	public void validateTab() {
		// no validation required
	}
	
	@Override
	public void updateLaunchConfigurationDialog() {
		if (!m_initializing) {
			super.updateLaunchConfigurationDialog();
		}
	}
	
	private void onFeatureSelected(String feature, boolean selected) {
		// TODO
	}
	
	public void notifyChanged() {
		updateLaunchConfigurationDialog();
	}
}
