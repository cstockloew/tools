package org.universaal.tools.modelling.ontology.wizard.wizards;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class OntologyProjectModelComposite extends Composite {

	private DataBindingContext m_bindingContext;
	private org.universaal.tools.modelling.ontology.wizard.wizards.OntologyProjectModel ontologyProjectModel = new org.universaal.tools.modelling.ontology.wizard.wizards.OntologyProjectModel();
	private Text ontologyNameText;
	private Text ontologyNamespaceText;
	private Text packageNameText;
	private Text projectNameText;
	private Button useDerivedValuesButton;

	public OntologyProjectModelComposite(
			Composite parent,
			int style,
			org.universaal.tools.modelling.ontology.wizard.wizards.OntologyProjectModel newOntologyProjectModel) {
		this(parent, style);
		setOntologyProjectModel(newOntologyProjectModel);
	}

	public OntologyProjectModelComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));

		new Label(this, SWT.NONE).setText("OntologyName:");

		ontologyNameText = new Text(this, SWT.BORDER | SWT.SINGLE);
		ontologyNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));

		new Label(this, SWT.NONE).setText("OntologyNamespace:");

		ontologyNamespaceText = new Text(this, SWT.BORDER | SWT.SINGLE);
		ontologyNamespaceText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));

		new Label(this, SWT.NONE).setText("PackageName:");

		packageNameText = new Text(this, SWT.BORDER | SWT.SINGLE);
		packageNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));

		new Label(this, SWT.NONE).setText("ProjectName:");

		projectNameText = new Text(this, SWT.BORDER | SWT.SINGLE);
		projectNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));

		new Label(this, SWT.NONE).setText("UseDerivedValues:");

		useDerivedValuesButton = new Button(this, SWT.CHECK);
		useDerivedValuesButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));

		if (ontologyProjectModel != null) {
			m_bindingContext = initDataBindings();
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private DataBindingContext initDataBindings() {
		IObservableValue ontologyNameObserveWidget = SWTObservables
				.observeText(ontologyNameText, SWT.Modify);
		IObservableValue ontologyNameObserveValue = BeansObservables
				.observeValue(ontologyProjectModel, "ontologyName");
		IObservableValue ontologyNamespaceObserveWidget = SWTObservables
				.observeText(ontologyNamespaceText, SWT.Modify);
		IObservableValue ontologyNamespaceObserveValue = BeansObservables
				.observeValue(ontologyProjectModel, "ontologyNamespace");
		IObservableValue packageNameObserveWidget = SWTObservables.observeText(
				packageNameText, SWT.Modify);
		IObservableValue packageNameObserveValue = BeansObservables
				.observeValue(ontologyProjectModel, "packageName");
		IObservableValue projectNameObserveWidget = SWTObservables.observeText(
				projectNameText, SWT.Modify);
		IObservableValue projectNameObserveValue = BeansObservables
				.observeValue(ontologyProjectModel, "projectName");
		IObservableValue useDerivedValuesObserveWidget = SWTObservables
				.observeSelection(useDerivedValuesButton);
		IObservableValue useDerivedValuesObserveValue = BeansObservables
				.observeValue(ontologyProjectModel, "useDerivedValues");
		//
		DataBindingContext bindingContext = new DataBindingContext();
		//
		bindingContext.bindValue(ontologyNameObserveWidget,
				ontologyNameObserveValue, null, null);
		bindingContext.bindValue(ontologyNamespaceObserveWidget,
				ontologyNamespaceObserveValue, null, null);
		bindingContext.bindValue(packageNameObserveWidget,
				packageNameObserveValue, null, null);
		bindingContext.bindValue(projectNameObserveWidget,
				projectNameObserveValue, null, null);
		bindingContext.bindValue(useDerivedValuesObserveWidget,
				useDerivedValuesObserveValue, null, null);
		//
		return bindingContext;
	}

	public org.universaal.tools.modelling.ontology.wizard.wizards.OntologyProjectModel getOntologyProjectModel() {
		return ontologyProjectModel;
	}

	public void setOntologyProjectModel(
			org.universaal.tools.modelling.ontology.wizard.wizards.OntologyProjectModel newOntologyProjectModel) {
		setOntologyProjectModel(newOntologyProjectModel, true);
	}

	public void setOntologyProjectModel(
			org.universaal.tools.modelling.ontology.wizard.wizards.OntologyProjectModel newOntologyProjectModel,
			boolean update) {
		ontologyProjectModel = newOntologyProjectModel;
		if (update) {
			if (m_bindingContext != null) {
				m_bindingContext.dispose();
				m_bindingContext = null;
			}
			if (ontologyProjectModel != null) {
				m_bindingContext = initDataBindings();
			}
		}
	}

}
