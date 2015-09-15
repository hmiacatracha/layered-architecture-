
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "removeEvent", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeEvent", namespace = "http://soap.ws.udc.es/")
public class RemoveEvent {

    @XmlElement(name = "eventID", namespace = "")
    private Long eventID;

    /**
     * 
     * @return
     *     returns Long
     */
    public Long getEventID() {
        return this.eventID;
    }

    /**
     * 
     * @param eventID
     *     the value for the eventID property
     */
    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

}
