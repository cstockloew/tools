//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.17 at 11:54:47 PM CEST 
//


package org.universAAL.ucc.model.usrv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * describes a simple requirement
 * 
 * <p>Java class for reqAtomType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reqAtomType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reqAtomName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reqAtomValue" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="reqCriteria" type="{http://www.universaal.org/aal-uapp/v1.0.0}logicalCriteriaType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reqAtomType", propOrder = {
    "reqAtomName",
    "reqAtomValue",
    "reqCriteria"
})
public class ReqAtomType
    implements Serializable
{

    private final static long serialVersionUID = 12343L;
    @XmlElement(required = true)
    protected String reqAtomName;
    @XmlElement(required = true)
    protected List<String> reqAtomValue;
    @XmlElement(defaultValue = "equal")
    protected LogicalCriteriaType reqCriteria;

    /**
     * Gets the value of the reqAtomName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqAtomName() {
        return reqAtomName;
    }

    /**
     * Sets the value of the reqAtomName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqAtomName(String value) {
        this.reqAtomName = value;
    }

    public boolean isSetReqAtomName() {
        return (this.reqAtomName!= null);
    }

    /**
     * Gets the value of the reqAtomValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reqAtomValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReqAtomValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getReqAtomValue() {
        if (reqAtomValue == null) {
            reqAtomValue = new ArrayList<String>();
        }
        return this.reqAtomValue;
    }

    public boolean isSetReqAtomValue() {
        return ((this.reqAtomValue!= null)&&(!this.reqAtomValue.isEmpty()));
    }

    public void unsetReqAtomValue() {
        this.reqAtomValue = null;
    }

    /**
     * Gets the value of the reqCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link LogicalCriteriaType }
     *     
     */
    public LogicalCriteriaType getReqCriteria() {
        return reqCriteria;
    }

    /**
     * Sets the value of the reqCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link LogicalCriteriaType }
     *     
     */
    public void setReqCriteria(LogicalCriteriaType value) {
        this.reqCriteria = value;
    }

    public boolean isSetReqCriteria() {
        return (this.reqCriteria!= null);
    }

}
