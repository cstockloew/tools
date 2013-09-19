package org.universaal.tools.modelling.servicemodel.presentation;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;
import org.universaal.tools.modelling.servicemodel.PropertyPath;
import org.universaal.tools.modelling.servicemodel.ServiceInterface;
import org.universaal.tools.modelling.servicemodel.provider.ServiceModelItemProviderAdapterFactory;

public class PropertyPathDialog2 extends ElementTreeSelectionDialog {

	Property[] resultPath = null;
	

	@Override
	protected void okPressed() {
		TreeViewer t = this.getTreeViewer();
		if (t.getSelection() instanceof ITreeSelection) {
			ITreeSelection tSel = (ITreeSelection)t.getSelection();
			TreePath paths[] = tSel.getPaths();
			
			if (paths.length > 0) {
				resultPath = new Property[paths[0].getSegmentCount()];
				for (int i = 0; i < paths[0].getSegmentCount(); i++) {
					resultPath[i] = (Property)paths[0].getSegment(i);
				}
			}
		}
		
		// TODO Auto-generated method stub
		super.okPressed();
	}


	public PropertyPathDialog2(Shell parent, ILabelProvider labelProvider,
			ITreeContentProvider contentProvider) {
		super(parent, labelProvider, contentProvider);
		// TODO Auto-generated constructor stub
	}
	
	
	public Property[] getResultPath() {
		return resultPath;
	}
	
	
	public static PropertyPathDialog2 createPropertyPathDialog(Shell parent, Object obj, IItemPropertyDescriptor itemPropDesc) {
		ILabelProvider labelProvider = null;
		ITreeContentProvider contentProvider = null;
		
				
//		if (obj instanceof PropertyPath) {
		if (obj instanceof EObject) {
			//PropertyPath pPath = (PropertyPath)obj;
			
			// Find interface by nesting upwards
			//EObject container = pPath.eContainer();
			EObject container = ((EObject) obj).eContainer();
			while ((container != null) && (!(container instanceof ServiceInterface))) {
				container = container.eContainer();
			}
			if (container != null) {
				ServiceInterface inter = (ServiceInterface)container;
				EObject serviceClass = inter.getService();
				if (serviceClass == null) {
					// Notify that a service must first be selected 
					// TODO: provide dialog notification and restructure
					return null;
				}
				
				ServiceModelItemProviderAdapterFactory fac2 = new ServiceModelItemProviderAdapterFactory();
				
				//contentProvider = new AdapterFactoryContentProvider(fac2);
			    //labelProvider = new AdapterFactoryLabelProvider(fac2);
				labelProvider = new LabelProvider() {
					@Override
					public String getText(Object element) {
						if (element instanceof Classifier) {
							return "Class " + ((Classifier)element).getName();
						}
						if (element instanceof Property) {
							Property prop = (Property)element;
							Element owner = prop.getOwner();
							if (owner instanceof NamedElement)
								return ((NamedElement)owner).getName() + "::" + prop.getName();
							else	
								return "::" + prop.getName();
						}
						else {
							return element.toString();
						}
					}					
				};
				
				
				contentProvider = new ITreeContentProvider() {

					@Override
					public void dispose() {
					}

					@Override
					public void inputChanged(Viewer viewer, Object oldInput,
							Object newInput) {
						// Do nothing for now
					}

					@Override
					public Object[] getElements(Object inputElement) {
						if (inputElement instanceof Classifier) {
							return ((Classifier)inputElement).getAttributes().toArray();							
						}
						return new Object[0];
					}

					@Override
					public Object[] getChildren(Object parentElement) {
						if (parentElement instanceof Classifier)
							return ((Classifier)parentElement).getAttributes().toArray();
						else if (parentElement instanceof Property) {
							Property prop = (Property)parentElement;
							if (prop.getType() instanceof Classifier)
								return ((Classifier)prop.getType()).getAttributes().toArray();						
						}
						return new Object[0];
					}

					@Override
					public Object getParent(Object element) {
						//if (element instanceof Property)
						//	return ((Property)element).getOwner();
						// We can not really compute the parent, as there can be multiple paths to the element
						return null;
					}

					@Override
					public boolean hasChildren(Object element) {
						// TODO Auto-generated method stub
						return (getChildren(element).length > 0);
					}
					
				};
				
				
				
			    PropertyPathDialog2 dialog =  new PropertyPathDialog2(parent, labelProvider, contentProvider);
			    dialog.setInput(serviceClass);
			    dialog.setTitle("Select property path");
			    dialog.setMessage("Select the path to the propety in the ontology that the parameter or effect being edited is related to");
//			    if (pPath.getProperties() != null) {
				    //TreeSelection sel = new TreeSelection(new TreePath(pPath.getProperties().toArray()));			
				    //dialog.getTreeViewer().setSelection(sel, true);
//			    	for (Property prop : pPath.getProperties()) {
//					    dialog.setInitialSelection(prop);		    		
//			    	}
//			    }
			    //dialog.getTreeViewer();
			    //dialog.
			    return dialog;
			}
			//UMLItemProviderAdapterFactory fac = new UMLItemProviderAdapterFactory();
			
		}
		return null;
	}

}
