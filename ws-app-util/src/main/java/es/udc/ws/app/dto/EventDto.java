package es.udc.ws.app.dto;

import java.util.Calendar;

public class EventDto {

	private Long eventID;
	private String name;
	private String description;
	private Calendar dateInit;
	private int aforo;
	private long attendees;
	private long duration;

	public EventDto() {
	}

	public EventDto(Long eventID, String name, String description,
			Calendar dateInit, long duration, int aforo, long attendees) {
		this.eventID = eventID;
		this.name = name;
		this.description = description;
		this.dateInit = dateInit;
		this.duration = duration;
		this.aforo = aforo;
		this.attendees = attendees;
	}

	public Long getEventID() {
		return eventID;
	}

	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getDateInit() {
		return dateInit;
	}

	public void setDateInit(Calendar dateInit) {
		this.dateInit = dateInit;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public int getAforo() {
		return aforo;
	}

	public void setAforo(int aforo) {
		this.aforo = aforo;
	}

	public long getAttendees() {
		return attendees;
	}

	public void setAttendees(long attendees) {
		this.attendees = attendees;
	}

	@Override
	public String toString() {
		return "EventDto [name=" + name + ", description=" + description
				+ ", dateInit=" + dateInit + ", duration=" + duration
				+ ", aforo=" + aforo + ", eventID=" + eventID + ", attendees="
				+ attendees + "]";
	}
}
