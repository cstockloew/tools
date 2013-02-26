/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.universaal.tools.modelling.servicemodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.universaal.tools.modelling.servicemodel.Parameter;
import org.universaal.tools.modelling.servicemodel.PropertyPath;
import org.universaal.tools.modelling.servicemodel.ServiceModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.universaal.tools.modelling.servicemodel.impl.ParameterImpl#getPropertyPath <em>Property Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ParameterImpl extends NamedElementImpl implements Parameter {
	/**
	 * The cached value of the '{@link #getPropertyPath() <em>Property Path</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyPath()
	 * @generated
	 * @ordered
	 */
	protected PropertyPath propertyPath;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ServiceModelPackage.Literals.PARAMETER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyPath getPropertyPath() {
		return propertyPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropertyPath(PropertyPath newPropertyPath, NotificationChain msgs) {
		PropertyPath oldPropertyPath = propertyPath;
		propertyPath = newPropertyPath;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ServiceModelPackage.PARAMETER__PROPERTY_PATH, oldPropertyPath, newPropertyPath);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyPath(PropertyPath newPropertyPath) {
		if (newPropertyPath != propertyPath) {
			NotificationChain msgs = null;
			if (propertyPath != null)
				msgs = ((InternalEObject)propertyPath).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ServiceModelPackage.PARAMETER__PROPERTY_PATH, null, msgs);
			if (newPropertyPath != null)
				msgs = ((InternalEObject)newPropertyPath).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ServiceModelPackage.PARAMETER__PROPERTY_PATH, null, msgs);
			msgs = basicSetPropertyPath(newPropertyPath, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServiceModelPackage.PARAMETER__PROPERTY_PATH, newPropertyPath, newPropertyPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ServiceModelPackage.PARAMETER__PROPERTY_PATH:
				return basicSetPropertyPath(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ServiceModelPackage.PARAMETER__PROPERTY_PATH:
				return getPropertyPath();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ServiceModelPackage.PARAMETER__PROPERTY_PATH:
				setPropertyPath((PropertyPath)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ServiceModelPackage.PARAMETER__PROPERTY_PATH:
				setPropertyPath((PropertyPath)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ServiceModelPackage.PARAMETER__PROPERTY_PATH:
				return propertyPath != null;
		}
		return super.eIsSet(featureID);
	}

} //ParameterImpl
