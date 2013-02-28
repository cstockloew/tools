//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.02.25 at 04:00:09 PM CET 
//


package org.universAAL.ucc.core.usrv;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for licenseCategoryType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="licenseCategoryType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ApplicationLicense"/>
 *     &lt;enumeration value="ExternalLicense"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "licenseCategoryType")
@XmlEnum
public enum LicenseCategoryType {

    @XmlEnumValue("ApplicationLicense")
    APPLICATION_LICENSE("ApplicationLicense"),
    @XmlEnumValue("ExternalLicense")
    EXTERNAL_LICENSE("ExternalLicense");
    private final String value;

    LicenseCategoryType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LicenseCategoryType fromValue(String v) {
        for (LicenseCategoryType c: LicenseCategoryType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
