
package com.amss.XMLProjekat.dto.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accommodationId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="restrictionFrom" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="restrictionTo" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "accommodationId",
    "restrictionFrom",
    "restrictionTo"
})
@XmlRootElement(name = "restrictionRequest")
public class RestrictionRequest {

    protected long accommodationId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar restrictionFrom;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar restrictionTo;

    /**
     * Gets the value of the accommodationId property.
     * 
     */
    public long getAccommodationId() {
        return accommodationId;
    }

    /**
     * Sets the value of the accommodationId property.
     * 
     */
    public void setAccommodationId(long value) {
        this.accommodationId = value;
    }

    /**
     * Gets the value of the restrictionFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRestrictionFrom() {
        return restrictionFrom;
    }

    /**
     * Sets the value of the restrictionFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRestrictionFrom(XMLGregorianCalendar value) {
        this.restrictionFrom = value;
    }

    /**
     * Gets the value of the restrictionTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRestrictionTo() {
        return restrictionTo;
    }

    /**
     * Sets the value of the restrictionTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRestrictionTo(XMLGregorianCalendar value) {
        this.restrictionTo = value;
    }

}
