/**
 * OnlineStoreManager.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.universAAL.ucc.ustore.ws.client;

public interface OnlineStoreManager extends java.rmi.Remote {
    public java.lang.String registerDeployManager(java.lang.String userName, java.lang.String password, java.lang.String ipAddress, java.lang.String port) throws java.rmi.RemoteException, UAALException;
    public java.lang.String getUserProfile(java.lang.String sessionKey) throws java.rmi.RemoteException, UAALException;
    public java.lang.String getFreeAALServices(java.lang.String sessionKey, boolean isFitToUser) throws java.rmi.RemoteException, UAALException;
    public java.lang.String purchaseFreeAALService(java.lang.String sessionKey, java.lang.String serviceId) throws java.rmi.RemoteException, UAALException;
    public java.lang.String getPurchasedAALServices(java.lang.String sessionKey) throws java.rmi.RemoteException, UAALException;
}
