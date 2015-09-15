
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "updateEvent", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateEvent", namespace = "http://soap.ws.udc.es/")
public class UpdateEvent {

    @XmlElement(name = "EventDto", namespace = "")
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
