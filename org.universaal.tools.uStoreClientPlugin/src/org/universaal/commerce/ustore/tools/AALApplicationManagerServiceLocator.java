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

public class AALApplicationManagerServiceLocator extends org.apache.axis.client.Service implements org.universaal.commerce.ustore.tools.AALApplicationManagerService {

    public AALApplicationManagerServiceLocator() {
    }


    public AALApplicationManagerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AALApplicationManagerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AALApplicationManagerPort
    private java.lang.String AALApplicationManagerPort_address = "http://srv-ustore.haifa.il.ibm.com:9060/universAAL/AALApplicationManagerService";

    public java.lang.String getAALApplicationManagerPortAddress() {
        return AALApplicationManagerPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AALApplicationManagerPortWSDDServiceName = "AALApplicationManagerPort";

    public java.lang.String getAALApplicationManagerPortWSDDServiceName() {
        return AALApplicationManagerPortWSDDServiceName;
    }

    public void setAALApplicationManagerPortWSDDServiceName(java.lang.String name) {
        AALApplicationManagerPortWSDDServiceName = name;
    }

    public org.universaal.commerce.ustore.tools.AALApplicationManager getAALApplicationManagerPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AALApplicationManagerPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAALApplicationManagerPort(endpoint);
    }

    public org.universaal.commerce.ustore.tools.AALApplicationManager getAALApplicationManagerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.universaal.commerce.ustore.tools.AALApplicationManagerPortBindingStub _stub = new org.universaal.commerce.ustore.tools.AALApplicationManagerPortBindingStub(portAddress, this);
            _stub.setPortName(getAALApplicationManagerPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAALApplicationManagerPortEndpointAddress(java.lang.String address) {
        AALApplicationManagerPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.universaal.commerce.ustore.tools.AALApplicationManager.class.isAssignableFrom(serviceEndpointInterface)) {
                org.universaal.commerce.ustore.tools.AALApplicationManagerPortBindingStub _stub = new org.universaal.commerce.ustore.tools.AALApplicationManagerPortBindingStub(new java.net.URL(AALApplicationManagerPort_address), this);
                _stub.setPortName(getAALApplicationManagerPortWSDDServiceName());
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
        if ("AALApplicationManagerPort".equals(inputPortName)) {
            return getAALApplicationManagerPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tools.ustore.commerce.universaal.org/", "AALApplicationManagerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tools.ustore.commerce.universaal.org/", "AALApplicationManagerPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AALApplicationManagerPort".equals(portName)) {
            setAALApplicationManagerPortEndpointAddress(address);
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
