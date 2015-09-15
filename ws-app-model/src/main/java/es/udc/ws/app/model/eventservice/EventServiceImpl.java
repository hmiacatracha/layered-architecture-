package es.udc.ws.app.model.eventservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import es.udc.ws.app.exceptions.CompleteEventException;
import es.udc.ws.app.exceptions.EventHasAttendeesException;
import es.udc.ws.app.exceptions.ExpiredEventException;
import es.udc.ws.app.exceptions.InvalidRankDate;
import es.udc.ws.app.exceptions.ReduceAforoException;
import es.udc.ws.app.model.event.Event;
import es.udc.ws.app.model.event.SqlEventDao;
import es.udc.ws.app.model.event.SqlEventDaoFactory;
import es.udc.ws.app.model.reply.Reply;
import es.udc.ws.app.model.reply.SqlReplyDao;
import es.udc.ws.app.model.reply.SqlReplyDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.validation.PropertyValidator;
import static es.udc.ws.app.model.util.ModelConstants.EVENT_DATA_SOURCE;

public class EventServiceImpl implements EventService {

	private DataSource dataSource;
	private SqlEventDao eventDao = null;
	private SqlReplyDao replyDao = null;

	public EventServiceImpl() {
		dataSource = DataSourceLocator.getDataSource(EVENT_DATA_SOURCE);
		eventDao = SqlEventDaoFactory.getDao();
		replyDao = SqlReplyDaoFactory.getDao();
	}

	/*
	 * Validate EVENT, verify every fields format
	 * 
	 * @param event
	 * 
	 * @throw InputValidationException
	 */

	private void validateEvent(Event event) throws InputValidationException {

		PropertyValidator.validateMandatoryString("name", event.getName());
		PropertyValidator.validateMandatoryString("description",
				event.getDescription());

		Calendar now = Calendar.getInstance();

		if ((event.getDateInit() == null) || (event.getDateInit().before(now))) {
			throw new InputValidationException(
					"Invalid initDate value (it must be a lastest date)");
		}

		if ((event.getDateFin() == null) || (event.getDateFin().before(now))
				|| (event.getDateFin().before(event.getDateInit()))) {
			throw new InputValidationException(
					"Invalid finDate value (it must be a lastest date)");
		}
		PropertyValidator.validateLong("aforo", event.getAforo(), -1, 1000000);
		PropertyValidator.validateLong("attendees", event.getNumAttendees(), 0,
				1000000);
	}

