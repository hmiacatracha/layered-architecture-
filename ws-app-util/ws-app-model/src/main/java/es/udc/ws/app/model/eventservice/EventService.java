package es.udc.ws.app.model.eventservice;

import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.exceptions.CompleteEventException;
import es.udc.ws.app.exceptions.EventHasAttendeesException;
import es.udc.ws.app.exceptions.ExpiredEventException;
import es.udc.ws.app.exceptions.InvalidRankDate;
import es.udc.ws.app.exceptions.ReduceAforoException;
import es.udc.ws.app.model.event.Event;
import es.udc.ws.app.model.reply.Reply;
import es.udc.ws.util.exceptions.*;

/*
 * Facade Design: Use cases 
 * @Bea Iglesias
 * @author Uxia Ponte
 * @author Heidy Izaguirre
 * 
 */

public interface EventService {

	/**
	 * @param event
	 * @return Event
	 */
	public Event addEvent(Event event) throws InputValidationException;

	/**
	 * @param event
	 */
	public void updateEvent(Event event) throws InputValidationException,
			InstanceNotFoundException, ReduceAforoException,
			ExpiredEventException;

	/**
	 * @param eventID
	 */
	public void removeEvent(Long eventID) throws InstanceNotFoundException,
			EventHasAttendeesException;

	/**
	 * @param eventID
	 * @return Event
	 */
	public Event findEventById(Long eventID) throws InstanceNotFoundException;

	/**
	 * @param keywords
	 * @return inicio
	 * @return fin
	 * @returnList<Event>
	 */
	public List<Event> findEvent(String keywords, Calendar inicio, Calendar fin)
			throws InvalidRankDate, InputValidationException;

	/**
	 * @param user
	 * @param eventID
	 * @param type
	 * @param description
	 * @return Reply
	 */
	public Reply replyEvent(String user, Long eventID, Boolean type,
			String description) throws InputValidationException,
			ExpiredEventException, CompleteEventException,
			InstanceNotFoundException;

	/**
	 * @param userID
	 * @param type
	 * @return List<Reply>
	 */
	public List<Reply> getRepliesByUser(String userID, Boolean type);

	/**
	 * @param eventID
	 * @param type
	 * @return List<Reply>
	 */
	public List<Reply> getRepliesByEvent(Long eventID, Boolean type);

}
