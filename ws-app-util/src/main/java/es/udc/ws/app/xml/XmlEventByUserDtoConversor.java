package es.udc.ws.app.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import es.udc.ws.app.dto.EventByUserDto;

public class XmlEventByUserDtoConversor {
	
	public final static Namespace XML_NS = Namespace
			.getNamespace("http://ws.udc.es/assistedEvents/xml");

	public static Document toXml(EventByUserDto eventByUser) throws IOException {

		Element eventByUserElement = toJDOMElement(eventByUser);

		return new Document(eventByUserElement);
	}

	public static Document toXml(List<EventByUserDto> eventsByUser) throws IOException {

		Element eventsByUserElement = new Element("assistedEvents", XML_NS);

		for (int i = 0; i < eventsByUser.size(); i++) {
			EventByUserDto xmlEventByUserDto = eventsByUser.get(i);
			Element eventByUserElement = toJDOMElement(xmlEventByUserDto);
			eventsByUserElement.addContent(eventByUserElement);
		}

		return new Document(eventsByUserElement);
	}
	
	public static Element toJDOMElement(EventByUserDto eventByUser) {

		Element eventByUserElement = new Element("assistedEvents", XML_NS);

		Element nameElement = new Element("eventName", XML_NS);
		nameElement.setText(eventByUser.getEventName());
		eventByUserElement.addContent(nameElement);
		
		Element dateInitElement = new Element("dateInit", XML_NS);
		dateInitElement = calendarToElement(eventByUser.getDateInit(), "dateInit");
		eventByUserElement.addContent(dateInitElement);
		
		Element dateElement = new Element("dateReply", XML_NS);
		dateElement = calendarToElement(eventByUser.getDateReply(), "dateReply");
		eventByUserElement.addContent(dateElement);

		return eventByUserElement;
	}
	
	private static EventByUserDto toEventByUser(Element eventByUserElement)
			throws ParsingException, DataConversionException {

		if (!"assistedEvents".equals(eventByUserElement.getName())) {
			throw new ParsingException("Unrecognized element '"
					+ eventByUserElement.getName() + "' ('assistedEvents' expected)");
		}
		
		String eventName = eventByUserElement.getChildText("eventName", XML_NS);		

		Calendar dateInit = elementToCalendar(eventByUserElement.getChild(
				"dateInit", XML_NS));
		
		Calendar dateReply = elementToCalendar(eventByUserElement.getChild(
				"dateReply", XML_NS));

		return new EventByUserDto(eventName, dateInit, dateReply);
	}
	
	public static EventByUserDto toEventByUser(InputStream eventByUserXml)
			throws ParsingException {

		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(eventByUserXml);
			Element rootElement = document.getRootElement();

			return toEventByUser(rootElement);
		} catch (ParsingException ex) {
			throw ex;
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}
	
	public static List<EventByUserDto> toEventsByUser(InputStream eventByUserXml)
			throws ParsingException {

		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(eventByUserXml);
			Element rootElement = document.getRootElement();

			if (!"assistedEvents".equalsIgnoreCase(rootElement.getName())) {
				throw new ParsingException("Unrecognized element '"
						+ rootElement.getName() + "' ('assistedEvents' expected)");
			}

			List<Element> children = rootElement.getChildren();
			List<EventByUserDto> eventsByUserDtos = new ArrayList<>(children.size());
			for (int i = 0; i < children.size(); i++) {
				Element element = children.get(i);
				eventsByUserDtos.add(toEventByUser(element));
			}

			return eventsByUserDtos;

		} catch (ParsingException ex) {
			throw ex;
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}
	
	private static Calendar elementToCalendar(Element dateElement)
			throws DataConversionException {

		Calendar date = Calendar.getInstance();

		int day = dateElement.getAttribute("day").getIntValue();
		int month = dateElement.getAttribute("month").getIntValue();
		int year = dateElement.getAttribute("year").getIntValue();
		int hours = dateElement.getAttribute("hours").getIntValue();
		int minutes = dateElement.getAttribute("minutes").getIntValue();

		date.set(Calendar.DAY_OF_MONTH, day);
		date.set(Calendar.MONTH, Calendar.JANUARY + month - 1);
		date.set(Calendar.YEAR, year);
		date.set(Calendar.HOUR_OF_DAY, hours);
		date.set(Calendar.MINUTE, minutes);

		return date;
	}

	private static Element calendarToElement(Calendar date, String dateType) {

		Element dateElement = new Element(dateType, XML_NS);

		int day = date.get(Calendar.DAY_OF_MONTH);
		int month = date.get(Calendar.MONTH) - Calendar.JANUARY + 1;
		int year = date.get(Calendar.YEAR);
		int hours = date.get(Calendar.HOUR_OF_DAY);
		int minutes = date.get(Calendar.MINUTE);

		dateElement.setAttribute("day", Integer.toString(day));
		dateElement.setAttribute("month", Integer.toString(month));
		dateElement.setAttribute("year", Integer.toString(year));
		dateElement.setAttribute("hours", Integer.toString(hours));
		dateElement.setAttribute("minutes", Integer.toString(minutes));

		return dateElement;
	}
}
