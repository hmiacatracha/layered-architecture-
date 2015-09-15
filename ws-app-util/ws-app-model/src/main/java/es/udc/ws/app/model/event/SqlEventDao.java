package es.udc.ws.app.model.event;

import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

public interface SqlEventDao {
	
	/*
	 * Create an event
	 * 
	 * @param connection
	 * 
	 * @param event
	 */

	public Event createEvent(Connection connection, Event event);

	/*
	 * Update an event
	 * 
	 * @param connection
	 * 
	 * @param event
	 * 
	 * @throws InstanceNotFoundException
	 */

	public void updateEvent(Connection connection, Event event)
			throws InstanceNotFoundException;

	/*
	 * Delete an event (The eventID is unique)
	 * 
	 * @param connection
	 * 
	 * @param eventID
	 * 
	 * @throws InstanceNotFoundException
	 */

	public void deleteEvent(Connection connection, Long eventID)
			throws InstanceNotFoundException;

	/*
	 * The way to find a event: a)Keywords specify either the name or the
	 * description of event b)or by date range
	 * 
	 * @param connection
	 * 
	 * @param keywords
	 * 
	 * @param inicioDate
	 * 
	 * @param finDate
	 * 
	 * @return List<Event>
	 */
	public List<Event> findEvent(Connection connection, String keywords,
			Calendar inicio, Calendar fin);

	/*
	 * Find an event by ID
	 * 
	 * @param connection
	 * 
	 * @param eventID
	 * 
	 * @return event
	 * 
	 * @throws InstanceNotFoundException
	 */
	public Event findEventByID(Connection connection, Long eventID)
			throws InstanceNotFoundException;

}
