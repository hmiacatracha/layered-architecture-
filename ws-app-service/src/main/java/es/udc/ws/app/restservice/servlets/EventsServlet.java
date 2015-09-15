package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.DataConversionException;

import es.udc.ws.app.dto.EventByUserDto;
import es.udc.ws.app.dto.EventDto;
import es.udc.ws.app.exceptions.EventHasAttendeesException;
import es.udc.ws.app.exceptions.ExpiredEventException;
import es.udc.ws.app.exceptions.InvalidRankDate;
import es.udc.ws.app.exceptions.ReduceAforoException;
import es.udc.ws.app.model.event.Event;
import es.udc.ws.app.model.eventservice.EventServiceFactory;
import es.udc.ws.app.model.reply.Reply;
import es.udc.ws.app.serviceutil.EventToEventDtoConversor;
import es.udc.ws.app.xml.ParsingException;
import es.udc.ws.app.xml.XmlEventByUserDtoConversor;
import es.udc.ws.app.xml.XmlEventDtoConversor;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class EventsServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		EventDto xmlevent;
		try {
			xmlevent = XmlEventDtoConversor.toEvent(request.getInputStream());
		} catch (ParsingException ex) {
			ServletUtils
					.writeServiceResponse(
							response,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											ex.getMessage())), null);

			return;
		}

		Event event = EventToEventDtoConversor.toEvent(xmlevent);
		try {
			event = EventServiceFactory.getService().addEvent(event);
		} catch (InputValidationException ex) {
			ServletUtils.writeServiceResponse(response,
					HttpServletResponse.SC_BAD_REQUEST,
					XmlExceptionConversor.toInputValidationExceptionXml(ex),
					null);
			return;
		}

		EventDto eventDto = EventToEventDtoConversor.toEventDto(event);

		String eventURL = request.getRequestURL().append("/")
				.append(event.getEventID()).toString();
		Map<String, String> headers = new HashMap<>(1);
		headers.put("Location", eventURL);

		ServletUtils.writeServiceResponse(response,
				HttpServletResponse.SC_CREATED,
				XmlEventDtoConversor.toXml(eventDto), headers);
	}

	@Override
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String path = ServletUtils.normalizePath(request.getPathInfo());
		if (path == null || path.length() == 0) {
			ServletUtils
					.writeServiceResponse(
							response,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid movie id")),
							null);
			return;
		}

		String eventIdString = path.substring(1);
		Long eventID;
		try {
			eventID = Long.valueOf(eventIdString);
		} catch (NumberFormatException ex) {
			ServletUtils
					.writeServiceResponse(
							response,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid movie id '"
													+ eventIdString + "'")),
							null);
			return;
		}

		EventDto eventDto;
		try {
			eventDto = XmlEventDtoConversor.toEvent(request.getInputStream());
		} catch (ParsingException ex) {
			ServletUtils
					.writeServiceResponse(
							response,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											ex.getMessage())), null);
			return;
		}
		if (!eventID.equals(eventDto.getEventID())) {
			ServletUtils
					.writeServiceResponse(
							response,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid movieId")), null);
			return;
		}

		Event event = EventToEventDtoConversor.toEvent(eventDto);
		try {
			EventServiceFactory.getService().updateEvent(event);
			
		} catch (InputValidationException ex) {
			ServletUtils.writeServiceResponse(response,
					HttpServletResponse.SC_BAD_REQUEST,
					XmlExceptionConversor.toInputValidationExceptionXml(ex),
					null);
			return;
			
		} catch (InstanceNotFoundException ex) {
			ServletUtils.writeServiceResponse(response,
					HttpServletResponse.SC_NOT_FOUND,
					XmlExceptionConversor.toInstanceNotFoundExceptionXml(ex),
					null);
			return;
			
		} catch (ReduceAforoException ex) {
			ServletUtils.writeServiceResponse(response,
					HttpServletResponse.SC_FORBIDDEN,
					XmlExceptionConversor.toReduceAforoExceptionXml(ex), null);
			return;
			
		} catch (ExpiredEventException ex) {
			ServletUtils.writeServiceResponse(response,
					HttpServletResponse.SC_FORBIDDEN,
					XmlExceptionConversor.toExpiredEventExceptionXml(ex), null);
			return;
		}
		
		ServletUtils.writeServiceResponse(response,
				HttpServletResponse.SC_NO_CONTENT, null, null);

	}

	@Override
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String path = ServletUtils.normalizePath(request.getPathInfo());
		if (path == null || path.length() == 0) {
			ServletUtils
					.writeServiceResponse(
							response,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid movie id")),
							null);
			return;
		}
		String eventIDString = path.substring(1);
		Long eventID;
		try {
			eventID = Long.valueOf(eventIDString);
		} catch (NumberFormatException ex) {
			ServletUtils
					.writeServiceResponse(
							response,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid movie id '"
													+ eventIDString + "'")),
							null);
			return;
		}

		try {
			EventServiceFactory.getService().removeEvent(eventID);
			
		} catch (InstanceNotFoundException ex) {
			ServletUtils.writeServiceResponse(response,
					HttpServletResponse.SC_NOT_FOUND,
					XmlExceptionConversor.toInstanceNotFoundExceptionXml(ex),
					null);
			return;
			
		} catch (EventHasAttendeesException ex) {
			ServletUtils.writeServiceResponse(response,
					HttpServletResponse.SC_FORBIDDEN,
					XmlExceptionConversor.toEventHasAttendeesExceptionXml(ex),
					null);
			return;
		}
		
		ServletUtils.writeServiceResponse(response,
				HttpServletResponse.SC_NO_CONTENT, null, null);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String path = ServletUtils.normalizePath(request.getPathInfo());

		if (path == null || path.length() == 0) {

			String keywords = request.getParameter("keywords");
			DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
			String dateInitString = request.getParameter("dateInit");
			Calendar dateInit = null;
			String dateFinString = request.getParameter("dateFin");
			Calendar dateFin = null;
			String assistedByUser = request.getParameter("assistedByUser");
			
			if (dateInitString != null) {
				
				dateInit = new GregorianCalendar();

				try {
					dateInit.setTime(sdf.parse(dateInitString));

				} catch (ParseException ex) {
					ServletUtils
							.writeServiceResponse(
									response,
									HttpServletResponse.SC_BAD_REQUEST,
									XmlExceptionConversor
											.toInputValidationExceptionXml(new InputValidationException(
													"Invalid dateInit")), null);
					return;
				}
			}			

			if (dateFinString != null) {
				
				dateFin = new GregorianCalendar();

				try {
					dateFin.setTime(sdf.parse(dateFinString));

				} catch (ParseException ex) {
					ServletUtils
							.writeServiceResponse(
									response,
									HttpServletResponse.SC_BAD_REQUEST,
									XmlExceptionConversor
											.toInputValidationExceptionXml(new InputValidationException(
													"Invalid dateFin")), null);
					return;
				}
			}

			List<Event> events;

			try {
				events = EventServiceFactory.getService().findEvent(keywords,
						dateInit, dateFin);

			} catch (InvalidRankDate ex) {
				ServletUtils.writeServiceResponse(response,
						HttpServletResponse.SC_BAD_REQUEST,
						XmlExceptionConversor.toInvalidRankDateXml(ex), null);
				return;

			} catch (InputValidationException ex) {
				ServletUtils
						.writeServiceResponse(response,
								HttpServletResponse.SC_BAD_REQUEST,
								XmlExceptionConversor
										.toInputValidationExceptionXml(ex),
								null);
				return;
			}

			List<EventDto> eventsDtos = EventToEventDtoConversor
					.toEventDtos(events);
			
			try {
				ServletUtils.writeServiceResponse(response,
						HttpServletResponse.SC_OK,
						XmlEventDtoConversor.toXml(eventsDtos), null);
			} catch (DataConversionException ex) {

			}
			

		} else {

			String eventIDString = path.substring(1);
			Long eventID;
			try {
				eventID = Long.valueOf(eventIDString);
			} catch (NumberFormatException ex) {
				ServletUtils
						.writeServiceResponse(
								response,
								HttpServletResponse.SC_BAD_REQUEST,
								XmlExceptionConversor
										.toInputValidationExceptionXml(new InputValidationException(
												"Invalid Request: "
														+ "invalid event id'"
														+ eventIDString + "'")),
								null);

				return;
			}

			Event event;
			try {
				event = EventServiceFactory.getService().findEventById(eventID);
				
			} catch (InstanceNotFoundException ex) {
				ServletUtils.writeServiceResponse(response,
						HttpServletResponse.SC_NOT_FOUND, XmlExceptionConversor
								.toInstanceNotFoundExceptionXml(ex), null);
				return;
			}
			
			EventDto eventDto = EventToEventDtoConversor.toEventDto(event);
			ServletUtils.writeServiceResponse(response,
					HttpServletResponse.SC_OK,
					XmlEventDtoConversor.toXml(eventDto), null);
		}
		
	}

}
