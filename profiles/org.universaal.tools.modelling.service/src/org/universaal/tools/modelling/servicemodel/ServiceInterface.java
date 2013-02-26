/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.universaal.tools.modelling.servicemodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Service Interface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.universaal.tools.modelling.servicemodel.ServiceInterface#getOperations <em>Operations</em>}</li>
 *   <li>{@link org.universaal.tools.modelling.servicemodel.ServiceInterface#getService <em>Service</em>}</li>
 *   <li>{@link org.universaal.tools.modelling.servicemodel.ServiceInterface#getOntology <em>Ontology</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getServiceInterface()
 * @model
 * @generated
 */
public interface ServiceInterface extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Operations</b></em>' containment reference list.
	 * The list contents are of type {@link org.universaal.tools.modelling.servicemodel.ServiceOperation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operations</em>' containment reference list.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getServiceInterface_Operations()
	 * @model containment="true"
	 * @generated
	 */
	EList<ServiceOperation> getOperations();

	/**
	 * Returns the value of the '<em><b>Service</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service</em>' reference.
	 * @see #setService(org.eclipse.uml2.uml.Class)
	 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getServiceInterface_Service()
	 * @model required="true"
	 * @generated
	 */
	org.eclipse.uml2.uml.Class getService();

	/**
	 * Sets the value of the '{@link org.universaal.tools.modelling.servicemodel.ServiceInterface#getService <em>Service</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service</em>' reference.
	 * @see #getService()
	 * @generated
	 */
	void setService(org.eclipse.uml2.uml.Class value);

	/**
	 * Returns the value of the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ontology</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ontology</em>' reference.
	 * @see #setOntology(org.eclipse.uml2.uml.Package)
	 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getServiceInterface_Ontology()
	 * @model required="true"
	 * @generated
	 */
	org.eclipse.uml2.uml.Package getOntology();

	/**
	 * Sets the value of the '{@link org.universaal.tools.modelling.servicemodel.ServiceInterface#getOntology <em>Ontology</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ontology</em>' reference.
	 * @see #getOntology()
	 * @generated
	 */
	void setOntology(org.eclipse.uml2.uml.Package value);

} // ServiceInterface
