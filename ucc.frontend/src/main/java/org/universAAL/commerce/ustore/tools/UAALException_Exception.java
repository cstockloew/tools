
package org.universaal.commerce.ustore.tools;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.6.8
 * 2013-06-14T10:17:15.417+02:00
 * Generated source version: 2.6.8
 */

@WebFault(name = "uAALException", targetNamespace = "http://tools.ustore.commerce.universaal.org/")
public class UAALException_Exception extends Exception {
    
    private org.universaal.commerce.ustore.tools.UAALException uAALException;

    public UAALException_Exception() {
        super();
    }
    
    public UAALException_Exception(String message) {
        super(message);
    }
    
    public UAALException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public UAALException_Exception(String message, org.universaal.commerce.ustore.tools.UAALException uAALException) {
        super(message);
        this.uAALException = uAALException;
    }

    public UAALException_Exception(String message, org.universaal.commerce.ustore.tools.UAALException uAALException, Throwable cause) {
        super(message, cause);
        this.uAALException = uAALException;
    }

    public org.universaal.commerce.ustore.tools.UAALException getFaultInfo() {
        return this.uAALException;
    }
}
