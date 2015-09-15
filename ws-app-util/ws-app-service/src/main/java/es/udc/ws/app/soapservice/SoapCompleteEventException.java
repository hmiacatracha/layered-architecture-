package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "SoapCompleteEventException", targetNamespace = "http://soap.ws.udc.es/")
public class SoapCompleteEventException extends Exception {

	public SoapCompleteEventException(String message) {
		super(message);
	}

	public String getFaultInfo() {
		return getMessage();
	}

}
