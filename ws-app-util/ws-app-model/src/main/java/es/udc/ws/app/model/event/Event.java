package es.udc.ws.app.model.event;

import java.util.Calendar;

/*
 * entity es.udc.ws.movies.model.movie.Movie 
 *  its gets and set methods 
 *  method override hash
 *  and method override equals
 */

public class Event {

	private String name;
	private String description;
	private Calendar dateInit;
	private Calendar dateFin;
	private int aforo;
	private Long eventID;
	private Long attendees;

	/*
 * 
 */
	public Event(String name, String description, Calendar dateInit,
			Calendar dateFin, int aforo) {
		this.setName(name);
		this.setDescription(description);
		this.dateInit = dateInit;
		if (dateInit != null) {
			this.dateInit.set(Calendar.MILLISECOND, 0);
		}
		this.dateFin = dateFin;
		if (dateFin != null) {
			this.dateFin.set(Calendar.MILLISECOND, 0);
		}
		this.setAforo(aforo);
		this.attendees = (long) 0;

	}

	public Event(Long eventId, String name, String description,
			Calendar dateInit, Calendar dateFin, int aforo) {
		this(name, description, dateInit, dateFin, aforo);
		this.setEventId(eventId);
	}

	public Event(Long eventId, String name, String description,
			Calendar dateInit, Calendar dateFin, int aforo, long attendees) {
		this(name, description, dateInit, dateFin, aforo);
		this.setEventId(eventId);
		this.setNumAttendees(attendees);
	}

	/*
	 * set and get Methods
	 */

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

	public int getAforo() {
		return aforo;
	}

	public void setAforo(int aforo) {
		this.aforo = aforo;
	}

	public Long getEventID() {
		return eventID;
	}

	public Long getNumAttendees() {
		return attendees;
	}

	public void setNumAttendees(long attendees) {
		this.attendees = attendees;
	}

	public void setEventId(Long eventId) {
		this.eventID = eventId;
	}

	public Calendar getDateInit() {
		return this.dateInit;
	}

	public void setDateInit(Calendar dateInit) {
		this.dateInit = dateInit;
		if (dateInit != null) {
			this.dateInit.set(Calendar.MILLISECOND, 0);
		}
	}

	public Calendar getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Calendar dateFin) {
		this.dateFin = dateFin;
		if (dateFin != null) {
			this.dateFin.set(Calendar.MILLISECOND, 0);
		}
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
		result = prime * result + aforo;
		result = prime * result
				+ ((attendees == null) ? 0 : attendees.hashCode());
		result = prime * result + ((dateFin == null) ? 0 : dateFin.hashCode());
		result = prime * result
				+ ((dateInit == null) ? 0 : dateInit.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((eventID == null) ? 0 : eventID.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Event other = (Event) obj;
		if (aforo != other.aforo)
			return false;
		if (attendees == null) {
			if (other.attendees != null)
				return false;
		} else if (!attendees.equals(other.attendees))
			return false;
		if (dateFin == null) {
			if (other.dateFin != null)
				return false;
		} else if (!dateFin.equals(other.dateFin))
			return false;
		if (dateInit == null) {
			if (other.dateInit != null)
				return false;
		} else if (!dateInit.equals(other.dateInit))
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}