package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "SoapExpiredEventException", targetNamespace = "http://soap.ws.udc.es/")
public class SoapExpiredEventException extends Exception {

	// private SoapExpiredEventExceptionInfo faultInfo;

	public SoapExpiredEventException(String message) {
		super(message);
	}

	public String getFaultInfo() {
		return getMessage();
	}

}
