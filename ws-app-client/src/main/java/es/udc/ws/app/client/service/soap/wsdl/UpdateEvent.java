
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para updateEvent complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="updateEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="eventDto" type="{http://soap.ws.udc.es/}eventDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateEvent", propOrder = {
    "eventDto"
})
public class UpdateEvent {

    protected EventDto eventDto;

    /**
     * Obtiene el valor de la propiedad eventDto.
     * 
     * @return
     *     possible object is
     *     {@link EventDto }
     *     
     */
    public EventDto getEventDto() {
        return eventDto;
    }

    /**
     * Define el valor de la propiedad eventDto.
     * 
     * @param value
     *     allowed object is
     *     {@link EventDto }
     *     
     */
    public void setEventDto(EventDto value) {
        this.eventDto = value;
    }

}
