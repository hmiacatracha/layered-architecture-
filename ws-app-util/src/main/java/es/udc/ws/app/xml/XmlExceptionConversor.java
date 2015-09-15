package es.udc.ws.app.xml;

import java.io.IOException;
import java.io.InputStream;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import es.udc.ws.app.exceptions.CompleteEventException;
import es.udc.ws.app.exceptions.EventHasAttendeesException;
import es.udc.ws.app.exceptions.ExpiredEventException;
import es.udc.ws.app.exceptions.InvalidRankDate;
import es.udc.ws.app.exceptions.ReduceAforoException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class XmlExceptionConversor {

	public final static Namespace XML_NS = XmlEventDtoConversor.XML_NS;

	public static InputValidationException fromInputValidationExceptionXml(
			InputStream ex) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(ex);
			Element rootElement = document.getRootElement();
			Element message = rootElement.getChild("message", XML_NS);
			return new InputValidationException(message.getText());

		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}

	}

	public static Document toInputValidationExceptionXml(
			InputValidationException ex) {
		Element exceptionElement = new Element("InputValidationException",
				XML_NS);
		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(ex.getMessage());
		exceptionElement.addContent(messageElement);
		return new Document(exceptionElement);
	}

	public static InstanceNotFoundException fromInstanceNotFoundExceptionXml(
			InputStream ex) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document;
			document = builder.build(ex);
			Element rootElement = document.getRootElement();
			Element instanceId = rootElement.getChild("instanceId", XML_NS);
			Element instanceType = rootElement.getChild("instanceType", XML_NS);
			return new InstanceNotFoundException(instanceId.getText(),
					instanceType.getText());

		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}

	}

	public static Document toInstanceNotFoundExceptionXml(
			InstanceNotFoundException ex) {
		Element exceptionElement = new Element("InstanceNotFoundException",
				XML_NS);
		if (ex.getInstanceId() != null) {
			Element messageId = new Element("instanceId", XML_NS);
			messageId.setText(ex.getInstanceId().toString());
			exceptionElement.addContent(messageId);
		}

		if (ex.getInstanceType() != null) {
			Element messageElement = new Element("instanceType", XML_NS);
			messageElement.setText(ex.getMessage());
			exceptionElement.addContent(messageElement);
		}
		return new Document(exceptionElement);
	}

	public static CompleteEventException fromCompleteEventExceptionXml(
			InputStream ex) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(ex);
			Element rootElement = document.getRootElement();
			Element message = rootElement.getChild("message", XML_NS);
			return new CompleteEventException(message.getText());
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static Document toCompleteEventExceptionXml(CompleteEventException ex) {

		Element exceptionElement = new Element("CompleteEventException",
				XML_NS);
		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(ex.getMessage());
		exceptionElement.addContent(messageElement);
		return new Document(exceptionElement);

	}

	public static EventHasAttendeesException fromEventHasAttendeesExceptionXml(
			InputStream ex) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(ex);
			Element rootElement = document.getRootElement();
			Element message = rootElement.getChild("message", XML_NS);
			return new EventHasAttendeesException(message.getText());
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static Document toEventHasAttendeesExceptionXml(
			EventHasAttendeesException ex) {
		Element exceptionElement = new Element("EventHasAttendeesException",
				XML_NS);
		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(ex.getMessage());
		exceptionElement.addContent(messageElement);
		return new Document(exceptionElement);
	}

	public static ExpiredEventException fromExpiredEventExceptionXml(
			InputStream ex) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(ex);
			Element rootElement = document.getRootElement();
			Element message = rootElement.getChild("message", XML_NS);
			return new ExpiredEventException(message.getText());
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static Document toExpiredEventExceptionXml(ExpiredEventException ex) {
		Element exceptionElement = new Element("ExpiredEventException",
				XML_NS);
		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(ex.getMessage());
		exceptionElement.addContent(messageElement);
		return new Document(exceptionElement);
	}

	public static InvalidRankDate fromInvalidRankDateXml(InputStream ex) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(ex);
			Element rootElement = document.getRootElement();
			Element message = rootElement.getChild("message", XML_NS);
			return new InvalidRankDate(message.getText());
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static Document toInvalidRankDateXml(InvalidRankDate ex) {
		Element exceptionElement = new Element("InvalidRankDate",
				XML_NS);
		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(ex.getMessage());
		exceptionElement.addContent(messageElement);
		return new Document(exceptionElement);
	}

	public static ReduceAforoException fromReduceAforoExceptionXml(
			InputStream ex) {

		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(ex);
			Element rootElement = document.getRootElement();
			Element message = rootElement.getChild("message", XML_NS);
			return new ReduceAforoException(message.getText());
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static Document toReduceAforoExceptionXml(ReduceAforoException ex) {
		Element exceptionElement = new Element("ReduceAforoException",
				XML_NS);
		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(ex.getMessage());
		exceptionElement.addContent(messageElement);
		return new Document(exceptionElement);
	}
}
