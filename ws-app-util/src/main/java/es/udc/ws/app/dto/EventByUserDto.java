package es.udc.ws.app.dto;

import java.util.Calendar;

public class EventByUserDto {

	String eventName;
	Calendar dateInit;
	Calendar dateReply;
	
	public EventByUserDto() {
	}
	
	public EventByUserDto(String name, Calendar dateInit, Calendar dateReply) {
		this.eventName = name;
		this.dateInit = dateInit;
		this.dateReply = dateReply;
	}
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String name) {
		this.eventName = name;
	}
	public Calendar getDateInit() {
		return dateInit;
	}
	public void setDateInit(Calendar dateInit) {
		this.dateInit = dateInit;
	}
	public Calendar getDateReply() {
		return dateReply;
	}
	public void setDateReply(Calendar dateReply) {
		this.dateReply = dateReply;
	}

}
