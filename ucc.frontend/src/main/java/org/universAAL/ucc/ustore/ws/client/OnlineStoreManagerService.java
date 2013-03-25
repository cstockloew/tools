
package org.universAAL.ucc.ustore.ws.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "OnlineStoreManagerService", targetNamespace = "http://tools.ustore.commerce.universaal.org/", wsdlLocation = "http://srv-ustore.haifa.il.ibm.com:9060/universAAL/OnlineStoreManagerService/OnlineStoreManagerService.wsdl")
public class OnlineStoreManagerService
    extends Service
{

    private final static URL ONLINESTOREMANAGERSERVICE_WSDL_LOCATION;
    private final static WebServiceException ONLINESTOREMANAGERSERVICE_EXCEPTION;
    private final static QName ONLINESTOREMANAGERSERVICE_QNAME = new QName("http://tools.ustore.commerce.universaal.org/", "OnlineStoreManagerService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://srv-ustore.haifa.il.ibm.com:9060/universAAL/OnlineStoreManagerService/OnlineStoreManagerService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ONLINESTOREMANAGERSERVICE_WSDL_LOCATION = url;
        ONLINESTOREMANAGERSERVICE_EXCEPTION = e;
    }

    public OnlineStoreManagerService() {
        super(__getWsdlLocation(), ONLINESTOREMANAGERSERVICE_QNAME);
    }

    public OnlineStoreManagerService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ONLINESTOREMANAGERSERVICE_QNAME, features);
    }

    public OnlineStoreManagerService(URL wsdlLocation) {
        super(wsdlLocation, ONLINESTOREMANAGERSERVICE_QNAME);
    }

    public OnlineStoreManagerService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ONLINESTOREMANAGERSERVICE_QNAME, features);
    }

    public OnlineStoreManagerService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OnlineStoreManagerService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns OnlineStoreManager
     */
    @WebEndpoint(name = "OnlineStoreManagerPort")
    public OnlineStoreManager getOnlineStoreManagerPort() {
        return super.getPort(new QName("http://tools.ustore.commerce.universaal.org/", "OnlineStoreManagerPort"), OnlineStoreManager.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OnlineStoreManager
     */
    @WebEndpoint(name = "OnlineStoreManagerPort")
    public OnlineStoreManager getOnlineStoreManagerPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://tools.ustore.commerce.universaal.org/", "OnlineStoreManagerPort"), OnlineStoreManager.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ONLINESTOREMANAGERSERVICE_EXCEPTION!= null) {
            throw ONLINESTOREMANAGERSERVICE_EXCEPTION;
        }
        return ONLINESTOREMANAGERSERVICE_WSDL_LOCATION;
    }

}
