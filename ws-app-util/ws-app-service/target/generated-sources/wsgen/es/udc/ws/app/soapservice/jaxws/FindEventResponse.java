
package es.udc.ws.app.soapservice.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "findEventResponse", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findEventResponse", namespace = "http://soap.ws.udc.es/")
public class FindEventResponse {

    @XmlElement(name = "return", namespace = "")
    private List<es.udc.ws.app.dto.EventDto> _return;

    /**
     * 
     * @return
     *     returns List<EventDto>
     */
    public List<es.udc.ws.app.dto.EventDto> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<es.udc.ws.app.dto.EventDto> _return) {
        this._return = _return;
    }

}
