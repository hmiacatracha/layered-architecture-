package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "SoapReduceAforoException", targetNamespace = "http://soap.ws.udc.es/")
public class SoapReduceAforoException extends Exception {

	public SoapReduceAforoException(String message) {
		super(message);
	}

	public String getFaultInfo() {
		return getMessage();
	}
}
