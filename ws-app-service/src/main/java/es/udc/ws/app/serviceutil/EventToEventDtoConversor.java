package es.udc.ws.app.serviceutil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.dto.EventDto;
import es.udc.ws.app.model.event.Event;

public class EventToEventDtoConversor {

	public static List<EventDto> toEventDtos(List<Event> events) {
		List<EventDto> eventDtos = new ArrayList<>(events.size());
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			eventDtos.add(toEventDto(event));
		}
		return eventDtos;
	}

	public static EventDto toEventDto(Event event) {
		long duration = (event.getDateFin().getTimeInMillis() - event
				.getDateInit().getTimeInMillis()) / 60000;

		return new EventDto(event.getEventID(), event.getName(),
				event.getDescription(), event.getDateInit(), duration,
				event.getAforo(), event.getNumAttendees());
	}

	public static Event toEvent(EventDto eventDto) {
		Calendar dateFin = Calendar.getInstance();
		dateFin.setTimeInMillis(eventDto.getDateInit().getTimeInMillis() + eventDto.getDuration()*60000);
		
		return new Event(eventDto.getEventID(), eventDto.getName(),
				eventDto.getDescription(), eventDto.getDateInit(), dateFin,
				eventDto.getAforo());
	}
}
