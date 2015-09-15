
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para replyDto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="replyDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dateReply" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="eventID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="replyID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="typeReply" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="userID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "replyDto", propOrder = {
    "dateReply",
    "eventID",
    "replyID",
    "typeReply",
    "userID"
})
public class ReplyDto {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateReply;
    protected Long eventID;
    protected Long replyID;
    protected Boolean typeReply;
    protected String userID;

    /**
     * Obtiene el valor de la propiedad dateReply.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateReply() {
        return dateReply;
    }

    /**
     * Define el valor de la propiedad dateReply.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateReply(XMLGregorianCalendar value) {
        this.dateReply = value;
    }

    /**
     * Obtiene el valor de la propiedad eventID.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEventID() {
        return eventID;
    }

    /**
     * Define el valor de la propiedad eventID.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEventID(Long value) {
        this.eventID = value;
    }

    /**
     * Obtiene el valor de la propiedad replyID.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getReplyID() {
        return replyID;
    }

    /**
     * Define el valor de la propiedad replyID.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setReplyID(Long value) {
        this.replyID = value;
    }

    /**
     * Obtiene el valor de la propiedad typeReply.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTypeReply() {
        return typeReply;
    }

    /**
     * Define el valor de la propiedad typeReply.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTypeReply(Boolean value) {
        this.typeReply = value;
    }

    /**
     * Obtiene el valor de la propiedad userID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Define el valor de la propiedad userID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserID(String value) {
        this.userID = value;
    }

}
