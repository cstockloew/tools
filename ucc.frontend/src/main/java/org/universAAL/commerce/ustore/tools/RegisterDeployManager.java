
package org.universaal.commerce.ustore.tools;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für registerDeployManager complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="registerDeployManager">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sessionKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adminUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adminPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ipAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="port" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registerDeployManager", propOrder = {
    "sessionKey",
    "adminUserName",
    "adminPassword",
    "ipAddress",
    "port"
})
public class RegisterDeployManager {

    protected String sessionKey;
    protected String adminUserName;
    protected String adminPassword;
    protected String ipAddress;
    protected String port;

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
     * Ruft den Wert der adminUserName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdminUserName() {
        return adminUserName;
    }

    /**
     * Legt den Wert der adminUserName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdminUserName(String value) {
        this.adminUserName = value;
    }

    /**
     * Ruft den Wert der adminPassword-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdminPassword() {
        return adminPassword;
    }

    /**
     * Legt den Wert der adminPassword-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdminPassword(String value) {
        this.adminPassword = value;
    }

    /**
     * Ruft den Wert der ipAddress-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Legt den Wert der ipAddress-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpAddress(String value) {
        this.ipAddress = value;
    }

    /**
     * Ruft den Wert der port-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPort() {
        return port;
    }

    /**
     * Legt den Wert der port-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPort(String value) {
        this.port = value;
    }

}
