/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.universaal.tools.modelling.servicemodel.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptorDecorator;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.universaal.tools.modelling.servicemodel.Parameter;
import org.universaal.tools.modelling.servicemodel.PropertyPath;
import org.universaal.tools.modelling.servicemodel.ServiceModelFactory;
import org.universaal.tools.modelling.servicemodel.ServiceModelPackage;
import org.universaal.tools.modelling.servicemodel.ServiceOperation;

/**
 * This is the item provider adapter for a {@link org.universaal.tools.modelling.servicemodel.Parameter} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ParameterItemProvider
	extends NamedElementItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This is the original getPropertyDescriptors 
	 * @generated NOT
	 */
	public List<IItemPropertyDescriptor> getPropertyDescriptorsGen(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

		}
		return itemPropertyDescriptors;
	}
	
	/**
	 * Adds the property path to the descriptors
	 * @generated NOT
	 */
	public void addPropertyPathDescriptors(PropertyPath pp, final String ppFeature) {
		PropertyPathItemProvider ppProvider = (PropertyPathItemProvider)adapterFactory.adapt(pp, IItemPropertySource.class);
		//List descriptors = ppProvider.getPropertyDescriptors(pp);
		for (IItemPropertyDescriptor desc : ppProvider.getPropertyDescriptors(pp)) {
			itemPropertyDescriptors.add( new ItemPropertyDescriptorDecorator(pp, desc) {

				@Override
				public String getId(Object thisObject) {
					return ppFeature + getDisplayName(thisObject);
				}
				
			});
		}
	}
	
	
	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		itemPropertyDescriptors = null; // Force rebuild
		if (itemPropertyDescriptors == null) {
			getPropertyDescriptorsGen(object);
			addPropertyPathDescriptors(((Parameter)object).getPropertyPath(), getString("_UI_Parameter_propertyPath_feature"));

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This returns Parameter.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Parameter"));
	}

	
	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		String label = ((Parameter)object).getName();
		ServiceOperation op = (ServiceOperation) ((Parameter)object).eContainer();
		boolean isInput = op.getInput().contains(object);
		if (isInput) {
			return label == null || label.length() == 0 ?
					getString("_UI_ServiceOperation_input_feature") + " " + getString("_UI_Parameter_type") :
					getString("_UI_ServiceOperation_input_feature") + " " + getString("_UI_Parameter_type") + " " + label;
		} else {
			return label == null || label.length() == 0 ?
					getString("_UI_ServiceOperation_output_feature") + " " + getString("_UI_Parameter_type") :
					getString("_UI_ServiceOperation_output_feature") + " " + getString("_UI_Parameter_type") + " " + label;
		}
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Parameter.class)) {
			case ServiceModelPackage.PARAMETER__PROPERTY_PATH:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
