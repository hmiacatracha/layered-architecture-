
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "addEvent", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addEvent", namespace = "http://soap.ws.udc.es/")
public class AddEvent {

    @XmlElement(name = "eventDto", namespace = "")
    private es.udc.ws.app.dto.EventDto eventDto;

    /**
     * 
     * @return
     *     returns EventDto
     */
    public es.udc.ws.app.dto.EventDto getEventDto() {
        return this.eventDto;
    }

    /**
     * 
     * @param eventDto
     *     the value for the eventDto property
     */
    public void setEventDto(es.udc.ws.app.dto.EventDto eventDto) {
        this.eventDto = eventDto;
    }

}
