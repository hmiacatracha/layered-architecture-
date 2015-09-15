package es.udc.ws.app.model.reply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class Jdbc3CcSqlReplyDao extends AbstractSqlReplyDao {

	@Override
	public Reply createReply(Connection connection, Reply reply) {
		/* Create "queryString". */
		String queryString = "INSERT INTO Reply"
				+ " (response, description, dateResponse, userID, eventID)"
				+ " VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				queryString, Statement.RETURN_GENERATED_KEYS)) {

			/* Fill "preparedStatement". */
			int i = 1;

			preparedStatement.setBoolean(i++, reply.getTypeReply());
			preparedStatement.setString(i++, reply.getDescription());
			Timestamp date = reply.getDateReply() != null ? new Timestamp(reply
					.getDateReply().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, date);
			preparedStatement.setString(i++, reply.getUserID());
			preparedStatement.setLong(i++, reply.getEventID());

			/* Execute query. */
			preparedStatement.executeUpdate();

			/* Get generated identifier. */
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if (!resultSet.next()) {
				throw new SQLException(
						"JDBC driver did not return generated key.");
			}
			Long replyID = resultSet.getLong(1);

			/* Return reply. */
			return new Reply(replyID, reply.getDescription(),
					reply.getUserID(), reply.getEventID(), reply.getTypeReply());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	

}
