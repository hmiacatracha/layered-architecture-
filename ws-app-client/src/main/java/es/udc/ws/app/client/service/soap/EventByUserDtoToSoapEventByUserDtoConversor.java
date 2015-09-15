package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import es.udc.ws.app.dto.EventByUserDto;

public class EventByUserDtoToSoapEventByUserDtoConversor {

	public static es.udc.ws.app.client.service.soap.wsdl.EventByUserDto toSoapEventByUserDto(
			EventByUserDto eventByUser) {

		es.udc.ws.app.client.service.soap.wsdl.EventByUserDto toSoapEventByUserDto = new es.udc.ws.app.client.service.soap.wsdl.EventByUserDto();
		toSoapEventByUserDto.setEventName(eventByUser.getEventName());

		GregorianCalendar initDateGregorianCalendar = new GregorianCalendar();
		initDateGregorianCalendar.setTime(eventByUser.getDateInit().getTime());
		try {
			toSoapEventByUserDto.setDateInit(DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(initDateGregorianCalendar));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

		GregorianCalendar replyDateGregorianCalendar = new GregorianCalendar();
		replyDateGregorianCalendar
				.setTime(eventByUser.getDateReply().getTime());
		try {
			toSoapEventByUserDto.setDateReply(DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(replyDateGregorianCalendar));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

		return toSoapEventByUserDto;
	}

	public static EventByUserDto toEventByUserDto(
			es.udc.ws.app.client.service.soap.wsdl.EventByUserDto eventByUser) {

		return new EventByUserDto(eventByUser.getEventName(), eventByUser
				.getDateInit().toGregorianCalendar(), eventByUser
				.getDateReply().toGregorianCalendar());
	}

	public static List<EventByUserDto> toEventByUserDtos(
			List<es.udc.ws.app.client.service.soap.wsdl.EventByUserDto> eventsByUser) {

		List<EventByUserDto> eventByUserDtos = new ArrayList<>(
				eventsByUser.size());
		for (int i = 0; i < eventsByUser.size(); i++) {
			es.udc.ws.app.client.service.soap.wsdl.EventByUserDto eventByUser = eventsByUser
					.get(i);
			eventByUserDtos.add(toEventByUserDto(eventByUser));

		}
		return eventByUserDtos;
	}
}
