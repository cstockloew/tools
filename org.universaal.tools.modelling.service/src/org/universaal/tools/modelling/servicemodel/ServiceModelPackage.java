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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.universaal.tools.modelling.servicemodel.ServiceModelFactory
 * @model kind="package"
 * @generated
 */
public interface ServiceModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "servicemodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.universaal.org/tools/modelling/servicemodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "servicemodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ServiceModelPackage eINSTANCE = org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.universaal.tools.modelling.servicemodel.impl.NamedElementImpl <em>Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.universaal.tools.modelling.servicemodel.impl.NamedElementImpl
	 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.universaal.tools.modelling.servicemodel.impl.ServiceInterfaceImpl <em>Service Interface</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceInterfaceImpl
	 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl#getServiceInterface()
	 * @generated
	 */
	int SERVICE_INTERFACE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Operations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE__OPERATIONS = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Service</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE__SERVICE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE__ONTOLOGY = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE__PACKAGE_NAME = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Service Interface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.universaal.tools.modelling.servicemodel.impl.ServiceOperationImpl <em>Service Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceOperationImpl
	 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl#getServiceOperation()
	 * @generated
	 */
	int SERVICE_OPERATION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_OPERATION__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Input</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_OPERATION__INPUT = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Output</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_OPERATION__OUTPUT = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Effects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_OPERATION__EFFECTS = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Service Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_OPERATION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.universaal.tools.modelling.servicemodel.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.universaal.tools.modelling.servicemodel.impl.ParameterImpl
	 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Property Path</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__PROPERTY_PATH = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.universaal.tools.modelling.servicemodel.impl.ServiceEffectImpl <em>Service Effect</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceEffectImpl
	 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl#getServiceEffect()
	 * @generated
	 */
	int SERVICE_EFFECT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_EFFECT__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Effect Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_EFFECT__EFFECT_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Effect Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_EFFECT__EFFECT_VALUE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Property Path</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_EFFECT__PROPERTY_PATH = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Service Effect</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_EFFECT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.universaal.tools.modelling.servicemodel.impl.PropertyPathImpl <em>Property Path</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.universaal.tools.modelling.servicemodel.impl.PropertyPathImpl
	 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl#getPropertyPath()
	 * @generated
	 */
	int PROPERTY_PATH = 5;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_PATH__PROPERTIES = 0;

	/**
	 * The number of structural features of the '<em>Property Path</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_PATH_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.universaal.tools.modelling.servicemodel.EffectType <em>Effect Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.universaal.tools.modelling.servicemodel.EffectType
	 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl#getEffectType()
	 * @generated
	 */
	int EFFECT_TYPE = 6;


	/**
	 * Returns the meta object for class '{@link org.universaal.tools.modelling.servicemodel.ServiceInterface <em>Service Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Interface</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceInterface
	 * @generated
	 */
	EClass getServiceInterface();

	/**
	 * Returns the meta object for the containment reference list '{@link org.universaal.tools.modelling.servicemodel.ServiceInterface#getOperations <em>Operations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operations</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceInterface#getOperations()
	 * @see #getServiceInterface()
	 * @generated
	 */
	EReference getServiceInterface_Operations();

	/**
	 * Returns the meta object for the reference '{@link org.universaal.tools.modelling.servicemodel.ServiceInterface#getService <em>Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Service</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceInterface#getService()
	 * @see #getServiceInterface()
	 * @generated
	 */
	EReference getServiceInterface_Service();

	/**
	 * Returns the meta object for the reference '{@link org.universaal.tools.modelling.servicemodel.ServiceInterface#getOntology <em>Ontology</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ontology</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceInterface#getOntology()
	 * @see #getServiceInterface()
	 * @generated
	 */
	EReference getServiceInterface_Ontology();

	/**
	 * Returns the meta object for the attribute '{@link org.universaal.tools.modelling.servicemodel.ServiceInterface#getPackageName <em>Package Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package Name</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceInterface#getPackageName()
	 * @see #getServiceInterface()
	 * @generated
	 */
	EAttribute getServiceInterface_PackageName();

	/**
	 * Returns the meta object for class '{@link org.universaal.tools.modelling.servicemodel.ServiceOperation <em>Service Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Operation</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceOperation
	 * @generated
	 */
	EClass getServiceOperation();

	/**
	 * Returns the meta object for the containment reference list '{@link org.universaal.tools.modelling.servicemodel.ServiceOperation#getInput <em>Input</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Input</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceOperation#getInput()
	 * @see #getServiceOperation()
	 * @generated
	 */
	EReference getServiceOperation_Input();

	/**
	 * Returns the meta object for the containment reference list '{@link org.universaal.tools.modelling.servicemodel.ServiceOperation#getOutput <em>Output</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Output</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceOperation#getOutput()
	 * @see #getServiceOperation()
	 * @generated
	 */
	EReference getServiceOperation_Output();

	/**
	 * Returns the meta object for the containment reference list '{@link org.universaal.tools.modelling.servicemodel.ServiceOperation#getEffects <em>Effects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Effects</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceOperation#getEffects()
	 * @see #getServiceOperation()
	 * @generated
	 */
	EReference getServiceOperation_Effects();

	/**
	 * Returns the meta object for class '{@link org.universaal.tools.modelling.servicemodel.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the containment reference '{@link org.universaal.tools.modelling.servicemodel.Parameter#getPropertyPath <em>Property Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Path</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.Parameter#getPropertyPath()
	 * @see #getParameter()
	 * @generated
	 */
	EReference getParameter_PropertyPath();

	/**
	 * Returns the meta object for class '{@link org.universaal.tools.modelling.servicemodel.ServiceEffect <em>Service Effect</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Effect</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceEffect
	 * @generated
	 */
	EClass getServiceEffect();

	/**
	 * Returns the meta object for the attribute '{@link org.universaal.tools.modelling.servicemodel.ServiceEffect#getEffectType <em>Effect Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Effect Type</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceEffect#getEffectType()
	 * @see #getServiceEffect()
	 * @generated
	 */
	EAttribute getServiceEffect_EffectType();

	/**
	 * Returns the meta object for the attribute '{@link org.universaal.tools.modelling.servicemodel.ServiceEffect#getEffectValue <em>Effect Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Effect Value</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceEffect#getEffectValue()
	 * @see #getServiceEffect()
	 * @generated
	 */
	EAttribute getServiceEffect_EffectValue();

	/**
	 * Returns the meta object for the containment reference '{@link org.universaal.tools.modelling.servicemodel.ServiceEffect#getPropertyPath <em>Property Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Path</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.ServiceEffect#getPropertyPath()
	 * @see #getServiceEffect()
	 * @generated
	 */
	EReference getServiceEffect_PropertyPath();

	/**
	 * Returns the meta object for class '{@link org.universaal.tools.modelling.servicemodel.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.universaal.tools.modelling.servicemodel.NamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link org.universaal.tools.modelling.servicemodel.PropertyPath <em>Property Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Path</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.PropertyPath
	 * @generated
	 */
	EClass getPropertyPath();

	/**
	 * Returns the meta object for the reference list '{@link org.universaal.tools.modelling.servicemodel.PropertyPath#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Properties</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.PropertyPath#getProperties()
	 * @see #getPropertyPath()
	 * @generated
	 */
	EReference getPropertyPath_Properties();

	/**
	 * Returns the meta object for enum '{@link org.universaal.tools.modelling.servicemodel.EffectType <em>Effect Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Effect Type</em>'.
	 * @see org.universaal.tools.modelling.servicemodel.EffectType
	 * @generated
	 */
	EEnum getEffectType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ServiceModelFactory getServiceModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.universaal.tools.modelling.servicemodel.impl.ServiceInterfaceImpl <em>Service Interface</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceInterfaceImpl
		 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl#getServiceInterface()
		 * @generated
		 */
		EClass SERVICE_INTERFACE = eINSTANCE.getServiceInterface();

		/**
		 * The meta object literal for the '<em><b>Operations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_INTERFACE__OPERATIONS = eINSTANCE.getServiceInterface_Operations();

		/**
		 * The meta object literal for the '<em><b>Service</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_INTERFACE__SERVICE = eINSTANCE.getServiceInterface_Service();

		/**
		 * The meta object literal for the '<em><b>Ontology</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_INTERFACE__ONTOLOGY = eINSTANCE.getServiceInterface_Ontology();

		/**
		 * The meta object literal for the '<em><b>Package Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE_INTERFACE__PACKAGE_NAME = eINSTANCE.getServiceInterface_PackageName();

		/**
		 * The meta object literal for the '{@link org.universaal.tools.modelling.servicemodel.impl.ServiceOperationImpl <em>Service Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceOperationImpl
		 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl#getServiceOperation()
		 * @generated
		 */
		EClass SERVICE_OPERATION = eINSTANCE.getServiceOperation();

		/**
		 * The meta object literal for the '<em><b>Input</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_OPERATION__INPUT = eINSTANCE.getServiceOperation_Input();

		/**
		 * The meta object literal for the '<em><b>Output</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_OPERATION__OUTPUT = eINSTANCE.getServiceOperation_Output();

		/**
		 * The meta object literal for the '<em><b>Effects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_OPERATION__EFFECTS = eINSTANCE.getServiceOperation_Effects();

		/**
		 * The meta object literal for the '{@link org.universaal.tools.modelling.servicemodel.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.universaal.tools.modelling.servicemodel.impl.ParameterImpl
		 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Property Path</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER__PROPERTY_PATH = eINSTANCE.getParameter_PropertyPath();

		/**
		 * The meta object literal for the '{@link org.universaal.tools.modelling.servicemodel.impl.ServiceEffectImpl <em>Service Effect</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceEffectImpl
		 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl#getServiceEffect()
		 * @generated
		 */
		EClass SERVICE_EFFECT = eINSTANCE.getServiceEffect();

		/**
		 * The meta object literal for the '<em><b>Effect Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE_EFFECT__EFFECT_TYPE = eINSTANCE.getServiceEffect_EffectType();

		/**
		 * The meta object literal for the '<em><b>Effect Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE_EFFECT__EFFECT_VALUE = eINSTANCE.getServiceEffect_EffectValue();

		/**
		 * The meta object literal for the '<em><b>Property Path</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_EFFECT__PROPERTY_PATH = eINSTANCE.getServiceEffect_PropertyPath();

		/**
		 * The meta object literal for the '{@link org.universaal.tools.modelling.servicemodel.impl.NamedElementImpl <em>Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.universaal.tools.modelling.servicemodel.impl.NamedElementImpl
		 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

		/**
		 * The meta object literal for the '{@link org.universaal.tools.modelling.servicemodel.impl.PropertyPathImpl <em>Property Path</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.universaal.tools.modelling.servicemodel.impl.PropertyPathImpl
		 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl#getPropertyPath()
		 * @generated
		 */
		EClass PROPERTY_PATH = eINSTANCE.getPropertyPath();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_PATH__PROPERTIES = eINSTANCE.getPropertyPath_Properties();

		/**
		 * The meta object literal for the '{@link org.universaal.tools.modelling.servicemodel.EffectType <em>Effect Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.universaal.tools.modelling.servicemodel.EffectType
		 * @see org.universaal.tools.modelling.servicemodel.impl.ServiceModelPackageImpl#getEffectType()
		 * @generated
		 */
		EEnum EFFECT_TYPE = eINSTANCE.getEffectType();

	}

} //ServiceModelPackage
