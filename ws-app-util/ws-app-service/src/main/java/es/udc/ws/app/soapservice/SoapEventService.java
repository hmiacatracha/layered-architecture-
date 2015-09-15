package es.udc.ws.app.soapservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import es.udc.ws.app.dto.EventByUserDto;
import es.udc.ws.app.dto.EventDto;
import es.udc.ws.app.dto.ReplyDto;
import es.udc.ws.app.exceptions.CompleteEventException;
import es.udc.ws.app.exceptions.EventHasAttendeesException;
import es.udc.ws.app.exceptions.ExpiredEventException;
import es.udc.ws.app.exceptions.InvalidRankDate;
import es.udc.ws.app.exceptions.ReduceAforoException;
import es.udc.ws.app.model.event.Event;
import es.udc.ws.app.model.eventservice.EventServiceFactory;
import es.udc.ws.app.model.reply.Reply;
import es.udc.ws.app.serviceutil.EventToEventDtoConversor;
import es.udc.ws.app.serviceutil.ReplyToReplyDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.soapservice.SoapInputValidationException;

@WebService(name = "EventsProvider", serviceName = "EventsProviderService", targetNamespace = "http://soap.ws.udc.es/")
public class SoapEventService {

	@WebMethod(operationName = "addEvent")
	public EventDto addEvent(@WebParam(name = "eventDto") EventDto eventDto)
			throws SoapInputValidationException {

		Event eventToCreate = EventToEventDtoConversor.toEvent(eventDto);
		Event eventCreated;

		try {
			eventCreated = EventServiceFactory.getService().addEvent(
					eventToCreate);

			return EventToEventDtoConversor.toEventDto(eventCreated);

		} catch (InputValidationException e) {
			throw new SoapInputValidationException(e.getMessage());
		}
	}

	@WebMethod(operationName = "updateEvent")
	public void updateEvent(@WebParam(name = "eventDto") EventDto eventDto)
			throws SoapInputValidationException, SoapInstanceNotFoundException,
			SoapReduceAforoException, SoapExpiredEventException {

		Event event = EventToEventDtoConversor.toEvent(eventDto);
		try {
			EventServiceFactory.getService().updateEvent(event);

		} catch (InputValidationException e) {
			throw new SoapInputValidationException(e.getMessage());

		} catch (InstanceNotFoundException e) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(),
							e.getInstanceType()));

		} catch (ExpiredEventException e) {
			throw new SoapExpiredEventException(e.getMessage());

		} catch (ReduceAforoException e) {
			throw new SoapReduceAforoException(e.getMessage());
		}
	}

	@WebMethod(operationName = "removeEvent")
	public void removeEvent(@WebParam(name = "eventID") Long eventID)
			throws SoapEventHasAttendeesException,
			SoapInstanceNotFoundException {
		try {
			EventServiceFactory.getService().removeEvent(eventID);

		} catch (InstanceNotFoundException e) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(),
							e.getInstanceType()));
		} catch (EventHasAttendeesException e) {
			throw new SoapEventHasAttendeesException(e.getMessage());
		}
	}

	@WebMethod(operationName = "findEventById")
	public EventDto findEventById(@WebParam(name = "eventID") Long eventID)
			throws SoapInstanceNotFoundException {

		try {
			Event event = EventServiceFactory.getService().findEventById(
					eventID);
			return EventToEventDtoConversor.toEventDto(event);
		} catch (InstanceNotFoundException e) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(),
							e.getInstanceType()));
		}
	}

	@WebMethod(operationName = "findEvent")
	public List<EventDto> findEvent(
			@WebParam(name = "keywords") String keywords,
			@WebParam(name = "inicio") Calendar inicio,
			@WebParam(name = "fin") Calendar fin)
			throws SoapInvalidRankDateException, SoapInputValidationException {

		List<EventDto> eventsDto = new ArrayList<EventDto>();
		List<Event> events = new ArrayList<Event>();
		try {
			events = EventServiceFactory.getService().findEvent(keywords,
					inicio, fin);
			eventsDto = EventToEventDtoConversor.toEventDtos(events);
			return eventsDto;

		} catch (InputValidationException e) {
			throw new SoapInputValidationException(e.getMessage());

		} catch (InvalidRankDate e) {
			throw new SoapInvalidRankDateException(e.getMessage());
		}
	}

	@WebMethod(operationName = "replyEvent")
	public ReplyDto replyEvent(@WebParam(name = "user") String user,
			@WebParam(name = "eventId") Long eventId,
			@WebParam(name = "type") Boolean type)
			throws SoapInputValidationException, SoapExpiredEventException,
			SoapCompleteEventException, SoapInstanceNotFoundException {

		try {

			Reply reply = EventServiceFactory.getService().replyEvent(user,
					eventId, type, null);

			ReplyDto responseDto = ReplyToReplyDtoConversor.toReplyDto(reply);
			return responseDto;

		} catch (InstanceNotFoundException e) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(e.getInstanceId(),
							e.getInstanceType()));
		} catch (InputValidationException e) {
			throw new SoapInputValidationException(e.getMessage());
		} catch (ExpiredEventException e) {
			throw new SoapExpiredEventException(e.getMessage());
		} catch (CompleteEventException e) {
			throw new SoapCompleteEventException(e.getMessage());
		}
	}

	@WebMethod(operationName = "getRepliesByEvent")
	public List<ReplyDto> getRepliesByEvent(
			@WebParam(name = "eventId") Long eventId,
			@WebParam(name = "type") Boolean type) {

		List<Reply> replies = new ArrayList<Reply>();
		List<ReplyDto> replyDtos = new ArrayList<ReplyDto>();

		replies = EventServiceFactory.getService().getRepliesByEvent(eventId,
				type);
		replyDtos = ReplyToReplyDtoConversor.toReplyDtos(replies);
		return replyDtos;

	}

	@WebMethod(operationName = "getRepliesByUser")
	public List<ReplyDto> getRepliesByUser(
			@WebParam(name = "userId") String userId,
			@WebParam(name = "type") Boolean type) {

		List<Reply> replies = new ArrayList<Reply>();
		replies = EventServiceFactory.getService().getRepliesByUser(userId,
				type);
		List<ReplyDto> replyDtos = new ArrayList<ReplyDto>();
		replyDtos = ReplyToReplyDtoConversor.toReplyDtos(replies);
		return replyDtos;
	}

	@WebMethod(operationName = "assistedEventsByUser")
	public List<EventByUserDto> assistedEventByUser(
			@WebParam(name = "userId") String userId) {
		List<Reply> repliedEvents = new ArrayList<Reply>();
		List<EventByUserDto> assistedEvents = new ArrayList<EventByUserDto>();
		Calendar now = Calendar.getInstance();
		repliedEvents = EventServiceFactory.getService().getRepliesByUser(
				userId, true);
		Long eventId;
		Event event;

		for (int i = 0; i < repliedEvents.size(); i++) {
			eventId = repliedEvents.get(i).getEventID();
			try {
				event = EventServiceFactory.getService().findEventById(eventId);
				if (now.after(event.getDateInit())) {
					assistedEvents.add(new EventByUserDto(event.getName(),
							event.getDateInit(), repliedEvents.get(i)
									.getDateReply()));
				}

			} catch (InstanceNotFoundException e) {

			}
		}
		return assistedEvents;
	}
}
