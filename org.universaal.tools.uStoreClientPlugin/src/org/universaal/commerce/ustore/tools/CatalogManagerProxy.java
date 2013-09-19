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

public class CatalogManagerProxy implements org.universaal.commerce.ustore.tools.CatalogManager {
  private String _endpoint = null;
  private org.universaal.commerce.ustore.tools.CatalogManager catalogManager = null;
  
  public CatalogManagerProxy() {
    _initCatalogManagerProxy();
  }
  
  public CatalogManagerProxy(String endpoint) {
    _endpoint = endpoint;
    _initCatalogManagerProxy();
  }
  
  private void _initCatalogManagerProxy() {
    try {
      catalogManager = (new org.universaal.commerce.ustore.tools.CatalogManagerServiceLocator()).getCatalogManagerPort();
      if (catalogManager != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)catalogManager)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)catalogManager)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (catalogManager != null)
      ((javax.xml.rpc.Stub)catalogManager)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.universaal.commerce.ustore.tools.CatalogManager getCatalogManager() {
    if (catalogManager == null)
      _initCatalogManagerProxy();
    return catalogManager;
  }
  
  public java.lang.String getAALApplicationsCategories(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException, org.universaal.commerce.ustore.tools.UAALException{
    if (catalogManager == null)
      _initCatalogManagerProxy();
    return catalogManager.getAALApplicationsCategories(username, password);
  }
  
  
}