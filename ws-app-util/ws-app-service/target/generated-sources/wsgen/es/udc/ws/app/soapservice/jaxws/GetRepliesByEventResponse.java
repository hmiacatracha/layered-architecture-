
package es.udc.ws.app.soapservice.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getRepliesByEventResponse", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRepliesByEventResponse", namespace = "http://soap.ws.udc.es/")
public class GetRepliesByEventResponse {

    @XmlElement(name = "return", namespace = "")
    private List<es.udc.ws.app.dto.ReplyDto> _return;

    /**
     * 
     * @return
     *     returns List<ReplyDto>
     */
    public List<es.udc.ws.app.dto.ReplyDto> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<es.udc.ws.app.dto.ReplyDto> _return) {
        this._return = _return;
    }

}
