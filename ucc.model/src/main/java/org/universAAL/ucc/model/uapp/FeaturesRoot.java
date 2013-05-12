//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.12 at 11:41:00 AM CEST 
//


package org.universAAL.ucc.model.uapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * Root element of Feature definition. It contains optional attribute which allow
 * name of repository. This name will be used in shell to display source repository
 * of given feature.
 *             
 * 
 * <p>Java class for featuresRoot complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="featuresRoot">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="repository" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="feature" type="{http://karaf.apache.org/xmlns/features/v1.0.0}feature"/>
 *       &lt;/choice>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "featuresRoot", namespace = "http://karaf.apache.org/xmlns/features/v1.0.0", propOrder = {
    "repositoryOrFeature"
})
public class FeaturesRoot
    implements Serializable
{

    private final static long serialVersionUID = 12343L;
    @XmlElements({
        @XmlElement(name = "repository", type = String.class),
        @XmlElement(name = "feature", type = Feature.class)
    })
    protected List<Serializable> repositoryOrFeature;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Gets the value of the repositoryOrFeature property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the repositoryOrFeature property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRepositoryOrFeature().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * {@link Feature }
     * 
     * 
     */
    public List<Serializable> getRepositoryOrFeature() {
        if (repositoryOrFeature == null) {
            repositoryOrFeature = new ArrayList<Serializable>();
        }
        return this.repositoryOrFeature;
    }

    public boolean isSetRepositoryOrFeature() {
        return ((this.repositoryOrFeature!= null)&&(!this.repositoryOrFeature.isEmpty()));
    }

    public void unsetRepositoryOrFeature() {
        this.repositoryOrFeature = null;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    public boolean isSetName() {
        return (this.name!= null);
    }

}