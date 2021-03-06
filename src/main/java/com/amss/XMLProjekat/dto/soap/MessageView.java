
package com.amss.XMLProjekat.dto.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messageView complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="messageView">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fromUserUsername" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="toUserUsername" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messageView", propOrder = {
    "id",
    "content",
    "fromUserUsername",
    "toUserUsername"
})
public class MessageView {

    protected long id;
    @XmlElement(required = true)
    protected String content;
    @XmlElement(required = true)
    protected String fromUserUsername;
    @XmlElement(required = true)
    protected String toUserUsername;

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
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the fromUserUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromUserUsername() {
        return fromUserUsername;
    }

    /**
     * Sets the value of the fromUserUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromUserUsername(String value) {
        this.fromUserUsername = value;
    }

    /**
     * Gets the value of the toUserUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToUserUsername() {
        return toUserUsername;
    }

    /**
     * Sets the value of the toUserUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToUserUsername(String value) {
        this.toUserUsername = value;
    }

}
