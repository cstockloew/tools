/**
 * AALApplicationManager.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.universaal.commerce.ustore.tools;

public interface AALApplicationManager extends java.rmi.Remote {
    public java.lang.String addApplication(java.lang.String username, java.lang.String password, java.lang.String applicationname, java.lang.String shortdescription, java.lang.String longdescription, java.lang.String keywords, java.lang.String manufacturer, java.lang.String manufacturerpartnumber, java.lang.String applicationURL, java.lang.String parentcategoryid, byte[] fullimage, byte[] thumbnailimage, java.lang.String listprice, java.lang.String groupid, java.lang.String artifactid, java.lang.String version, java.lang.String filename, byte[] fileinputstream, java.lang.String hardwarereq, java.lang.String softwarereq, java.lang.String developercontactdetails, java.util.Calendar uploadtimetonexus) throws java.rmi.RemoteException, org.universaal.commerce.ustore.tools.UAALException;
    public void updateApplication(java.lang.String username, java.lang.String password, java.lang.String applicationid, java.lang.String applicationname, java.lang.String shortdescription, java.lang.String longdescription, java.lang.String keywords, java.lang.String manufacturer, java.lang.String manufacturerpartnumber, java.lang.String applicationURL, java.lang.String parentcategoryid, byte[] fullimage, byte[] thumbnailimage, java.lang.String listprice, java.lang.String groupid, java.lang.String artifactid, java.lang.String version, java.lang.String filename, byte[] fileinputstream, java.lang.String hardwarereq, java.lang.String softwarereq, java.lang.String developercontactdetails, java.util.Calendar uploadtimetonexus) throws java.rmi.RemoteException, org.universaal.commerce.ustore.tools.UAALException;
    public void deleteApplication(java.lang.String username, java.lang.String password, java.lang.String applicationid) throws java.rmi.RemoteException, org.universaal.commerce.ustore.tools.UAALException;
}
