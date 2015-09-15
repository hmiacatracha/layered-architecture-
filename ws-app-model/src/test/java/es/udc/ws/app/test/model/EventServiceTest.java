package es.udc.ws.app.test.model;

import static es.udc.ws.app.model.util.ModelConstants.EVENT_DATA_SOURCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.sql.DataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import es.udc.ws.app.exceptions.CompleteEventException;
import es.udc.ws.app.exceptions.EventHasAttendeesException;
import es.udc.ws.app.exceptions.ExpiredEventException;
import es.udc.ws.app.exceptions.InvalidRankDate;
import es.udc.ws.app.exceptions.ReduceAforoException;
import es.udc.ws.app.model.eventservice.EventServiceFactory;
import es.udc.ws.app.model.eventservice.EventService;
import es.udc.ws.app.model.reply.Reply;
import es.udc.ws.app.model.reply.SqlReplyDao;
import es.udc.ws.app.model.reply.SqlReplyDaoFactory;
import es.udc.ws.app.model.event.Event;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;

public class EventServiceTest {

	private final long NON_EXISTENT_EVENT_ID = -1;
	private final long NON_EXISTENT_REPLY_ID = -1;

	private static EventService eventService = null;

	private static SqlReplyDao replyDao = null;

	@BeforeClass
	public static void init() {

		DataSource dataSource = new SimpleDataSource();

		DataSourceLocator.addDataSource(EVENT_DATA_SOURCE, dataSource);

		eventService = EventServiceFactory.getService();

		replyDao = SqlReplyDaoFactory.getDao();

	}

	private Event getValidPublicEvent() {

		Calendar inicio = Calendar.getInstance();
		inicio.set(2015, 7, 1);

		Calendar fin = Calendar.getInstance();
		fin.set(2015, 8, 1);

		return new Event("Evento1", "Descripcion evento", inicio, fin, -1);
	}

	private Event getValidPrivateEvent() {

		Calendar inicio = Calendar.getInstance();
		inicio.set(2015, 7, 1);

		Calendar fin = Calendar.getInstance();
		fin.set(2015, 8, 1);

		return new Event("Evento1", "Descripcion evento", inicio, fin, 1);
	}

	private Reply getValidAfirmativeReply(Long eventID) {

		return new Reply(null, "usuario 1", eventID, true);
	}

	private Reply getValidNegativeReply(Long eventID) {

		return new Reply("Descripcion respuesta", "usuario 1", eventID, false);
	}

	private Event createEvent(Event event) throws InputValidationException {

		Event addedEvent = null;

		addedEvent = eventService.addEvent(event);

		return addedEvent;
	}

	private Reply createReply(Reply reply) throws InputValidationException,
			ExpiredEventException, CompleteEventException,
			InstanceNotFoundException {

		Reply addedReply = null;

		addedReply = eventService.replyEvent(reply.getUserID(),
				reply.getEventID(), reply.getTypeReply(),
				reply.getDescription());

		return addedReply;
	}

	private void removeEvent(Long eventID) throws EventHasAttendeesException {

		try {
			eventService.removeEvent(eventID);
		} catch (InstanceNotFoundException e) {
			throw new RuntimeException(e);
		}

	}

