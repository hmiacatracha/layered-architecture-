
package es.udc.ws.app.soapservice.jaxws;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "findEvent", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findEvent", namespace = "http://soap.ws.udc.es/", propOrder = {
    "keywords",
    "inicio",
    "fin"
})
public class FindEvent {

    @XmlElement(name = "keywords", namespace = "")
    private String keywords;
    @XmlElement(name = "inicio", namespace = "")
    private Calendar inicio;
    @XmlElement(name = "fin", namespace = "")
    private Calendar fin;

    /**
     * 
     * @return
     *     returns String
     */
    public String getKeywords() {
        return this.keywords;
    }

    /**
     * 
     * @param keywords
     *     the value for the keywords property
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * 
     * @return
     *     returns Calendar
     */
    public Calendar getInicio() {
        return this.inicio;
    }

    /**
     * 
     * @param inicio
     *     the value for the inicio property
     */
    public void setInicio(Calendar inicio) {
        this.inicio = inicio;
    }

    /**
     * 
     * @return
     *     returns Calendar
     */
    public Calendar getFin() {
        return this.fin;
    }

    /**
     * 
     * @param fin
     *     the value for the fin property
     */
    public void setFin(Calendar fin) {
        this.fin = fin;
    }

}
