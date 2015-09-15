package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "SoapEventHasAttendeesException", targetNamespace = "http://soap.ws.udc.es/")
public class SoapEventHasAttendeesException extends Exception {

	public SoapEventHasAttendeesException(String message) {
		super(message);
	}

	public String getFaultInfo() {
		return getMessage();
	}
}
