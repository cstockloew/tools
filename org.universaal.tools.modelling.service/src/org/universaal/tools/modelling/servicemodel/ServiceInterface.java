/**
 * 	Copyright 2013 SINTEF, http://www.sintef.no
 * 	
 * 	See the NOTICE file distributed with this work for additional 
 * 	information regarding copyright ownership
 * 	
 * 	Licensed under the Apache License, Version 2.0 (the "License");
 * 	you may not use this file except in compliance with the License.
 * 	You may obtain a copy of the License at
 * 	
 * 	  http://www.apache.org/licenses/LICENSE-2.0
 * 	
 * 	Unless required by applicable law or agreed to in writing, software
 * 	distributed under the License is distributed on an "AS IS" BASIS,
 * 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 	See the License for the specific language governing permissions and
 * 	limitations under the License.
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
 *   <li>{@link org.universaal.tools.modelling.servicemodel.ServiceInterface#getPackageName <em>Package Name</em>}</li>
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

	/**
	 * Returns the value of the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Name</em>' attribute.
	 * @see #setPackageName(String)
	 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getServiceInterface_PackageName()
	 * @model
	 * @generated
	 */
	String getPackageName();

	/**
	 * Sets the value of the '{@link org.universaal.tools.modelling.servicemodel.ServiceInterface#getPackageName <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Name</em>' attribute.
	 * @see #getPackageName()
	 * @generated
	 */
	void setPackageName(String value);

} // ServiceInterface
