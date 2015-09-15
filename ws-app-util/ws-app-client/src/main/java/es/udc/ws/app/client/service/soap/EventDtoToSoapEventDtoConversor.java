package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import es.udc.ws.app.dto.EventDto;

public class EventDtoToSoapEventDtoConversor {

	public static es.udc.ws.app.client.service.soap.wsdl.EventDto toSoapEventDto(
			EventDto event) {

		es.udc.ws.app.client.service.soap.wsdl.EventDto toSoapEventDto = new es.udc.ws.app.client.service.soap.wsdl.EventDto();
		toSoapEventDto.setEventID(event.getEventID());
		toSoapEventDto.setName(event.getName());
		toSoapEventDto.setDescription(event.getDescription());

		GregorianCalendar initDateGregorianCalendar = new GregorianCalendar();
		initDateGregorianCalendar.setTime(event.getDateInit().getTime());
		try {
			toSoapEventDto.setDateInit(DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(initDateGregorianCalendar));
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
		toSoapEventDto.setDuration(event.getDuration());
		toSoapEventDto.setAforo(event.getAforo());
		toSoapEventDto.setAttendees(event.getAttendees());

		return toSoapEventDto;
	}

	public static EventDto toEventDto(
			es.udc.ws.app.client.service.soap.wsdl.EventDto event) {

		return new EventDto(event.getEventID(), event.getName(),
				event.getDescription(), event.getDateInit()
						.toGregorianCalendar(), event.getDuration(),
				event.getAforo(), event.getAttendees());
	}

	public static List<EventDto> toEventDtos(
			List<es.udc.ws.app.client.service.soap.wsdl.EventDto> events) {
		List<EventDto> eventDtos = new ArrayList<>(events.size());
		for (int i = 0; i < events.size(); i++) {
			es.udc.ws.app.client.service.soap.wsdl.EventDto event = events
					.get(i);
			eventDtos.add(toEventDto(event));

		}
		return eventDtos;
	}

	public static EventDto toEventDto(Long addEvent) {
		// TODO Auto-generated method stub
		return null;
	}
}
