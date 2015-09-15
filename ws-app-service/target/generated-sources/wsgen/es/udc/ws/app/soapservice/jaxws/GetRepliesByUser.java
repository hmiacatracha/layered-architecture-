
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getRepliesByUser", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRepliesByUser", namespace = "http://soap.ws.udc.es/")
public class GetRepliesByUser {

    @XmlElement(name = "userId", namespace = "")
    private String userId;

    /**
     * 
     * @return
     *     returns String
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * 
     * @param userId
     *     the value for the userId property
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

}
