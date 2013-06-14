package org.universAAL.commerce.ustore.tools;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.8
 * 2013-06-14T10:17:15.448+02:00
 * Generated source version: 2.6.8
 * 
 */
@WebService(targetNamespace = "http://tools.ustore.commerce.universaal.org/", name = "OnlineStoreManager")
@XmlSeeAlso({ObjectFactory.class})
public interface OnlineStoreManager {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getFreeAALServices", targetNamespace = "http://tools.ustore.commerce.universaal.org/", className = "org.universaal.commerce.ustore.tools.GetFreeAALServices")
    @WebMethod
    @ResponseWrapper(localName = "getFreeAALServicesResponse", targetNamespace = "http://tools.ustore.commerce.universaal.org/", className = "org.universaal.commerce.ustore.tools.GetFreeAALServicesResponse")
    public java.lang.String getFreeAALServices(
        @WebParam(name = "sessionKey", targetNamespace = "")
        java.lang.String sessionKey,
        @WebParam(name = "isFitToUser", targetNamespace = "")
        boolean isFitToUser
    ) throws UAALException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUpdatesForAALServices", targetNamespace = "http://tools.ustore.commerce.universaal.org/", className = "org.universaal.commerce.ustore.tools.GetUpdatesForAALServices")
    @WebMethod
    @ResponseWrapper(localName = "getUpdatesForAALServicesResponse", targetNamespace = "http://tools.ustore.commerce.universaal.org/", className = "org.universaal.commerce.ustore.tools.GetUpdatesForAALServicesResponse")
    public java.lang.String getUpdatesForAALServices(
        @WebParam(name = "sessionKey", targetNamespace = "")
        java.lang.String sessionKey
    ) throws UAALException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUserProfile", targetNamespace = "http://tools.ustore.commerce.universaal.org/", className = "org.universaal.commerce.ustore.tools.GetUserProfile")
    @WebMethod
    @ResponseWrapper(localName = "getUserProfileResponse", targetNamespace = "http://tools.ustore.commerce.universaal.org/", className = "org.universaal.commerce.ustore.tools.GetUserProfileResponse")
    public java.lang.String getUserProfile(
        @WebParam(name = "sessionKey", targetNamespace = "")
        java.lang.String sessionKey
    ) throws UAALException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getPurchasedAALServices", targetNamespace = "http://tools.ustore.commerce.universaal.org/", className = "org.universaal.commerce.ustore.tools.GetPurchasedAALServices")
    @WebMethod
    @ResponseWrapper(localName = "getPurchasedAALServicesResponse", targetNamespace = "http://tools.ustore.commerce.universaal.org/", className = "org.universaal.commerce.ustore.tools.GetPurchasedAALServicesResponse")
    public java.lang.String getPurchasedAALServices(
        @WebParam(name = "sessionKey", targetNamespace = "")
        java.lang.String sessionKey
    ) throws UAALException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getSessionKey", targetNamespace = "http://tools.ustore.commerce.universaal.org/", className = "org.universaal.commerce.ustore.tools.GetSessionKey")
    @WebMethod
    @ResponseWrapper(localName = "getSessionKeyResponse", targetNamespace = "http://tools.ustore.commerce.universaal.org/", className = "org.universaal.commerce.ustore.tools.GetSessionKeyResponse")
    public java.lang.String getSessionKey(
        @WebParam(name = "userName", targetNamespace = "")
        java.lang.String userName,
        @WebParam(name = "password", targetNamespace = "")
        java.lang.String password
    ) throws UAALException_Exception;

    @RequestWrapper(localName = "registerDeployManager", targetNamespace = "http://tools.ustore.commerce.universaal.org/", className = "org.universaal.commerce.ustore.tools.RegisterDeployManager")
    @WebMethod
    @ResponseWrapper(localName = "registerDeployManagerResponse", targetNamespace = "http://tools.ustore.commerce.universaal.org/", className = "org.universaal.commerce.ustore.tools.RegisterDeployManagerResponse")
    public void registerDeployManager(
        @WebParam(name = "sessionKey", targetNamespace = "")
        java.lang.String sessionKey,
        @WebParam(name = "adminUserName", targetNamespace = "")
        java.lang.String adminUserName,
        @WebParam(name = "adminPassword", targetNamespace = "")
        java.lang.String adminPassword,
        @WebParam(name = "ipAddress", targetNamespace = "")
        java.lang.String ipAddress,
        @WebParam(name = "port", targetNamespace = "")
        java.lang.String port
    ) throws UAALException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "purchaseFreeAALService", targetNamespace = "http://tools.ustore.commerce.universaal.org/", className = "org.universaal.commerce.ustore.tools.PurchaseFreeAALService")
    @WebMethod
    @ResponseWrapper(localName = "purchaseFreeAALServiceResponse", targetNamespace = "http://tools.ustore.commerce.universaal.org/", className = "org.universaal.commerce.ustore.tools.PurchaseFreeAALServiceResponse")
    public java.lang.String purchaseFreeAALService(
        @WebParam(name = "sessionKey", targetNamespace = "")
        java.lang.String sessionKey,
        @WebParam(name = "serviceId", targetNamespace = "")
        java.lang.String serviceId
    ) throws UAALException_Exception;
}
