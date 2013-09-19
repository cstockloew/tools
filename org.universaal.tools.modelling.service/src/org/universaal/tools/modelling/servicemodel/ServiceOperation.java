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
 * A representation of the model object '<em><b>Service Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.universaal.tools.modelling.servicemodel.ServiceOperation#getInput <em>Input</em>}</li>
 *   <li>{@link org.universaal.tools.modelling.servicemodel.ServiceOperation#getOutput <em>Output</em>}</li>
 *   <li>{@link org.universaal.tools.modelling.servicemodel.ServiceOperation#getEffects <em>Effects</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getServiceOperation()
 * @model
 * @generated
 */
public interface ServiceOperation extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Input</b></em>' containment reference list.
	 * The list contents are of type {@link org.universaal.tools.modelling.servicemodel.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input</em>' containment reference list.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getServiceOperation_Input()
	 * @model containment="true"
	 * @generated
	 */
	EList<Parameter> getInput();

	/**
	 * Returns the value of the '<em><b>Output</b></em>' containment reference list.
	 * The list contents are of type {@link org.universaal.tools.modelling.servicemodel.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output</em>' containment reference list.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getServiceOperation_Output()
	 * @model containment="true"
	 * @generated
	 */
	EList<Parameter> getOutput();

	/**
	 * Returns the value of the '<em><b>Effects</b></em>' containment reference list.
	 * The list contents are of type {@link org.universaal.tools.modelling.servicemodel.ServiceEffect}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Effects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Effects</em>' containment reference list.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getServiceOperation_Effects()
	 * @model containment="true"
	 * @generated
	 */
	EList<ServiceEffect> getEffects();

} // ServiceOperation
