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
  
  public java.lang.String addApplication(java.lang.String username, java.lang.String password, java.lang.String applicationname, java.lang.String shortdescription, java.lang.String longdescription, java.lang.String keywords, java.lang.String manufacturer, java.lang.String manufacturerpartnumber, java.lang.String applicationURL, java.lang.String parentcategoryid, byte[] fullimage, byte[] thumbnailimage, java.lang.String listprice, java.lang.String groupid, java.lang.String artifactid, java.lang.String version, java.lang.String filename, byte[] fileinputstream, java.lang.String hardwarereq, java.lang.String softwarereq, java.lang.String developercontactdetails, java.util.Calendar uploadtimetonexus) throws java.rmi.RemoteException, org.universaal.commerce.ustore.tools.UAALException{
    if (aALApplicationManager == null)
      _initAALApplicationManagerProxy();
    return aALApplicationManager.addApplication(username, password, applicationname, shortdescription, longdescription, keywords, manufacturer, manufacturerpartnumber, applicationURL, parentcategoryid, fullimage, thumbnailimage, listprice, groupid, artifactid, version, filename, fileinputstream, hardwarereq, softwarereq, developercontactdetails, uploadtimetonexus);
  }
  
  public void updateApplication(java.lang.String username, java.lang.String password, java.lang.String applicationid, java.lang.String applicationname, java.lang.String shortdescription, java.lang.String longdescription, java.lang.String keywords, java.lang.String manufacturer, java.lang.String manufacturerpartnumber, java.lang.String applicationURL, java.lang.String parentcategoryid, byte[] fullimage, byte[] thumbnailimage, java.lang.String listprice, java.lang.String groupid, java.lang.String artifactid, java.lang.String version, java.lang.String filename, byte[] fileinputstream, java.lang.String hardwarereq, java.lang.String softwarereq, java.lang.String developercontactdetails, java.util.Calendar uploadtimetonexus) throws java.rmi.RemoteException, org.universaal.commerce.ustore.tools.UAALException{
    if (aALApplicationManager == null)
      _initAALApplicationManagerProxy();
    aALApplicationManager.updateApplication(username, password, applicationid, applicationname, shortdescription, longdescription, keywords, manufacturer, manufacturerpartnumber, applicationURL, parentcategoryid, fullimage, thumbnailimage, listprice, groupid, artifactid, version, filename, fileinputstream, hardwarereq, softwarereq, developercontactdetails, uploadtimetonexus);
  }
  
  public void deleteApplication(java.lang.String username, java.lang.String password, java.lang.String applicationid) throws java.rmi.RemoteException, org.universaal.commerce.ustore.tools.UAALException{
    if (aALApplicationManager == null)
      _initAALApplicationManagerProxy();
    aALApplicationManager.deleteApplication(username, password, applicationid);
  }
  
  
}