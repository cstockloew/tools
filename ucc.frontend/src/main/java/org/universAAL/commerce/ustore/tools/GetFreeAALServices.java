
package org.universaal.commerce.ustore.tools;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für getFreeAALServices complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="getFreeAALServices">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sessionKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isFitToUser" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getFreeAALServices", propOrder = {
    "sessionKey",
    "isFitToUser"
})
public class GetFreeAALServices {

    protected String sessionKey;
    protected boolean isFitToUser;

    /**
     * Ruft den Wert der sessionKey-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * Legt den Wert der sessionKey-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionKey(String value) {
        this.sessionKey = value;
    }

    /**
     * Ruft den Wert der isFitToUser-Eigenschaft ab.
     * 
     */
    public boolean isIsFitToUser() {
        return isFitToUser;
    }

    /**
     * Legt den Wert der isFitToUser-Eigenschaft fest.
     * 
     */
    public void setIsFitToUser(boolean value) {
        this.isFitToUser = value;
    }

}
