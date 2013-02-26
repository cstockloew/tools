/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.universaal.tools.modelling.servicemodel.util;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

import org.universaal.tools.modelling.servicemodel.ServiceModelPackage;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ServiceModelXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServiceModelXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		ServiceModelPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the ServiceModelResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new ServiceModelResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new ServiceModelResourceFactoryImpl());
		}
		return registrations;
	}

} //ServiceModelXMLProcessor
