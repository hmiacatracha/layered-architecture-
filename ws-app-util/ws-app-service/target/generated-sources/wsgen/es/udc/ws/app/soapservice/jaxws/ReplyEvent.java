
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "replyEvent", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "replyEvent", namespace = "http://soap.ws.udc.es/", propOrder = {
    "user",
    "eventId",
    "type"
})
public class ReplyEvent {

    @XmlElement(name = "user", namespace = "")
    private String user;
    @XmlElement(name = "eventId", namespace = "")
    private Long eventId;
    @XmlElement(name = "type", namespace = "")
    private Boolean type;

    /**
     * 
     * @return
     *     returns String
     */
    public String getUser() {
        return this.user;
    }

    /**
     * 
     * @param user
     *     the value for the user property
     */
    public void setUser(String user) {
        this.user = user;
    }

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
