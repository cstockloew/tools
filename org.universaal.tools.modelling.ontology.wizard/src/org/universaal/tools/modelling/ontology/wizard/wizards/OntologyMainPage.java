package org.universaal.tools.modelling.ontology.wizard.wizards;


import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.core.databinding.Binding;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;

public class OntologyMainPage extends WizardPage {
	private Binding projectNameBinding;
	private DataBindingContext m_bindingContext;
	private Text txtOntologyname;
	private Text txtPackagename;
	private Text txtProjectname;
	private Text txtNamespace;

	/**
	 * Create the wizard.
	 */
	public OntologyMainPage() {
		super("wizardPage");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.universaal.tools.modelling.ontology.wizard", "icons/ic-uAAL-hdpi.png"));
		setTitle("Ontology project properties");
		setDescription("Set the name and packaging for the ontology and the Eclipse project");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(2, false));
		
		Label lblOntologyName = new Label(container, SWT.NONE);
		lblOntologyName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOntologyName.setText("Ontology name");
		
		txtOntologyname = new Text(container, SWT.BORDER);
		txtOntologyname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Update time");
				projectNameBinding.updateModelToTarget();
			}
		});
		txtOntologyname.setText("ontologyName");
		txtOntologyname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPackageName = new Label(container, SWT.NONE);
		lblPackageName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPackageName.setText("Package name");
		
		txtPackagename = new Text(container, SWT.BORDER);
		txtPackagename.setText("packageName");
		txtPackagename.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		btnUseDefaultValues = new Button(container, SWT.CHECK);
		btnUseDefaultValues.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnUseDefaultValues.setText("Use derived values");
		
		Label lblProjectName = new Label(container, SWT.NONE);
		lblProjectName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblProjectName.setText("Project name");
		
		txtProjectname = new Text(container, SWT.BORDER);
		txtProjectname.setText("projectName");
		txtProjectname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblOntologyNamespace = new Label(container, SWT.NONE);
		lblOntologyNamespace.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOntologyNamespace.setText("Namespace");
		
		txtNamespace = new Text(container, SWT.BORDER);
		txtNamespace.setText("namespace");
		txtNamespace.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		m_bindingContext = initDataBindings();
	}
	
	OntologyProjectModel model;
	private Button btnUseDefaultValues;
	
	public void setModel(OntologyProjectModel model) {
		this.model = model;
	}
	
	public void updateFromModel() {
		
	}
	
	public void updateModel() {
		
	}	
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue txtOntologynameTextObserveValue = PojoObservables.observeValue(txtOntologyname, "text");
		IObservableValue modelOntologyNameObserveValue = PojoObservables.observeValue(model, "ontologyName");
		bindingContext.bindValue(txtOntologynameTextObserveValue, modelOntologyNameObserveValue, null, null);
		//
		IObservableValue txtPackagenameTextObserveValue = PojoObservables.observeValue(txtPackagename, "text");
		IObservableValue modelPackageNameObserveValue = PojoObservables.observeValue(model, "packageName");
		bindingContext.bindValue(txtPackagenameTextObserveValue, modelPackageNameObserveValue, null, null);
		//
		IObservableValue txtProjectnameTextObserveValue = PojoObservables.observeValue(txtProjectname, "text");
		IObservableValue modelProjectNameObserveValue = PojoObservables.observeValue(model, "projectName");
		projectNameBinding = bindingContext.bindValue(txtProjectnameTextObserveValue, modelProjectNameObserveValue, null, null);
		//
		IObservableValue txtNamespaceObserveTextObserveWidget = SWTObservables.observeText(txtNamespace, SWT.Modify);
		IObservableValue modelOntologyNamespaceObserveValue = PojoObservables.observeValue(model, "ontologyNamespace");
		bindingContext.bindValue(txtNamespaceObserveTextObserveWidget, modelOntologyNamespaceObserveValue, null, null);
		//
		IObservableValue btnUseDefaultValuesObserveSelectionObserveWidget = SWTObservables.observeSelection(btnUseDefaultValues);
		IObservableValue modelUseDerivedValuesObserveValue = PojoObservables.observeValue(model, "useDerivedValues");
		bindingContext.bindValue(btnUseDefaultValuesObserveSelectionObserveWidget, modelUseDerivedValuesObserveValue, null, null);
		//
		return bindingContext;
	}
}
