
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "replyEventResponse", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "replyEventResponse", namespace = "http://soap.ws.udc.es/")
public class ReplyEventResponse {

    @XmlElement(name = "return", namespace = "")
    private es.udc.ws.app.dto.ReplyDto _return;

    /**
     * 
     * @return
     *     returns ReplyDto
     */
    public es.udc.ws.app.dto.ReplyDto getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(es.udc.ws.app.dto.ReplyDto _return) {
        this._return = _return;
    }

}
