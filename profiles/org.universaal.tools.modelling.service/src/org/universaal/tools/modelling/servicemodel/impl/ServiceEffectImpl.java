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

import org.universaal.tools.modelling.servicemodel.EffectType;
import org.universaal.tools.modelling.servicemodel.PropertyPath;
import org.universaal.tools.modelling.servicemodel.ServiceEffect;
import org.universaal.tools.modelling.servicemodel.ServiceModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service Effect</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.universaal.tools.modelling.servicemodel.impl.ServiceEffectImpl#getEffectType <em>Effect Type</em>}</li>
 *   <li>{@link org.universaal.tools.modelling.servicemodel.impl.ServiceEffectImpl#getEffectValue <em>Effect Value</em>}</li>
 *   <li>{@link org.universaal.tools.modelling.servicemodel.impl.ServiceEffectImpl#getPropertyPath <em>Property Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServiceEffectImpl extends NamedElementImpl implements ServiceEffect {
	/**
	 * The default value of the '{@link #getEffectType() <em>Effect Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEffectType()
	 * @generated
	 * @ordered
	 */
	protected static final EffectType EFFECT_TYPE_EDEFAULT = EffectType.CHANGE;

	/**
	 * The cached value of the '{@link #getEffectType() <em>Effect Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEffectType()
	 * @generated
	 * @ordered
	 */
	protected EffectType effectType = EFFECT_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getEffectValue() <em>Effect Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEffectValue()
	 * @generated
	 * @ordered
	 */
	protected static final String EFFECT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEffectValue() <em>Effect Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEffectValue()
	 * @generated
	 * @ordered
	 */
	protected String effectValue = EFFECT_VALUE_EDEFAULT;

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
	protected ServiceEffectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ServiceModelPackage.Literals.SERVICE_EFFECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EffectType getEffectType() {
		return effectType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEffectType(EffectType newEffectType) {
		EffectType oldEffectType = effectType;
		effectType = newEffectType == null ? EFFECT_TYPE_EDEFAULT : newEffectType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServiceModelPackage.SERVICE_EFFECT__EFFECT_TYPE, oldEffectType, effectType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEffectValue() {
		return effectValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEffectValue(String newEffectValue) {
		String oldEffectValue = effectValue;
		effectValue = newEffectValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServiceModelPackage.SERVICE_EFFECT__EFFECT_VALUE, oldEffectValue, effectValue));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ServiceModelPackage.SERVICE_EFFECT__PROPERTY_PATH, oldPropertyPath, newPropertyPath);
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
				msgs = ((InternalEObject)propertyPath).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ServiceModelPackage.SERVICE_EFFECT__PROPERTY_PATH, null, msgs);
			if (newPropertyPath != null)
				msgs = ((InternalEObject)newPropertyPath).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ServiceModelPackage.SERVICE_EFFECT__PROPERTY_PATH, null, msgs);
			msgs = basicSetPropertyPath(newPropertyPath, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServiceModelPackage.SERVICE_EFFECT__PROPERTY_PATH, newPropertyPath, newPropertyPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ServiceModelPackage.SERVICE_EFFECT__PROPERTY_PATH:
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
			case ServiceModelPackage.SERVICE_EFFECT__EFFECT_TYPE:
				return getEffectType();
			case ServiceModelPackage.SERVICE_EFFECT__EFFECT_VALUE:
				return getEffectValue();
			case ServiceModelPackage.SERVICE_EFFECT__PROPERTY_PATH:
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
			case ServiceModelPackage.SERVICE_EFFECT__EFFECT_TYPE:
				setEffectType((EffectType)newValue);
				return;
			case ServiceModelPackage.SERVICE_EFFECT__EFFECT_VALUE:
				setEffectValue((String)newValue);
				return;
			case ServiceModelPackage.SERVICE_EFFECT__PROPERTY_PATH:
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
			case ServiceModelPackage.SERVICE_EFFECT__EFFECT_TYPE:
				setEffectType(EFFECT_TYPE_EDEFAULT);
				return;
			case ServiceModelPackage.SERVICE_EFFECT__EFFECT_VALUE:
				setEffectValue(EFFECT_VALUE_EDEFAULT);
				return;
			case ServiceModelPackage.SERVICE_EFFECT__PROPERTY_PATH:
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
			case ServiceModelPackage.SERVICE_EFFECT__EFFECT_TYPE:
				return effectType != EFFECT_TYPE_EDEFAULT;
			case ServiceModelPackage.SERVICE_EFFECT__EFFECT_VALUE:
				return EFFECT_VALUE_EDEFAULT == null ? effectValue != null : !EFFECT_VALUE_EDEFAULT.equals(effectValue);
			case ServiceModelPackage.SERVICE_EFFECT__PROPERTY_PATH:
				return propertyPath != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (effectType: ");
		result.append(effectType);
		result.append(", effectValue: ");
		result.append(effectValue);
		result.append(')');
		return result.toString();
	}

} //ServiceEffectImpl
