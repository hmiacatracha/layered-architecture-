package es.udc.ws.app.model.reply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.model.event.Event;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlReplyDao implements SqlReplyDao {

	@Override
	public List<Reply> getRepliesByUser(Connection connection, String userID, Boolean type) {

		/* Create "queryString". */
		String queryString = "SELECT responseId, description, dateResponse, userId, eventId, response "
				+ "FROM Reply WHERE userId = ?";
		
		if (type != null) {
			queryString += " AND response = ?";
		}

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			preparedStatement.setString(1, userID);
			
			if (type != null) {
				preparedStatement.setBoolean(2, type);
			}
			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();
			
			/* Read movies. */
			List<Reply> replies = new ArrayList<Reply>();

			while (resultSet.next()) {

				/* Get results. */
				int i = 1;
				Long replyID = resultSet.getLong(i++);
				String description = resultSet.getString(i++);
				Calendar date = Calendar.getInstance();
				date.setTime(resultSet.getTimestamp(i++));
				String user = resultSet.getString(i++);
				Long eventID = resultSet.getLong(i++);

				Boolean response = resultSet.getBoolean(i++);
				replies.add(new Reply(replyID, description, date, user, eventID,
						response));
			}

			/* Return replies. */
			return replies;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<Reply> getRepliesByEvent(Connection connection, Long eventID,
			Boolean type) {

		/* Create "queryString". */
		String queryString = "SELECT responseId, description, dateResponse, userId, eventId, response "
				+ "FROM Reply WHERE eventId = ?";

		if (type != null) {
			queryString += " AND response = ?";
		}
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			preparedStatement.setLong(1, eventID);

			if (type != null) {
				preparedStatement.setBoolean(2, type);
			}
			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Reply> replies = new ArrayList<Reply>();
			
			while (resultSet.next()) {

				/* Get results. */
				int i = 1;
				Long replyID = resultSet.getLong(i++);
				String description = resultSet.getString(i++);
				Calendar date = Calendar.getInstance();
				date.setTime(resultSet.getTimestamp(i++));
				String user = resultSet.getString(i++);
				Long event = resultSet.getLong(i++);
				Boolean response = resultSet.getBoolean(i++);

				replies.add(new Reply(replyID, description, date, user, event,
						response));
			}

			/* Return replies. */
			return replies;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateReply(Connection connection, Reply reply)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "UPDATE Reply"
				+ " SET response = ?, description = ?, dateResponse = ?"
				+ " WHERE responseId= ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setBoolean(i++, reply.getTypeReply());
			preparedStatement.setString(i++, reply.getDescription());
			Timestamp date = reply.getDateReply() != null ? new Timestamp(reply
					.getDateReply().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, date);
			preparedStatement.setLong(i++, reply.getReplyID());

			/* Execute query. */
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(reply.getReplyID(),
						Reply.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteReply(Connection connection, Long replyId)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "DELETE FROM Reply WHERE responseId = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, replyId);
			/* Execute query. */
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(replyId,
						Reply.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Reply getRepliesByUserAndEvent(Connection connection, Long eventID,
			String userID) {

		/* Create "queryString". */
		String queryString = "SELECT responseId, description, dateResponse, userId, eventId, response "
				+ "FROM Reply WHERE eventId = ? AND userId = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			preparedStatement.setLong(1, eventID);
			preparedStatement.setString(2, userID);

			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			/* Read movies. */
			Reply reply = null;

			while (resultSet.next()) {

				/* Get results. */
				int i = 1;
				Long replyID = resultSet.getLong(i++);
				String description = resultSet.getString(i++);
				Calendar date = Calendar.getInstance();
				date.setTime(resultSet.getTimestamp(i++));
				String user = resultSet.getString(i++);
				Long event = resultSet.getLong(i++);
				Boolean response = resultSet.getBoolean(i++);

				reply = new Reply(replyID, description, user, event, response);
			}

			/* Return replies. */
			return reply;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
