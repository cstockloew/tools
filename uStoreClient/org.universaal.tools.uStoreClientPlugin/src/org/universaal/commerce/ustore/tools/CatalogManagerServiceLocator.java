/*
	Copyright 2011-2014 CERTH-ITI, http://www.iti.gr
	Information Technologies Institute (ITI)
	Centre For Research and Technology Hellas (CERTH)
	
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

package org.universaal.commerce.ustore.tools;

public class CatalogManagerServiceLocator extends org.apache.axis.client.Service implements org.universaal.commerce.ustore.tools.CatalogManagerService {

    public CatalogManagerServiceLocator() {
    }


    public CatalogManagerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CatalogManagerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CatalogManagerPort
    private java.lang.String CatalogManagerPort_address = "http://srv-ustore.haifa.il.ibm.com:9060/universAAL/CatalogManagerService";

    public java.lang.String getCatalogManagerPortAddress() {
        return CatalogManagerPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CatalogManagerPortWSDDServiceName = "CatalogManagerPort";

    public java.lang.String getCatalogManagerPortWSDDServiceName() {
        return CatalogManagerPortWSDDServiceName;
    }

    public void setCatalogManagerPortWSDDServiceName(java.lang.String name) {
        CatalogManagerPortWSDDServiceName = name;
    }

    public org.universaal.commerce.ustore.tools.CatalogManager getCatalogManagerPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CatalogManagerPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCatalogManagerPort(endpoint);
    }

    public org.universaal.commerce.ustore.tools.CatalogManager getCatalogManagerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.universaal.commerce.ustore.tools.CatalogManagerPortBindingStub _stub = new org.universaal.commerce.ustore.tools.CatalogManagerPortBindingStub(portAddress, this);
            _stub.setPortName(getCatalogManagerPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCatalogManagerPortEndpointAddress(java.lang.String address) {
        CatalogManagerPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.universaal.commerce.ustore.tools.CatalogManager.class.isAssignableFrom(serviceEndpointInterface)) {
                org.universaal.commerce.ustore.tools.CatalogManagerPortBindingStub _stub = new org.universaal.commerce.ustore.tools.CatalogManagerPortBindingStub(new java.net.URL(CatalogManagerPort_address), this);
                _stub.setPortName(getCatalogManagerPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CatalogManagerPort".equals(inputPortName)) {
            return getCatalogManagerPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tools.ustore.commerce.universaal.org/", "CatalogManagerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tools.ustore.commerce.universaal.org/", "CatalogManagerPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CatalogManagerPort".equals(portName)) {
            setCatalogManagerPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
