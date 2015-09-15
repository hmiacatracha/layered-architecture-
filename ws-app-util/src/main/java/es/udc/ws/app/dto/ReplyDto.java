package es.udc.ws.app.dto;

import java.util.Calendar;

public class ReplyDto {
	
	private Long replyID;
	private Calendar dateReply;
	private String userID;
	private Long eventID;
	private Boolean typeReply;
	
	public ReplyDto() {
	}

	public ReplyDto(Long replyID, Calendar dateReply, String userID,
			Long eventID, Boolean typeReply) {
		this.replyID = replyID;
		this.dateReply = dateReply;
		this.userID = userID;
		this.eventID = eventID;
		this.typeReply = typeReply;
	}

	public ReplyDto(Long replyID, String userID, Long eventID, Boolean typeReply) {
		this.replyID = replyID;
		this.userID = userID;
		this.eventID = eventID;
		this.typeReply = typeReply;	
	}
	
	public Long getReplyID() {
		return replyID;
	}

	public void setReplyID(Long replyID) {
		this.replyID = replyID;
	}

	public Calendar getDateReply() {
		return dateReply;
	}

	public void setDateReply(Calendar dateReply) {
		this.dateReply = dateReply;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Long getEventID() {
		return eventID;
	}

	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}

	public Boolean getTypeReply() {
		return typeReply;
	}

	public void setTypeReply(Boolean typeReply) {
		this.typeReply = typeReply;
	}
}
