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

public class AALApplicationManagerProxy implements org.universaal.commerce.ustore.tools.AALApplicationManager {
  private String _endpoint = null;
  private org.universaal.commerce.ustore.tools.AALApplicationManager aALApplicationManager = null;
  
  public AALApplicationManagerProxy() {
    _initAALApplicationManagerProxy();
  }
  
  public AALApplicationManagerProxy(String endpoint) {
    _endpoint = endpoint;
    _initAALApplicationManagerProxy();
  }
  
  private void _initAALApplicationManagerProxy() {
    try {
      aALApplicationManager = (new org.universaal.commerce.ustore.tools.AALApplicationManagerServiceLocator()).getAALApplicationManagerPort();
      if (aALApplicationManager != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)aALApplicationManager)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)aALApplicationManager)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (aALApplicationManager != null)
      ((javax.xml.rpc.Stub)aALApplicationManager)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.universaal.commerce.ustore.tools.AALApplicationManager getAALApplicationManager() {
    if (aALApplicationManager == null)
      _initAALApplicationManagerProxy();
    return aALApplicationManager;
  }
  
  public java.lang.String uploadAnyAALApplication(java.lang.String username, java.lang.String password, java.lang.String applicationid, java.lang.String applicationname, java.lang.String shortdescription, java.lang.String longdescription, java.lang.String keywords, java.lang.String developername, java.lang.String developeremail, java.lang.String developerphone, java.lang.String organizationname, java.lang.String organizationURL, java.lang.String organizationcertificate, java.lang.String applicationURL, java.lang.String parentcategoryid, java.lang.String fullimagefilename, byte[] fullimage, java.lang.String thumbnailimagefilename, byte[] thumbnailimage, java.lang.String listprice, java.lang.String version, java.lang.String versionnotes, java.lang.String filename, byte[] fileinputstream, java.lang.String servicelevelagreement, java.lang.String requirements, java.lang.String licenses, java.lang.String capabilities, boolean isforpurchase) throws java.rmi.RemoteException, org.universaal.commerce.ustore.tools.UAALException{
    if (aALApplicationManager == null)
      _initAALApplicationManagerProxy();
    return aALApplicationManager.uploadAnyAALApplication(username, password, applicationid, applicationname, shortdescription, longdescription, keywords, developername, developeremail, developerphone, organizationname, organizationURL, organizationcertificate, applicationURL, parentcategoryid, fullimagefilename, fullimage, thumbnailimagefilename, thumbnailimage, listprice, version, versionnotes, filename, fileinputstream, servicelevelagreement, requirements, licenses, capabilities, isforpurchase);
  }
  
  public java.lang.String uploadUaapAALApplication(java.lang.String username, java.lang.String password, java.lang.String applicationid, java.lang.String longdescription, java.lang.String applicationURL, java.lang.String parentcategoryid, java.lang.String fullimagefilename, byte[] fullimage, java.lang.String thumbnailimagefilename, byte[] thumbnailimage, java.lang.String listprice, java.lang.String version, java.lang.String versionnotes, java.lang.String filename, byte[] fileinputstream, boolean isforpurchase) throws java.rmi.RemoteException, org.universaal.commerce.ustore.tools.UAALException{
    if (aALApplicationManager == null)
      _initAALApplicationManagerProxy();
    return aALApplicationManager.uploadUaapAALApplication(username, password, applicationid, longdescription, applicationURL, parentcategoryid, fullimagefilename, fullimage, thumbnailimagefilename, thumbnailimage, listprice, version, versionnotes, filename, fileinputstream, isforpurchase);
  }
  
  public void deleteAALApplication(java.lang.String username, java.lang.String password, java.lang.String applicationid) throws java.rmi.RemoteException, org.universaal.commerce.ustore.tools.UAALException{
    if (aALApplicationManager == null)
      _initAALApplicationManagerProxy();
    aALApplicationManager.deleteAALApplication(username, password, applicationid);
  }
  
  public java.lang.String getAALApplications(java.lang.String username, java.lang.String password, java.lang.String parentcategoryid) throws java.rmi.RemoteException, org.universaal.commerce.ustore.tools.UAALException{
    if (aALApplicationManager == null)
      _initAALApplicationManagerProxy();
    return aALApplicationManager.getAALApplications(username, password, parentcategoryid);
  }
  
  
}