/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.universaal.tools.modelling.servicemodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.universaal.tools.modelling.servicemodel.Parameter#getPropertyPath <em>Property Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getParameter()
 * @model
 * @generated
 */
public interface Parameter extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Property Path</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Path</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Path</em>' containment reference.
	 * @see #setPropertyPath(PropertyPath)
	 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getParameter_PropertyPath()
	 * @model containment="true"
	 * @generated
	 */
	PropertyPath getPropertyPath();

	/**
	 * Sets the value of the '{@link org.universaal.tools.modelling.servicemodel.Parameter#getPropertyPath <em>Property Path</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Path</em>' containment reference.
	 * @see #getPropertyPath()
	 * @generated
	 */
	void setPropertyPath(PropertyPath value);

} // Parameter