	@Override
	public Event addEvent(Event event) throws InputValidationException {

		validateEvent(event);

		try (Connection connection = dataSource.getConnection()) {

			try {

				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				Event createEvent = eventDao.createEvent(connection, event);
				connection.commit();

				return createEvent;

			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException | Error e) {
				connection.rollback();
				throw e;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateEvent(Event afterEvent) throws InputValidationException,
			InstanceNotFoundException, ReduceAforoException,
			ExpiredEventException {

		validateEvent(afterEvent);

		try (Connection connection = dataSource.getConnection()) {

			try {
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				Event beforeEvent = eventDao.findEventByID(connection,
						afterEvent.getEventID());

				/* Verify that event is not started or finished */
				Calendar today = Calendar.getInstance();

				if (today.after(beforeEvent.getDateInit())
						|| today.after(beforeEvent.getDateFin())) {
					throw new ExpiredEventException(
							"You can't update, this event has expired");
				}

				/* Verify new aforo is not less than attends number */
				long attends = replyDao.getRepliesByEvent(connection,
						afterEvent.getEventID(), true).size();

				if ((afterEvent.getAforo() != -1)
						&& (afterEvent.getAforo() < attends)) {
					throw new ReduceAforoException(
							"The event could not be updated because the foro (capacity) is less");

				}

				/* If all is fine it will execute updateEvent query */
				eventDao.updateEvent(connection, afterEvent);
				connection.commit();

			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw e;

			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);

			} catch (RuntimeException | Error e) {
				connection.rollback();
				throw e;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void removeEvent(Long eventID) throws InstanceNotFoundException,
			EventHasAttendeesException {

		try (Connection connection = dataSource.getConnection()) {

			try {
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				int positivesReplies = replyDao.getRepliesByEvent(connection,
						eventID, true).size();

				if (positivesReplies > 0) {
					throw new EventHasAttendeesException(
							"You could not delete this event because the event has attendees");
				}

				List<Reply> negativeReplies = replyDao.getRepliesByEvent(
						connection, eventID, false);

				int num_negativeReplies = negativeReplies.size();
				if (num_negativeReplies > 0) {
					for (int j = 0; j < num_negativeReplies; j++) {
						replyDao.deleteReply(connection, negativeReplies.get(j)
								.getReplyID());
					}
				}

				eventDao.deleteEvent(connection, eventID);
				connection.commit();

			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw e;
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);

			} catch (RuntimeException | Error e) {
				connection.rollback();
				throw e;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);

		}
	}

	@Override
	public Event findEventById(Long eventID) throws InstanceNotFoundException {

		try (Connection connection = dataSource.getConnection()) {

			return eventDao.findEventByID(connection, eventID);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Event> findEvent(String keywords, Calendar inicio, Calendar fin)
			throws InvalidRankDate, InputValidationException {

		if ((inicio != null) && (fin != null) && (inicio.after(fin))) {
			throw new InvalidRankDate("Init date is later than finish date");
		}

		if (((inicio == null) && (fin != null)) || (inicio != null)
				&& (fin == null)) {
			throw new InputValidationException(
					"Init date or Finish date can't be null");
		}

		try (Connection connection = dataSource.getConnection()) {

			return eventDao.findEvent(connection, keywords, inicio, fin);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/*
	 * Validate REPLY, verify every fields format
	 * 
	 * @param reply
	 * 
	 * @throw InputValidationException
	 */

	private void validateReply(String user, Long eventID, Boolean type,
			String description) throws InputValidationException {

		if (type && description != null) {

			throw new InputValidationException(
					"No responder con respuesta afirmativa");
		}

		PropertyValidator.validateMandatoryString("userID", user);
		PropertyValidator.validateLong("eventID", eventID, 0, 100000000);
	}

	@Override
	public Reply replyEvent(String user, Long eventID, Boolean type,
			String description) throws InputValidationException,
			ExpiredEventException, CompleteEventException,
			InstanceNotFoundException {

		validateReply(user, eventID, type, description);

		try (Connection connection = dataSource.getConnection()) {

			try {

				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				Event event = eventDao.findEventByID(connection, eventID);

				Reply reply = new Reply(description, user, eventID, type);

				if (reply.getDateReply().after(event.getDateInit())
						|| reply.getDateReply().after(event.getDateFin())) {
					throw new ExpiredEventException(
							"You can't reply to this event");
				}

				if (reply.getTypeReply() == true) {

					if (event.getAforo() > -1) { // si es un evento privado
						// y el numero de asistentes es >= aforo
						if (event.getNumAttendees() >= event.getAforo()) {
							throw new CompleteEventException(
									"The event is full");
						}
					}
				}

				Reply replyByUserAndEvent = replyDao.getRepliesByUserAndEvent(
						connection, eventID, user);

				// el usuario ya tiene una respuesta a ese evento
				if (replyByUserAndEvent != null) {

					// Si la que habia es positiva y la nueva reply es negativa,
					// el numero de asistentes disminuye en 1
					if (replyByUserAndEvent.getTypeReply()
							&& !reply.getTypeReply()) {
						event.setNumAttendees(event.getNumAttendees() - 1);
					}

					// Si la que habia es negativa y la nueva reply es
					// afirmativa, el numero de asistentes aumenta en 1
					if (!replyByUserAndEvent.getTypeReply()
							&& reply.getTypeReply()) {
						event.setNumAttendees(event.getNumAttendees() + 1);
					}

					eventDao.updateEvent(connection, event);

					replyByUserAndEvent.setTypeReply(type);
					replyDao.updateReply(connection, replyByUserAndEvent);

					connection.commit();

					return replyByUserAndEvent;
				}

				Reply response = replyDao.createReply(connection, reply);
				// Si es una respuesta afirmativa, actualizar numero de
				// asistentes
				if (response.getTypeReply()) {
					event.setNumAttendees(event.getNumAttendees() + 1);
					eventDao.updateEvent(connection, event);
				}

				connection.commit();
				return response;

			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw e;

			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException | Error e) {
				connection.rollback();
				throw e;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Reply> getRepliesByEvent(Long eventID, Boolean type) {

		try (Connection connection = dataSource.getConnection()) {

			return replyDao.getRepliesByEvent(connection, eventID, type);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Reply> getRepliesByUser(String userID, Boolean type) {

		try (Connection connection = dataSource.getConnection()) {

			return replyDao.getRepliesByUser(connection, userID, type);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}