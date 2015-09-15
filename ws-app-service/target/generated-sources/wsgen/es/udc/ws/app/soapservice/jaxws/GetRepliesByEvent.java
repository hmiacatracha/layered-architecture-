
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getRepliesByEvent", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRepliesByEvent", namespace = "http://soap.ws.udc.es/", propOrder = {
    "eventId",
    "type"
})
public class GetRepliesByEvent {

    @XmlElement(name = "eventId", namespace = "")
    private Long eventId;
    @XmlElement(name = "type", namespace = "")
    private Boolean type;

    /**
     * 
     * @return
     *     returns Long
     */
    public Long getEventId() {
        return this.eventId;
    }

    /**
     * 
     * @param eventId
     *     the value for the eventId property
     */
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    /**
     * 
     * @return
     *     returns Boolean
     */
    public Boolean getType() {
        return this.type;
    }

    /**
     * 
     * @param type
     *     the value for the type property
     */
    public void setType(Boolean type) {
        this.type = type;
    }

}
