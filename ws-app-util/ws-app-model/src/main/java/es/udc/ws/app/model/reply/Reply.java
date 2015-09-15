package es.udc.ws.app.model.reply;

import java.util.Calendar;

public class Reply {

	private Long replyID;
	private String description;
	private Calendar dateReply;
	private String userID;
	private Long eventID;
	private Boolean typeReply;

	public Reply(String description, String user, Long eventID, Boolean type) {
		this.description = description;
		this.dateReply = Calendar.getInstance();
		this.dateReply.set(Calendar.MILLISECOND, 0);
		this.userID = user;
		this.eventID = eventID;
		this.typeReply = type;
	}

	public Reply(Long replyID, String description, String user, Long eventID,
			Boolean type) {
		this(description, user, eventID, type);
		this.replyID = replyID;
	}
	
	public Reply(Long replyID, String description, Calendar date, String user, Long eventID,
			Boolean type) {
		this(replyID, description, user, eventID, type);
		this.dateReply = date;
	}
	

	public String getDescription() {
		return this.description;
	}

	public Calendar getDateReply() {
		return this.dateReply;
	}

	public String getUserID() {
		return this.userID;
	}

	public Long getReplyID() {
		return this.replyID;
	}

	public Long getEventID() {
		return this.eventID;
	}

	public Boolean getTypeReply() {
		return this.typeReply;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDateReply(Calendar date) {
		this.dateReply = date;
		if (date != null) {
			this.dateReply.set(Calendar.MILLISECOND, 0);
		}
	}

	public void setUserID(String user) {
		this.userID = user;
	}

	public void setReplyID(Long replyID) {
		this.replyID = replyID;
	}

	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}

	public void setTypeReply(Boolean type) {
		this.typeReply = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateReply == null) ? 0 : dateReply.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((eventID == null) ? 0 : eventID.hashCode());
		result = prime * result + ((replyID == null) ? 0 : replyID.hashCode());
		result = prime * result
				+ ((typeReply == null) ? 0 : typeReply.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reply other = (Reply) obj;
		if (dateReply == null) {
			if (other.dateReply != null)
				return false;
		} else if (!dateReply.equals(other.dateReply))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (eventID == null) {
			if (other.eventID != null)
				return false;
		} else if (!eventID.equals(other.eventID))
			return false;
		if (replyID == null) {
			if (other.replyID != null)
				return false;
		} else if (!replyID.equals(other.replyID))
			return false;
		if (typeReply == null) {
			if (other.typeReply != null)
				return false;
		} else if (!typeReply.equals(other.typeReply))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}

}
