
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "findEventByIdResponse", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findEventByIdResponse", namespace = "http://soap.ws.udc.es/")
public class FindEventByIdResponse {

    @XmlElement(name = "return", namespace = "")
    private es.udc.ws.app.dto.EventDto _return;

    /**
     * 
     * @return
     *     returns EventDto
     */
    public es.udc.ws.app.dto.EventDto getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(es.udc.ws.app.dto.EventDto _return) {
        this._return = _return;
    }

}
