
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para removeEvent complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="removeEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="eventID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeEvent", propOrder = {
    "eventID"
})
public class RemoveEvent {

    protected Long eventID;

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

}