	private void removeReply(Long replyID) {

		DataSource dataSource = DataSourceLocator
				.getDataSource(EVENT_DATA_SOURCE);

		try (Connection connection = dataSource.getConnection()) {

			try {
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				replyDao.deleteReply(connection, replyID);

				connection.commit();

			} catch (InstanceNotFoundException e) {
				throw new RuntimeException(e);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testAddInvalidEvent() throws EventHasAttendeesException {
		Event event = getValidPrivateEvent();
		Event addedEvent = null;
		boolean exception = false;

		try {
			// Check name
			event.setName(null);
			try {
				addedEvent = eventService.addEvent(event);
			} catch (InputValidationException e) {
				exception = true;
			}
			assertTrue(exception);

			exception = false;
			event = getValidPrivateEvent();
			event.setName("");
			try {
				addedEvent = eventService.addEvent(event);
			} catch (InputValidationException e) {
				exception = true;
			}
			assertTrue(exception);

			// Check description
			exception = false;
			event.setDescription(null);
			try {
				addedEvent = eventService.addEvent(event);
			} catch (InputValidationException e) {
				exception = true;
			}
			assertTrue(exception);

			exception = false;
			event = getValidPrivateEvent();
			event.setDescription("");
			try {
				addedEvent = eventService.addEvent(event);
			} catch (InputValidationException e) {
				exception = true;
			}
			assertTrue(exception);

			// Check Dateinit
			exception = false;
			event = getValidPrivateEvent();
			event.setDateInit(null);
			try {
				addedEvent = eventService.addEvent(event);
			} catch (InputValidationException e) {
				exception = true;
			}
			assertTrue(exception);

			// Check DateInit in the past
			exception = false;
			event = getValidPrivateEvent();
			Calendar now = Calendar.getInstance();
			now.add(Calendar.MINUTE, -1);
			event.setDateInit(now);
			try {
				addedEvent = eventService.addEvent(event);
			} catch (InputValidationException e) {
				exception = true;
			}
			assertTrue(exception);

			// Check DateFin
			exception = false;
			event = getValidPrivateEvent();
			event.setDateFin(null);
			try {
				addedEvent = eventService.addEvent(event);
			} catch (InputValidationException e) {
				exception = true;
			}
			assertTrue(exception);

			// Check DateFin in the past
			exception = false;
			event = getValidPrivateEvent();
			now = Calendar.getInstance();
			now.add(Calendar.MINUTE, -1);
			event.setDateFin(now);
			try {
				addedEvent = eventService.addEvent(event);
			} catch (InputValidationException e) {
				exception = true;
			}
			assertTrue(exception);

			// Check if DateFin earlier than DateInit
			exception = false;
			event = getValidPrivateEvent();
			Calendar dateInit = Calendar.getInstance();
			Calendar dateFin = Calendar.getInstance();
			dateFin.add(Calendar.MINUTE, -1);
			event.setDateInit(dateInit);
			event.setDateFin(dateFin);
			try {
				addedEvent = eventService.addEvent(event);
			} catch (InputValidationException e) {
				exception = true;
			}
			assertTrue(exception);

			// Check private aforo
			exception = false;
			event = getValidPrivateEvent();
			event.setAforo(-3);
			try {
				addedEvent = eventService.addEvent(event);
			} catch (InputValidationException e) {
				exception = true;
			}
			assertTrue(exception);

		} finally {
			if (!exception) {
				removeEvent(addedEvent.getEventID());
			}
		}
	}

	@Test
	public void testAddEventAndFindEvent() throws InstanceNotFoundException,
			EventHasAttendeesException, InputValidationException {

		Event event = getValidPrivateEvent();
		Event addedEvent = null;
		try {
			addedEvent = eventService.addEvent(event);

			Event foundEvent = eventService.findEventById(addedEvent
					.getEventID());

			assertEquals(addedEvent, foundEvent);
		} finally {
			removeEvent(addedEvent.getEventID());
		}
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentEvent() throws InstanceNotFoundException {

		eventService.findEventById(NON_EXISTENT_EVENT_ID);
	}

	@Test
	public void testFindEvents() throws EventHasAttendeesException,
			InputValidationException, InvalidRankDate {

		List<Event> events = new ArrayList<Event>();

		Event event1 = getValidPrivateEvent();
		event1.setName("event title1");
		Event addedEvent1 = createEvent(event1);
		events.add(addedEvent1);

		Event event2 = getValidPrivateEvent();
		event2.setName("event title2");
		Calendar fechaini = Calendar.getInstance();
		fechaini.set(event2.getDateInit().get(Calendar.YEAR), event2
				.getDateInit().get(Calendar.MONTH),
				event2.getDateInit().get(Calendar.DAY_OF_MONTH) + 10);
		event2.setDateInit(fechaini);
		Event addedEvent2 = createEvent(event2);
		events.add(addedEvent2);

		Event event3 = getValidPrivateEvent();
		event3.setName("event title3");
		fechaini = Calendar.getInstance();
		fechaini.set(event3.getDateInit().get(Calendar.YEAR), event3
				.getDateInit().get(Calendar.MONTH),
				event3.getDateInit().get(Calendar.DAY_OF_MONTH) + 20);
		event3.setDateInit(fechaini);
		Event addedEvent3 = createEvent(event3);
		events.add(addedEvent3);

		try {
			// Buscar solo por keywords
			List<Event> foundEvents = eventService.findEvent("event", null,
					null);

			assertEquals(events, foundEvents);

			foundEvents = null;

			// Buscar solo por rango de fechas
			Calendar inicio = addedEvent2.getDateInit();
			Calendar finEvento = addedEvent2.getDateFin();

			foundEvents = eventService.findEvent(null, inicio, finEvento);

			Event e = events.remove(0);
			removeEvent(e.getEventID());

			assertEquals(events, foundEvents);
			foundEvents = null;

			// Buscar por keywords + rango de fechas
			List<Event> event = new ArrayList<Event>();
			event.add(addedEvent2);
			foundEvents = eventService.findEvent("2", inicio, finEvento);
			assertEquals(event, foundEvents);
			foundEvents = null;
			event = null;

		} finally {

			for (Event event : events) {
				removeEvent(event.getEventID());
			}
		}
	}

	@Test(expected = InvalidRankDate.class)
	public void testFindEventByInvalidRank() throws InvalidRankDate,
			InputValidationException {
		// Rango de fechas : inicio > fin
		Calendar inicio = Calendar.getInstance();
		inicio.add(Calendar.YEAR, 1);

		Calendar fin = Calendar.getInstance();

		eventService.findEvent("1", inicio, fin);
	}

	@Test(expected = InputValidationException.class)
	public void testFindEventFinDateNull() throws InvalidRankDate,
			InputValidationException {
		// Rango de fechas : inicio != null y fin == null

		Calendar inicio = Calendar.getInstance();

		Calendar fin = null;

		eventService.findEvent("1", inicio, fin);
	}

	@Test(expected = InputValidationException.class)
	public void testFindEventInitDateNull() throws InvalidRankDate,
			InputValidationException {
		// Rango de fechas : inicio == null y fin != null

		Calendar inicio = null;

		Calendar fin = Calendar.getInstance();

		eventService.findEvent("1", inicio, fin);
	}

	@Test
	public void testUpdateEvent() throws InputValidationException,
			InstanceNotFoundException, ReduceAforoException,
			ExpiredEventException, EventHasAttendeesException {

		Event event = createEvent(getValidPrivateEvent());

		event.setName("new name");
		Calendar inicio = Calendar.getInstance();
		inicio.add(Calendar.MONTH, Calendar.FEBRUARY);
		Calendar fin = Calendar.getInstance();
		fin.add(Calendar.MONTH, Calendar.MARCH);
		event.setDateInit(inicio);
		event.setDateFin(fin);
		event.setDescription("new description");
		event.setAforo(12);

		try {
			eventService.updateEvent(event);

			Event updatedEvent = eventService.findEventById(event.getEventID());
			assertEquals(event, updatedEvent);

		} finally {
			removeEvent(event.getEventID());
		}
	}

	@Test(expected = InputValidationException.class)
	public void testUpdateInvalidEvent() throws InstanceNotFoundException,
			InputValidationException, ReduceAforoException,
			ExpiredEventException, EventHasAttendeesException {

		Event event = createEvent(getValidPrivateEvent());
		try {
			// Check movie title not null
			event = eventService.findEventById(event.getEventID());
			event.setName(null);
			eventService.updateEvent(event);

		} finally {
			// Clear Database
			removeEvent(event.getEventID());
		}
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testUpdateNonExistentEvent() throws InputValidationException,
			InstanceNotFoundException, ReduceAforoException,
			ExpiredEventException, EventHasAttendeesException {

		Event event = getValidPrivateEvent();
		event.setEventId(NON_EXISTENT_EVENT_ID);		

		eventService.updateEvent(event);
	}

	@Test(expected = ReduceAforoException.class)
	public void testUpdateReduceAforo() throws InputValidationException,
			InstanceNotFoundException, ReduceAforoException,
			ExpiredEventException, EventHasAttendeesException,
			CompleteEventException {

		Event event = createEvent(getValidPrivateEvent());
		Reply reply = createReply(getValidAfirmativeReply(event.getEventID()));
		event.setAforo(0);

		try {			
			eventService.updateEvent(event);
			
		} finally {
			removeReply(reply.getReplyID());
			removeEvent(event.getEventID());
		}
	}

	@Test(expected = InputValidationException.class)
	public void testUpdateStartedEvent() throws InputValidationException,
			InstanceNotFoundException, ReduceAforoException,
			ExpiredEventException, EventHasAttendeesException {

		Event event = createEvent(getValidPrivateEvent());
		Calendar inicio = Calendar.getInstance();
		inicio.add(Calendar.YEAR, -1);
		event.setDateInit(inicio);
		event.setDescription("nueva descripcion");
		try {
			eventService.updateEvent(event);
		} finally {
			removeEvent(event.getEventID());
		}
	}

	@Test(expected = InputValidationException.class)
	public void testUpdateExpiredEvent() throws InputValidationException,
			InstanceNotFoundException, ReduceAforoException,
			ExpiredEventException, EventHasAttendeesException {

		Event event = createEvent(getValidPrivateEvent());
		Calendar fin = Calendar.getInstance();
		fin.add(Calendar.DAY_OF_YEAR, -1);
		event.setDateFin(fin);
		event.setDescription("nueva descripcion");
		try {
			eventService.updateEvent(event);
		} finally {
			removeEvent(event.getEventID());
		}
	}

	@Test
	public void testRemoveValidEvent() throws InputValidationException {

		Event event = createEvent(getValidPrivateEvent());
		boolean exceptionCatched = false;

		try {
			eventService.removeEvent(event.getEventID());
		} catch (InstanceNotFoundException | EventHasAttendeesException e) {
			exceptionCatched = true;
		} finally {
			assertTrue(!exceptionCatched);
		}
	}

	@Test(expected = EventHasAttendeesException.class)
	public void testRemoveEventWithAttendees()
			throws EventHasAttendeesException, InstanceNotFoundException,
			InputValidationException, ExpiredEventException,
			CompleteEventException {

		Event event = getValidPrivateEvent();
		Event addedEvent = createEvent(event);

		Reply reply = getValidAfirmativeReply(addedEvent.getEventID());
		Reply addedReply = createReply(reply);
		try {
			eventService.removeEvent(addedEvent.getEventID());
		} finally {
			removeReply(addedReply.getReplyID());
			removeEvent(addedEvent.getEventID());
		}
	}

	@Test
	public void testRemoveEventWithOutAttendees()
			throws EventHasAttendeesException, InstanceNotFoundException,
			InputValidationException, ExpiredEventException,
			CompleteEventException {

		boolean exceptionCatched = false;

		Event event = getValidPrivateEvent();
		Event addedEvent = createEvent(event);
		Reply reply = getValidNegativeReply(addedEvent.getEventID());
		Reply addedReply = createReply(reply);

		try {

			eventService.removeEvent(addedEvent.getEventID());

		} catch (EventHasAttendeesException e) {
			exceptionCatched = true;
		} finally {
			assertTrue(!exceptionCatched);
		}
	}

	@Test(expected = InputValidationException.class)
	public void testAddInvalidReply() throws InputValidationException,
			ExpiredEventException, CompleteEventException,
			InstanceNotFoundException {

		Reply reply = getValidAfirmativeReply((long) 1);
		reply.setDescription("Descripcion afirmativa");

		createReply(reply);
	}

	@Test
	public void getAllRepliesByEvent() throws InputValidationException,
			ExpiredEventException, CompleteEventException,
			InstanceNotFoundException, EventHasAttendeesException {

		Event event = getValidPrivateEvent();
		Event addedEvent = createEvent(event);

		List<Reply> replies = new ArrayList<Reply>();

		Reply reply1 = getValidNegativeReply(addedEvent.getEventID());
		Reply addedReply1 = createReply(reply1);
		replies.add(addedReply1);

		Reply reply2 = getValidAfirmativeReply(addedEvent.getEventID());
		reply2.setUserID("usuario 2");
		Reply addedReply2 = createReply(reply2);

		replies.add(addedReply2);

		Reply reply3 = getValidNegativeReply(addedEvent.getEventID());
		reply3.setUserID("usuario 3");
		Reply addedReply3 = createReply(reply3);
		replies.add(addedReply3);

		try {
			List<Reply> foundReplies = new ArrayList<Reply>();
			foundReplies = eventService.getRepliesByEvent(
					addedEvent.getEventID(), null);

			assertEquals(replies, foundReplies);
		} finally {
			for (Reply reply : replies) {
				removeReply(reply.getReplyID());
			}
			removeEvent(addedEvent.getEventID());
		}
	}

	@Test
	public void getAfirmativeRepliesByEvent() throws InputValidationException,
			ExpiredEventException, CompleteEventException,
			InstanceNotFoundException, EventHasAttendeesException {

		Event event = getValidPrivateEvent();
		Event addedEvent = createEvent(event);

		List<Reply> replies = new ArrayList<Reply>();

		Reply reply1 = getValidNegativeReply(addedEvent.getEventID());
		Reply addedReply1 = createReply(reply1);

		Reply reply2 = getValidAfirmativeReply(addedEvent.getEventID());
		reply2.setUserID("usuario 2");
		Reply addedReply2 = createReply(reply2);
		replies.add(addedReply2);

		Reply reply3 = getValidNegativeReply(addedEvent.getEventID());
		reply3.setUserID("usuario 3");
		Reply addedReply3 = createReply(reply3);

		try {
			List<Reply> foundReplies = new ArrayList<Reply>();
			foundReplies = eventService.getRepliesByEvent(
					addedEvent.getEventID(), true);

			assertEquals(replies, foundReplies);
		} finally {
			removeReply(addedReply1.getReplyID());
			removeReply(addedReply2.getReplyID());
			removeReply(addedReply3.getReplyID());

			removeEvent(addedEvent.getEventID());

		}
	}

	@Test
	public void getNegativeRepliesByEvent() throws InputValidationException,
			ExpiredEventException, CompleteEventException,
			InstanceNotFoundException, EventHasAttendeesException {

		Event event = getValidPrivateEvent();
		Event addedEvent = createEvent(event);

		List<Reply> replies = new ArrayList<Reply>();

		Reply reply1 = getValidNegativeReply(addedEvent.getEventID());
		Reply addedReply1 = createReply(reply1);
		replies.add(addedReply1);

		Reply reply2 = getValidAfirmativeReply(addedEvent.getEventID());
		reply2.setUserID("usuario 2");
		Reply addedReply2 = createReply(reply2);

		Reply reply3 = getValidNegativeReply(addedEvent.getEventID());
		reply3.setUserID("usuario 3");
		Reply addedReply3 = createReply(reply3);
		replies.add(addedReply3);

		try {
			List<Reply> foundReplies = new ArrayList<Reply>();
			foundReplies = eventService.getRepliesByEvent(
					addedEvent.getEventID(), false);

			assertEquals(replies, foundReplies);
		} finally {
			removeReply(addedReply1.getReplyID());
			removeReply(addedReply2.getReplyID());
			removeReply(addedReply3.getReplyID());

			removeEvent(addedEvent.getEventID());
		}
	}

	@Test
	public void testGetAllRepliesByUser() throws InputValidationException,
			ExpiredEventException, CompleteEventException,
			InstanceNotFoundException, EventHasAttendeesException {

		Event event1 = getValidPrivateEvent();
		Event addedEvent1 = createEvent(event1);

		Event event2 = getValidPrivateEvent();
		event2.setName("Event2");
		Event addedEvent2 = createEvent(event2);

		Reply reply1 = getValidAfirmativeReply(addedEvent1.getEventID());
		Reply addedReply1 = createReply(reply1);

		Reply reply2 = getValidNegativeReply(addedEvent2.getEventID());
		Reply addedReply2 = createReply(reply2);

		List<Reply> replies = new ArrayList<Reply>();
		replies.add(addedReply1);
		replies.add(addedReply2);

		List<Reply> foundReplies = new ArrayList<Reply>();
		foundReplies = eventService.getRepliesByUser("usuario 1", null);

		assertEquals(replies, foundReplies);

		for (Reply reply : replies) {
			removeReply(reply.getReplyID());
		}

		removeEvent(addedEvent1.getEventID());
		removeEvent(addedEvent2.getEventID());
	}

	@Test
	public void testGetAfirmativeRepliesByUser()
			throws InputValidationException, ExpiredEventException,
			CompleteEventException, InstanceNotFoundException,
			EventHasAttendeesException {

		Event event1 = getValidPrivateEvent();
		Event addedEvent1 = createEvent(event1);

		Event event2 = getValidPrivateEvent();
		event2.setName("Event2");
		Event addedEvent2 = createEvent(event2);

		Reply reply1 = getValidAfirmativeReply(addedEvent1.getEventID());
		Reply addedReply1 = createReply(reply1);

		Reply reply2 = getValidNegativeReply(addedEvent2.getEventID());
		createReply(reply2);

		List<Reply> replies = new ArrayList<Reply>();
		replies.add(addedReply1);

		List<Reply> foundReplies = new ArrayList<Reply>();
		foundReplies = eventService.getRepliesByUser("usuario 1", true);

		assertEquals(replies, foundReplies);

		for (Reply reply : replies) {
			removeReply(reply.getReplyID());
		}

		removeEvent(addedEvent1.getEventID());
		removeEvent(addedEvent2.getEventID());
	}

	@Test
	public void testGetNegativeRepliesByUser()
			throws InputValidationException, ExpiredEventException,
			CompleteEventException, InstanceNotFoundException,
			EventHasAttendeesException {

		Event event1 = getValidPrivateEvent();
		Event addedEvent1 = createEvent(event1);

		Event event2 = getValidPrivateEvent();
		event2.setName("Event2");
		Event addedEvent2 = createEvent(event2);

		Reply reply1 = getValidAfirmativeReply(addedEvent1.getEventID());
		Reply addedReply1 = createReply(reply1);

		Reply reply2 = getValidNegativeReply(addedEvent2.getEventID());
		Reply addedReply2 = createReply(reply2);

		List<Reply> replies = new ArrayList<Reply>();
		replies.add(addedReply2);

		List<Reply> foundReplies = new ArrayList<Reply>();
		foundReplies = eventService.getRepliesByUser("usuario 1", false);

		assertEquals(replies, foundReplies);
		
		removeReply(addedReply1.getReplyID());
		removeReply(addedReply2.getReplyID());

		removeEvent(addedEvent1.getEventID());
		removeEvent(addedEvent2.getEventID());
	}
}