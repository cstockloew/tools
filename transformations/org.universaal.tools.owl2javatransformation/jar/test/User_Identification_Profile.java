
/*
	Copyright 2008-2014 Forschungszentrum Informatik FZI, http://www.fzi.de
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressed or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
 
package org.universAAL.ontology.test;

import org.universAAL.middleware.rdf.*;
import org.universAAL.middleware.owl.*;

/**
 * This class represents an ontological concept.
 * <br> <br> 
 * 
 * <br> <br> 
 * This class was generated at $now by velocity.
 */

public class User_Identification_Profile extends Sub_Profile {

	/** Class URI */
	public static final String MY_URI = "http://ontology.universAAL.org/Profile.owl#User-Identification-Profile";
	
    /** http://ontology.universAAL.org/Profile.owl#email */
	public static final String PROP_EMAIL = "http://ontology.universAAL.org/Profile.owl#email";
    /** http://ontology.universAAL.org/Profile.owl#name */
	public static final String PROP_NAME = "http://ontology.universAAL.org/Profile.owl#name";
    /** http://ontology.universAAL.org/Profile.owl#mobile-phone-number */
	public static final String PROP_MOBILE_PHONE_NUMBER = "http://ontology.universAAL.org/Profile.owl#mobile-phone-number";
	
	static {
		register(User_Identification_Profile.class);
    }
    
    public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (PROP_EMAIL.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI,
				 TypeMapper.getDatatypeURI(String.class), 1, 0);
		if (PROP_NAME.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI,
				 TypeMapper.getDatatypeURI(String.class), 1, 0);
		if (PROP_MOBILE_PHONE_NUMBER.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI,
				 TypeMapper.getDatatypeURI(String.class), 1, 0);
		return Sub_Profile.getClassRestrictionsOnProperty(propURI);
    }
 
    public static String[] getStandardPropertyURIs() {
		return new String[] {
					PROP_EMAIL,
					PROP_NAME,
					PROP_MOBILE_PHONE_NUMBER 
		};
	}
 	
	/**
     * Returns a human readable description on the essence of this ontology
     * class.
     */
    public static String getRDFSComment() {
		return "";
    }
    
    /**
     * Returns a label with which this ontology class can be introduced to human
     * users.
     */
    public static String getRDFSLabel() {
		return "User_Identification_Profile";
    }
    
    protected User_Identification_Profile() {
		super();
    }

    public User_Identification_Profile(String uri) {
		super(uri);
    }

    protected User_Identification_Profile(String uriPrefix, int numProps) {
		super(uriPrefix, numProps);
    }
		
	public boolean isWellFormed() {
		return true;
    }
    
    public int getPropSerializationType(String propURI) {
		return PROP_SERIALIZATION_FULL;
    }
    
    public void setEMAIL(String value) {
	    super.setProperty(PROP_EMAIL , value);
    }
    
    public String getEMAIL() {
    	return (String) props.get(PROP_EMAIL );
    }
    
    public void setNAME(String value) {
	    super.setProperty(PROP_NAME , value);
    }
    
    public String getNAME() {
    	return (String) props.get(PROP_NAME );
    }
    
    public void setMOBILE_PHONE_NUMBER(String value) {
	    super.setProperty(PROP_MOBILE_PHONE_NUMBER , value);
    }
    
    public String getMOBILE_PHONE_NUMBER() {
    	return (String) props.get(PROP_MOBILE_PHONE_NUMBER );
    }
    
    
    

}
