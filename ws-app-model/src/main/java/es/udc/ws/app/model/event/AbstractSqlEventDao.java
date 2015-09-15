package es.udc.ws.app.model.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlEventDao implements SqlEventDao {

	protected AbstractSqlEventDao() {

	}

	/*
	 * Parameterized query. * string,string,date,date,int,*
	 */

	@Override
	public void updateEvent(Connection connection, Event event)
			throws InstanceNotFoundException {

		String updateQuery = "UPDATE Event  SET name = ?, description = ?, dateInit = ?, dateFin = ?,"
				+ "aforo = ?, attendees = ? WHERE eventId	 = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(updateQuery)) {

			/* Fill "preparedStatement". */
			int parameter = 1;
			preparedStatement.setString(parameter++, event.getName());
			preparedStatement.setString(parameter++, event.getDescription());
			preparedStatement.setTimestamp(parameter++, new Timestamp(event
					.getDateInit().getTime().getTime()));
			preparedStatement.setTimestamp(parameter++, new Timestamp(event
					.getDateFin().getTime().getTime()));
			preparedStatement.setInt(parameter++, event.getAforo());
			preparedStatement.setLong(parameter++, event.getNumAttendees());
			preparedStatement.setLong(parameter++, event.getEventID());

			/* EXECUTE UPDATE */
			int rupdatedRows = preparedStatement.executeUpdate();

			/* verify that the query is updated */
			if (rupdatedRows == 0) {
				throw new InstanceNotFoundException(event.getEventID(),
						Event.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);

		}

	}

	@Override
	public void deleteEvent(Connection connection, Long eventId)
			throws InstanceNotFoundException {

		String deleteQuery = "DELETE FROM Event WHERE eventId = ? "
				+ "and ? not in (SELECT eventId FROM Reply)";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(deleteQuery)) {

			/* Fill "preparedStatement". */
			int parameter = 1;
			preparedStatement.setLong(parameter++, eventId);
			preparedStatement.setLong(parameter++, eventId);

			int deletedRows = preparedStatement.executeUpdate();

			if (deletedRows == 0) {
				throw new InstanceNotFoundException(eventId,
						Event.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Event> findEvent(Connection connection, String keywords,
			Calendar initRank, Calendar finRank) {

		/*------------------------- Create SQL QUERY ------------------------------- */
		String findQuery = "SELECT eventId, name, description, dateInit, dateFin, aforo, attendees FROM Event";

		String[] words = null;
		
		if (keywords == null || keywords.isEmpty()) {

			if (initRank != null && finRank != null) {

				findQuery += " WHERE (dateInit >= ? AND dateFin <= ?)";			
			}
		
		} else {
			
			words = keywords != null ? keywords.split(" ") : null;
			
			if (words != null && words.length > 0) {
				findQuery += " WHERE (";
				for (int i = 0; i < words.length; i++) {
					if (i > 0) {
						findQuery += " AND";
					}
					findQuery += " LOWER(name) LIKE LOWER(?)";
				}
	
				findQuery += " OR";
	
				for (int i = 0; i < words.length; i++) {
					
					if (i > 0) {
						findQuery += " AND";
					}
					findQuery += " LOWER(description) LIKE LOWER(?)";
				}
				
				findQuery += ")";
	
				if (initRank != null && finRank != null) {
					
					findQuery += " AND (dateInit >= ? AND dateFin <= ?)";
				}
			}		
		
		}
		
		findQuery += " ORDER BY name";
		/*---------------------------------END-------------------------------------*/

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(findQuery)) {

			/* Fill "preparedStatement". */
			int parameters = 1;

			if (words != null) {

				for (int i = 0; i < words.length; i++) {
					preparedStatement.setString(parameters++, "%" + words[i]
							+ "%");
				}

				for (int i = 0; i < words.length; i++) {
					preparedStatement.setString(parameters++, "%" + words[i]
							+ "%");
				}
			}

			Timestamp init = initRank != null ? new Timestamp(initRank
					.getTime().getTime()) : null;
			Timestamp fin = finRank != null ? new Timestamp(finRank.getTime()
					.getTime()) : null;

			if (init != null && fin != null) {
				preparedStatement.setTimestamp(parameters++, init);
				preparedStatement.setTimestamp(parameters++, fin);
			}

			/* EXECUTE QUERY */
			ResultSet rows = preparedStatement.executeQuery();

			List<Event> events = new ArrayList<Event>();

			int column;
			while (rows.next()) {
				column = 1;
				Long eventId = rows.getLong(column++);
				String name = rows.getString(column++);
				String description = rows.getString(column++);
				Calendar dateInit = Calendar.getInstance();
				Calendar dateFin = Calendar.getInstance();
				dateInit.setTime(rows.getTimestamp(column++));
				dateFin.setTime(rows.getTimestamp(column++));
				int aforo = rows.getInt(column++);
				long attendees = rows.getLong(column++);
				// consulta por respuestas afirmativas
				// devuelve el numero de asistentes
				//
				events.add(new Event(eventId, name, description, dateInit,
						dateFin, aforo, attendees));
			}

			return events;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Event findEventByID(Connection connection, Long eventId)
			throws InstanceNotFoundException {
		String findQueryById = "SELECT name, description, dateInit, dateFin, aforo, attendees"
				+ " FROM Event" + " WHERE eventId = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(findQueryById)) {
			/* Fill "preparedStatement". */
			int parameters = 1;

			preparedStatement.setLong(parameters++, eventId.longValue());

			/* Execute query. */
			ResultSet row = preparedStatement.executeQuery();

			/* Verify the sql result */
			if (!row.next()) {
				throw new InstanceNotFoundException(eventId,
						Event.class.getName());
			}

			/* Get results. */
			int column = 1;
			String name = row.getString(column++);
			String description = row.getString(column++);
			Calendar dateInit = Calendar.getInstance();
			dateInit.setTime(row.getTimestamp(column++));
			Calendar dateFin = Calendar.getInstance();
			dateFin.setTime(row.getTimestamp(column++));
			int aforo = row.getInt(column++);
			long attendees = row.getLong(column++);

			return new Event(eventId, name, description, dateInit, dateFin,
					aforo, attendees);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}