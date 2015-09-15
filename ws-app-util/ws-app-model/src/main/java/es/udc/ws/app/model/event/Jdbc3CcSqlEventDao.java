package es.udc.ws.app.model.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import es.udc.ws.app.model.event.AbstractSqlEventDao;

public class Jdbc3CcSqlEventDao extends AbstractSqlEventDao {

	@Override
	public Event createEvent(Connection connection, Event event) {

		String createQuery = "INSERT INTO Event (name, description,dateInit, dateFin, aforo) "
				+ "VALUES (?,?,?,?,?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				createQuery, Statement.RETURN_GENERATED_KEYS)) {

			int parameter = 1;
			preparedStatement.setString(parameter++, event.getName());
			preparedStatement.setString(parameter++, event.getDescription());
			Timestamp dateInit = event.getDateInit() != null ? new Timestamp(
					event.getDateInit().getTime().getTime()) : null;
			preparedStatement.setTimestamp(parameter++, dateInit);
			Timestamp getDateFin = event.getDateFin() != null ? new Timestamp(
					event.getDateFin().getTime().getTime()) : null;
			preparedStatement.setTimestamp(parameter++, getDateFin);
			preparedStatement.setLong(parameter++, event.getAforo());

			/* Execute query. */
			int createExec = preparedStatement.executeUpdate();

			/* Get generated identifier. */
			ResultSet createdRows = preparedStatement.getGeneratedKeys();

			if (createExec == 0) {
				throw new SQLException(
						"The event is not added in the data base");
			}
			if (createExec > 1) {
				throw new SQLException(
						"The event is duplicated in the data base");
			}
			if (!createdRows.next()) {
				throw new SQLException(
						"JDBC driver did not return generated key.");
			}

			Long eventId = createdRows.getLong(1);

			/* Return event. */
			return new Event(eventId, event.getName(), event.getDescription(),
					event.getDateInit(), event.getDateFin(), event.getAforo());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
