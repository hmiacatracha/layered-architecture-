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

import es.udc.ws.app.dto.ReplyDto;
import es.udc.ws.app.xml.ParsingException;

public class XmlReplyDtoConversor {

	public final static Namespace XML_NS = Namespace
			.getNamespace("http://ws.udc.es/replies/xml");

	public static Document toXml(ReplyDto reply) throws IOException {

		Element replyElement = toJDOMElement(reply);

		return new Document(replyElement);
	}

	public static Document toXml(List<ReplyDto> replies) throws IOException {

		Element repliesElement = new Element("replies", XML_NS);

		for (int i = 0; i < replies.size(); i++) {
			ReplyDto xmlReplyDto = replies.get(i);
			Element replyElement = toJDOMElement(xmlReplyDto);
			repliesElement.addContent(replyElement);
		}

		return new Document(repliesElement);
	}

	public static Element toJDOMElement(ReplyDto reply) {

		Element replyElement = new Element("reply", XML_NS);

		if (reply.getReplyID() != null) {
			Element identifierElement = new Element("replyID", XML_NS);
			identifierElement.setText(reply.getReplyID().toString());
			replyElement.addContent(identifierElement);
		}

		Element dateElement = new Element("dateReply", XML_NS);
		dateElement = calendarToElement(reply.getDateReply());
		replyElement.addContent(dateElement);

		Element userElement = new Element("userID", XML_NS);
		userElement.setText(reply.getUserID());
		replyElement.addContent(userElement);

		Element eventElement = new Element("eventID", XML_NS);
		eventElement.setText(reply.getEventID().toString());
		replyElement.addContent(eventElement);

		Element typeElement = new Element("typeReply", XML_NS);
		typeElement.setText(reply.getTypeReply().toString());
		replyElement.addContent(typeElement);

		return replyElement;
	}

	private static ReplyDto toReply(Element replyElement)
			throws ParsingException, DataConversionException {

		if (!"reply".equals(replyElement.getName())) {
			throw new ParsingException("Unrecognized element '"
					+ replyElement.getName() + "' ('reply' expected)");
		}

		Element identifierElement = replyElement.getChild("replyID", XML_NS);
		Long identifier = null;

		if (identifierElement != null) {
			identifier = Long.valueOf(identifierElement.getTextTrim());
		}

		Calendar dateReply = elementToCalendar(replyElement.getChild(
				"dateReply", XML_NS));

		String userID = replyElement.getChildText("userID", XML_NS);

		Long eventID = Long.valueOf(replyElement.getChildTextTrim("eventID",
				XML_NS));

		Boolean typeReply = Boolean.valueOf(replyElement.getChildTextTrim(
				"typeReply", XML_NS));

		return new ReplyDto(identifier, dateReply, userID, eventID, typeReply);
	}

	public static ReplyDto toReply(InputStream replyXml)
			throws ParsingException {

		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(replyXml);
			Element rootElement = document.getRootElement();

			return toReply(rootElement);
			
		} catch (ParsingException ex) {
			throw ex;
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static List<ReplyDto> toReplies(InputStream replyXml)
			throws ParsingException {

		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(replyXml);
			Element rootElement = document.getRootElement();

			if (!"replies".equalsIgnoreCase(rootElement.getName())) {
				throw new ParsingException("Unrecognized element '"
						+ rootElement.getName() + "' ('replies' expected)");
			}

            @SuppressWarnings("unchecked")
			List<Element> children = rootElement.getChildren();
			List<ReplyDto> repliesDtos = new ArrayList<>(children.size());
			
			for (int i = 0; i < children.size(); i++) {
				Element element = children.get(i);
				repliesDtos.add(toReply(element));
			}

			return repliesDtos;

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

	private static Element calendarToElement(Calendar date) {

		Element dateElement = new Element("dateReply", XML_NS);

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
