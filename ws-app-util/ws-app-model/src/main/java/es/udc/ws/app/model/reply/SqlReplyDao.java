package es.udc.ws.app.model.reply;

import java.sql.Connection;
import java.util.List;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface SqlReplyDao {

	/**
	 * @param connection
	 * @param reply
	 * @return Reply
	 */
	public Reply createReply(Connection connection, Reply reply);

	/**
	 * @param connection
	 * @param eventID
	 * @param type
	 * @return List<Reply>
	 */
	public List<Reply> getRepliesByEvent(Connection connection, Long eventID,
			Boolean type);

	/**
	 * @param connection
	 * @param userID
	 * @param type
	 * @return List<Reply>
	 */
	public List<Reply> getRepliesByUser(Connection connection, String userID,
			Boolean type);
	
	/**
	 * @param connection
	 * @param reply
	 */
	public void updateReply(Connection connection, Reply reply)
			throws InstanceNotFoundException;

	
	/**
	 * @param connection
	 * @param replyId
	 * @throws InstanceNotFoundException
	 */
	public void deleteReply(Connection connection, Long replyId)
			throws InstanceNotFoundException;

	/**
	 * @param connection
	 * @param eventID
	 * @param userID
	 * @throws InstanceNotFoundException
	 */
	public Reply getRepliesByUserAndEvent(Connection connection, Long eventID,
			String userID);

}