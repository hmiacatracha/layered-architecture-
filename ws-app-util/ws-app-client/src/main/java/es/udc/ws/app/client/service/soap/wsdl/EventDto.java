
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para eventDto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="eventDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aforo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="attendees" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="dateInit" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="eventID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventDto", propOrder = {
    "aforo",
    "attendees",
    "dateInit",
    "description",
    "duration",
    "eventID",
    "name"
})
public class EventDto {

    protected int aforo;
    protected long attendees;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateInit;
    protected String description;
    protected long duration;
    protected Long eventID;
    protected String name;

    /**
     * Obtiene el valor de la propiedad aforo.
     * 
     */
    public int getAforo() {
        return aforo;
    }

    /**
     * Define el valor de la propiedad aforo.
     * 
     */
    public void setAforo(int value) {
        this.aforo = value;
    }

    /**
     * Obtiene el valor de la propiedad attendees.
     * 
     */
    public long getAttendees() {
        return attendees;
    }

    /**
     * Define el valor de la propiedad attendees.
     * 
     */
    public void setAttendees(long value) {
        this.attendees = value;
    }

    /**
     * Obtiene el valor de la propiedad dateInit.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateInit() {
        return dateInit;
    }

    /**
     * Define el valor de la propiedad dateInit.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateInit(XMLGregorianCalendar value) {
        this.dateInit = value;
    }

    /**
     * Obtiene el valor de la propiedad description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtiene el valor de la propiedad duration.
     * 
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Define el valor de la propiedad duration.
     * 
     */
    public void setDuration(long value) {
        this.duration = value;
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
     * Obtiene el valor de la propiedad name.
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
     * Define el valor de la propiedad name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
