
package com.amss.XMLProjekat.dto.soap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for reservationView complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reservationView">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="accommodatioName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="registeredUserUsername" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="startingDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="endingDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="confirmed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="messages" type="{http://amss.com/XMLProjekat/dto/soap}messageView" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reservationView", propOrder = {
    "id",
    "accommodatioName",
    "registeredUserUsername",
    "startingDate",
    "endingDate",
    "confirmed",
    "messages"
})
public class ReservationView {

    protected long id;
    @XmlElement(required = true)
    protected String accommodatioName;
    @XmlElement(required = true)
    protected String registeredUserUsername;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startingDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endingDate;
    protected boolean confirmed;
    @XmlElement(required = true)
    protected List<MessageView> messages;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the accommodatioName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccommodatioName() {
        return accommodatioName;
    }

    /**
     * Sets the value of the accommodatioName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccommodatioName(String value) {
        this.accommodatioName = value;
    }

    /**
     * Gets the value of the registeredUserUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegisteredUserUsername() {
        return registeredUserUsername;
    }

    /**
     * Sets the value of the registeredUserUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegisteredUserUsername(String value) {
        this.registeredUserUsername = value;
    }

    /**
     * Gets the value of the startingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartingDate() {
        return startingDate;
    }

    /**
     * Sets the value of the startingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartingDate(XMLGregorianCalendar value) {
        this.startingDate = value;
    }

    /**
     * Gets the value of the endingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndingDate() {
        return endingDate;
    }

    /**
     * Sets the value of the endingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndingDate(XMLGregorianCalendar value) {
        this.endingDate = value;
    }

    /**
     * Gets the value of the confirmed property.
     * 
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Sets the value of the confirmed property.
     * 
     */
    public void setConfirmed(boolean value) {
        this.confirmed = value;
    }

    /**
     * Gets the value of the messages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageView }
     * 
     * 
     */
    public List<MessageView> getMessages() {
        if (messages == null) {
            messages = new ArrayList<MessageView>();
        }
        return this.messages;
    }

}
