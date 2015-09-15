package es.udc.ws.app.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import es.udc.ws.app.dto.EventDto;
import es.udc.ws.app.xml.ParsingException;
import org.jdom2.DataConversionException;

public class XmlEventDtoConversor {

	public final static Namespace XML_NS = Namespace
			.getNamespace("http://ws.udc.es/events/xml");

	
	public static Document toXml(EventDto event) throws IOException
			 {

		Element eventElement = toJDOMElement(event);

		return new Document(eventElement);
	}

	public static Document toXml(List<EventDto> events) throws IOException,
			DataConversionException {

		Element eventsElement = new Element("events", XML_NS);
		
		for (int i = 0; i < events.size(); i++) {
			EventDto xmlEventDto = events.get(i);
			Element eventElement = toJDOMElement(xmlEventDto);
			eventsElement.addContent(eventElement);
		}

		return new Document(eventsElement);
	}

	public static Element toJDOMElement(EventDto event)
			 {

		Element eventElement = new Element("event", XML_NS);

		if (event.getEventID() != null) {
			Element identifierElement = new Element("eventID", XML_NS);
			identifierElement.setText(event.getEventID().toString());
			eventElement.addContent(identifierElement);
		}

		Element nameElement = new Element("name", XML_NS);
		nameElement.setText(event.getName());
		eventElement.addContent(nameElement);

		Element descriptionElement = new Element("description", XML_NS);
		descriptionElement.setText(event.getDescription());
		eventElement.addContent(descriptionElement);

		Element dateElement = new Element("dateInit", XML_NS);
		dateElement = calendarToElement(event.getDateInit());
		eventElement.addContent(dateElement);

		Element aforoElement = new Element("aforo", XML_NS);
		aforoElement.setText(Integer.toString(event.getAforo()));
		eventElement.addContent(aforoElement);

		Element attendeesElement = new Element("attendees", XML_NS);
		attendeesElement.setText(Long.toString(event.getAttendees()));
		eventElement.addContent(attendeesElement);

		Element durationElement = new Element("duration", XML_NS);
		durationElement.setText(Long.toString(event.getDuration()));
		eventElement.addContent(durationElement);

		return eventElement;
	}

	private static EventDto toEvent(Element eventElement)
            throws ParsingException, DataConversionException {
        
		if (!"event".equals(
                eventElement.getName())) {
            
			throw new ParsingException("Unrecognized element '"
                    + eventElement.getName() + "' ('event' expected)");
        }
		
        Element identifierElement = eventElement.getChild("eventID", XML_NS);
        Long identifier = null;

        if (identifierElement != null) {
            identifier = Long.valueOf(identifierElement.getTextTrim());
        }
        
        String name = eventElement.getChildTextNormalize("name", XML_NS);
        
        String description = eventElement.getChildTextNormalize("description", XML_NS);
        
        Calendar dateInit = elementToCalendar(eventElement.getChild("dateInit", XML_NS));
        
        int aforo = Integer.valueOf(eventElement.getChildTextTrim("aforo", XML_NS));
        
        long attendees = Long.valueOf(eventElement.getChildTextTrim("attendees", XML_NS));
        
        long duration = Long.valueOf(eventElement.getChildTextTrim("duration", XML_NS));

          			
        return new EventDto(identifier, name, description, dateInit, duration, aforo, attendees);
    }

	public static EventDto toEvent(InputStream eventXml)
			throws ParsingException { 
		
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(eventXml);
			Element rootElement = document.getRootElement();

			return toEvent(rootElement);

		} catch (ParsingException ex) {
			throw ex;
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}
	
	public static List<EventDto> toEvents(InputStream eventXml)
            throws ParsingException {
        
		try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(eventXml);
            Element rootElement = document.getRootElement();

            if(!"events".equalsIgnoreCase(rootElement.getName())) {
                throw new ParsingException("Unrecognized element '"
                    + rootElement.getName() + "' ('events' expected)");
            }
            
            @SuppressWarnings("unchecked")
			List<Element> children = rootElement.getChildren();
            List<EventDto> eventDtos = new ArrayList<>(children.size());
            
            for (int i = 0; i < children.size(); i++) {
                Element element = children.get(i);
                eventDtos.add(toEvent(element));
            }

            return eventDtos;
            
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }


	private static Element calendarToElement(Calendar date)
			{

		Element dateElement = new Element("dateInit", XML_NS);

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
}
