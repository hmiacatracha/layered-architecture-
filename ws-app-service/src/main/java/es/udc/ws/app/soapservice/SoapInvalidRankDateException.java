package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "SoapInvalidRankDateException", targetNamespace = "http://soap.ws.udc.es/")
public class SoapInvalidRankDateException extends Exception {

	public SoapInvalidRankDateException(String message) {
		super(message);
	}

	public String getFaultInfo() {
		return getMessage();
	}
}
