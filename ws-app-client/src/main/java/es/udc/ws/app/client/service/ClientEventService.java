package es.udc.ws.app.client.service;

import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.dto.EventByUserDto;
import es.udc.ws.app.dto.EventDto;
import es.udc.ws.app.dto.ReplyDto;
import es.udc.ws.app.exceptions.CompleteEventException;
import es.udc.ws.app.exceptions.EventHasAttendeesException;
import es.udc.ws.app.exceptions.ExpiredEventException;
import es.udc.ws.app.exceptions.InvalidRankDate;
import es.udc.ws.app.exceptions.ReduceAforoException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface ClientEventService {

	public EventDto addEvent(EventDto event) throws InputValidationException;

	public void updateEvent(EventDto event) throws InputValidationException,
			InstanceNotFoundException, ReduceAforoException,
			ExpiredEventException;

	public void removeEvent(Long eventID) throws InstanceNotFoundException,
			EventHasAttendeesException;

	public EventDto findEventById(Long eventId)
			throws InstanceNotFoundException;

	public List<EventDto> findEvent(String keywords, Calendar inicio,
			Calendar fin) throws InvalidRankDate, InputValidationException;

	public ReplyDto replyEvent(Long eventId, String userId, Boolean type)
			throws InstanceNotFoundException, InputValidationException,
			ExpiredEventException, CompleteEventException;

	public List<ReplyDto> getRepliesByEvent(Long eventId, Boolean type);

	public List<ReplyDto> getRepliesByUser(String userId, Boolean type);

	public List<EventByUserDto> assistedEventsByUser(String userId);

}
