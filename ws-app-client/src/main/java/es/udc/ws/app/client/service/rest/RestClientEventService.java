package es.udc.ws.app.client.service.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import es.udc.ws.app.client.service.ClientEventService;
import es.udc.ws.app.dto.EventByUserDto;
import es.udc.ws.app.dto.EventDto;
import es.udc.ws.app.dto.ReplyDto;
import es.udc.ws.app.exceptions.CompleteEventException;
import es.udc.ws.app.exceptions.EventHasAttendeesException;
import es.udc.ws.app.exceptions.ExpiredEventException;
import es.udc.ws.app.exceptions.InvalidRankDate;
import es.udc.ws.app.exceptions.ReduceAforoException;
import es.udc.ws.app.xml.ParsingException;
import es.udc.ws.app.xml.XmlEventByUserDtoConversor;
import es.udc.ws.app.xml.XmlEventDtoConversor;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlReplyDtoConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class RestClientEventService implements ClientEventService {

	private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientEventService.endpointAddress";
	private String endpointAddress;

	@Override
	public EventDto addEvent(EventDto event) throws InputValidationException {
		try {

			HttpResponse response = Request
					.Post(getEndpointAddress() + "events")
					.bodyStream(toInputStream(event),
							ContentType.create("application/xml")).execute()
					.returnResponse();

			validateStatusCode(HttpStatus.SC_CREATED, response);

			return XmlEventDtoConversor.toEvent(response.getEntity()
					.getContent());

		} catch (InputValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateEvent(EventDto event) throws InputValidationException,
			InstanceNotFoundException, ReduceAforoException,
			ExpiredEventException {

		try {

			HttpResponse response = Request
					.Put(getEndpointAddress() + "events/" + +event.getEventID())
					.bodyStream(toInputStream(event),
							ContentType.create("application/xml")).execute()
					.returnResponse();

			validateStatusCode(HttpStatus.SC_NO_CONTENT, response);

		} catch (InputValidationException | InstanceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void removeEvent(Long eventID) throws InstanceNotFoundException,
			EventHasAttendeesException {
		try {

			HttpResponse response = Request
					.Delete(getEndpointAddress() + "events/" + eventID)
					.execute().returnResponse();

			validateStatusCode(HttpStatus.SC_NO_CONTENT, response);

		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public EventDto findEventById(Long eventID)
			throws InstanceNotFoundException {

		HttpResponse response = null;
		EventDto event = null;

		try {
			response = Request.Get(getEndpointAddress() + "events/" + eventID)
					.execute().returnResponse();

			validateStatusCode(HttpStatus.SC_OK, response);

			event = XmlEventDtoConversor.toEvent(response.getEntity()
					.getContent());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return event;
	}

	@Override
	public List<EventDto> findEvent(String keywords, Calendar inicio,
			Calendar fin) throws InvalidRankDate, InputValidationException {

		HttpResponse response = null;
		DateFormat d = new SimpleDateFormat("dd-MM-yyyy+hh:mm");
		List<EventDto> events = null;

		try {

			if ((keywords == null || keywords.isEmpty()) && inicio == null
					&& fin == null) {

				response = Request.Get(getEndpointAddress() + "events")
						.execute().returnResponse();

			} else if (keywords != null && inicio == null && fin == null) {

				response = Request
						.Get(getEndpointAddress() + "events?keywords="
								+ URLEncoder.encode(keywords, "UTF-8"))
						.execute().returnResponse();

			} else if (keywords == null && inicio != null && fin != null) {

				response = Request
						.Get(getEndpointAddress() + "events?dateInit="
								+ d.format(inicio.getTime()) + "&dateFin="
								+ d.format(fin.getTime())).execute()
						.returnResponse();

			} else {
				response = Request
						.Get(getEndpointAddress() + "events?keywords="
								+ URLEncoder.encode(keywords, "UTF-8")
								+ "&dateInit=" + d.format(inicio.getTime())
								+ "&dateFin=" + d.format(fin.getTime()))
						.execute().returnResponse();
			}

			validateStatusCode(HttpStatus.SC_OK, response);

			events = XmlEventDtoConversor.toEvents(response.getEntity()
					.getContent());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return events;

	}

	@Override
	public ReplyDto replyEvent(Long eventID, String userID, Boolean type)
			throws InstanceNotFoundException, InputValidationException,
			ExpiredEventException, CompleteEventException {

		ReplyDto reply = null;

		try {

			HttpResponse response = Request
					.Post(getEndpointAddress() + "replies")
					.bodyForm(
							Form.form().add("eventID", Long.toString(eventID))
									.add("userID", userID)
									.add("type", Boolean.toString(type))
									.build()).execute().returnResponse();

			validateStatusCode(HttpStatus.SC_CREATED, response);

			reply = XmlReplyDtoConversor.toReply(response.getEntity()
					.getContent());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return reply;
	}

	@Override
	public List<ReplyDto> getRepliesByEvent(Long eventId, Boolean type) {

		List<ReplyDto> replies = null;
		HttpResponse response = null;

		try {

			if (type == null) {

				response = Request
						.Get(getEndpointAddress() + "replies?eventID="
								+ eventId).execute().returnResponse();
			} else {

				response = Request
						.Get(getEndpointAddress() + "replies?eventID="
								+ eventId + "&type=" + type).execute()
						.returnResponse();
			}

			replies = XmlReplyDtoConversor.toReplies(response.getEntity()
					.getContent());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return replies;
	}

	@Override
	public List<ReplyDto> getRepliesByUser(String userId, Boolean type) {

		List<ReplyDto> replies = null;
		HttpResponse response = null;

		try {

			if (type == null) {

				response = Request
						.Get(getEndpointAddress() + "replies?userID=" + userId)
						.execute().returnResponse();
			} else {

				response = Request
						.Get(getEndpointAddress() + "replies?userID=" + userId
								+ "&type=" + type).execute().returnResponse();
			}

			replies = XmlReplyDtoConversor.toReplies(response.getEntity()
					.getContent());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return replies;
	}

	@Override
	public List<EventByUserDto> assistedEventsByUser(String userId) {

		List<EventByUserDto> events = null;
		HttpResponse response = null;

		try {
			response = Request
					.Get(getEndpointAddress() + "assistedEvents?userID="
							+ userId).execute().returnResponse();

			events = XmlEventByUserDtoConversor.toEventsByUser(response
					.getEntity().getContent());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return events;
	}

	private synchronized String getEndpointAddress() {

		if (endpointAddress == null) {
			endpointAddress = ConfigurationParametersManager
					.getParameter(ENDPOINT_ADDRESS_PARAMETER);
		}

		return endpointAddress;
	}

	private InputStream toInputStream(EventDto event) {

		try {

			ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

			outputter
					.output(XmlEventDtoConversor.toXml(event), xmlOutputStream);

			return new ByteArrayInputStream(xmlOutputStream.toByteArray());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void validateStatusCode(int successCode, HttpResponse response)
			throws InstanceNotFoundException, InputValidationException,
			IllegalStateException, ExpiredEventException,
			CompleteEventException, JDOMException, EventHasAttendeesException,
			ReduceAforoException, InvalidRankDate {

		try {

			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == successCode) {
				return;
			}

			SAXBuilder builder = new SAXBuilder();
			Document document = builder
					.build(response.getEntity().getContent());
			Element root = document.getRootElement();

			switch (statusCode) {

			case HttpStatus.SC_NOT_FOUND:
				throw XmlExceptionConversor
						.fromInstanceNotFoundExceptionXml(response.getEntity()
								.getContent());

			case HttpStatus.SC_BAD_REQUEST:
				if ("InputValidationException".equalsIgnoreCase(root.getName())) {
					throw XmlExceptionConversor
							.fromInputValidationExceptionXml(response
									.getEntity().getContent());
				}

				if ("InvalidRankDate".equalsIgnoreCase(root.getName())) {
					throw XmlExceptionConversor.fromInvalidRankDateXml(response
							.getEntity().getContent());
				}

			case HttpStatus.SC_GONE:
				throw XmlExceptionConversor
						.fromExpiredEventExceptionXml(response.getEntity()
								.getContent());

			case HttpStatus.SC_FORBIDDEN:

				if ("CompleteEventException".equalsIgnoreCase(root.getName())) {
					throw XmlExceptionConversor
							.fromCompleteEventExceptionXml(response.getEntity()
									.getContent());
				}

				if ("EventHasAttendeesException".equalsIgnoreCase(root
						.getName())) {
					throw XmlExceptionConversor
							.fromEventHasAttendeesExceptionXml(response
									.getEntity().getContent());
				}

				if ("ExpiredEventException".equalsIgnoreCase(root.getName())) {
					throw XmlExceptionConversor
							.fromExpiredEventExceptionXml(response.getEntity()
									.getContent());
				}

				if ("ReduceAforoException".equalsIgnoreCase(root.getName())) {
					throw XmlExceptionConversor
							.fromReduceAforoExceptionXml(response.getEntity()
									.getContent());
				}

			default:
				throw new RuntimeException("HTTP error; status code = "
						+ statusCode);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/*
	 * private static Long getIdFromHeaders(HttpResponse response) {
	 * 
	 * String location = getResponseHeader(response, "Location");
	 * 
	 * if (location != null) { int idx = location.lastIndexOf('/'); return
	 * Long.valueOf(location.substring(idx + 1)); }
	 * 
	 * return null; }
	 */

	/*
	 * private static String getResponseHeader(HttpResponse response, String
	 * headerName) {
	 * 
	 * String headerAsString = null; Header header =
	 * response.getFirstHeader(headerName);
	 * 
	 * if (header != null) { headerAsString = header.getValue(); }
	 * 
	 * return headerAsString; }
	 */

}
