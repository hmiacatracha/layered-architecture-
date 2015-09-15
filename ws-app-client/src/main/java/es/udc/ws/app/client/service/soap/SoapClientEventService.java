package es.udc.ws.app.client.service.soap;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import es.udc.ws.app.client.service.soap.wsdl.EventsProvider;
import es.udc.ws.app.client.service.ClientEventService;
import es.udc.ws.app.client.service.soap.wsdl.EventsProviderService;
import es.udc.ws.app.client.service.soap.wsdl.SoapCompleteEventException;
import es.udc.ws.app.client.service.soap.wsdl.SoapEventHasAttendeesException;
import es.udc.ws.app.client.service.soap.wsdl.SoapExpiredEventException;
import es.udc.ws.app.client.service.soap.wsdl.SoapInputValidationException;
import es.udc.ws.app.client.service.soap.wsdl.SoapInstanceNotFoundException;
import es.udc.ws.app.client.service.soap.wsdl.SoapInvalidRankDateException;
import es.udc.ws.app.client.service.soap.wsdl.SoapReduceAforoException;
import es.udc.ws.app.dto.EventByUserDto;
import es.udc.ws.app.dto.EventDto;
import es.udc.ws.app.dto.ReplyDto;
import es.udc.ws.app.exceptions.CompleteEventException;
import es.udc.ws.app.exceptions.EventHasAttendeesException;
import es.udc.ws.app.exceptions.ExpiredEventException;
import es.udc.ws.app.exceptions.InvalidRankDate;
import es.udc.ws.app.exceptions.ReduceAforoException;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class SoapClientEventService implements ClientEventService {

	private final static String ENDPOINT_ADDRESS_PARAMETER = "SoapClientEventService.endpointAddress";

	private String endpointAddress;

	private EventsProvider eventsProvider;

	public SoapClientEventService() {
		init(getEndpointAddress());
	}

	private String getEndpointAddress() {

		if (endpointAddress == null) {
			endpointAddress = ConfigurationParametersManager
					.getParameter(ENDPOINT_ADDRESS_PARAMETER);
		}
		return endpointAddress;
	}

	private void init(String eventsProviderURL) {
		EventsProviderService stockQuoteProviderService = new EventsProviderService();
		eventsProvider = stockQuoteProviderService.getEventsProviderPort();

		((BindingProvider) eventsProvider).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, eventsProviderURL);
	}

	@Override
	public EventDto addEvent(EventDto eventDto) throws InputValidationException {
		try {

			return EventDtoToSoapEventDtoConversor.toEventDto(eventsProvider
					.addEvent(EventDtoToSoapEventDtoConversor
							.toSoapEventDto(eventDto)));

		} catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getMessage());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateEvent(EventDto event) throws InputValidationException,
			InstanceNotFoundException, ReduceAforoException,
			ExpiredEventException {

		try {
			eventsProvider.updateEvent(EventDtoToSoapEventDtoConversor
					.toSoapEventDto(event));

		} catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getMessage());

		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());

		} catch (SoapReduceAforoException e) {
			throw new ReduceAforoException(e.getMessage());

		} catch (SoapExpiredEventException e) {
			throw new ExpiredEventException(e.getMessage());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void removeEvent(Long eventID) throws InstanceNotFoundException,
			EventHasAttendeesException {

		try {
			eventsProvider.removeEvent(eventID);

		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());

		} catch (SoapEventHasAttendeesException e) {
			throw new EventHasAttendeesException(e.getMessage());
		}
	}

	@Override
	public EventDto findEventById(Long eventID)
			throws InstanceNotFoundException {

		try {
			// Conversor en wsdl
			return EventDtoToSoapEventDtoConversor.toEventDto(eventsProvider
					.findEventById(eventID));

		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());
		}

	}

	@Override
	public List<EventDto> findEvent(String keywords, Calendar dateInit,
			Calendar dateFin) throws InvalidRankDate, InputValidationException {
		try {

			GregorianCalendar initG = new GregorianCalendar();
			GregorianCalendar finG = new GregorianCalendar();
			XMLGregorianCalendar initXmlG;
			XMLGregorianCalendar finXmlG;

			if (dateInit != null) {
				initG.setTime(dateInit.getTime());
				initXmlG = DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(initG);
			} else
				initXmlG = null;

			if (dateFin != null) {
				finG.setTime(dateFin.getTime());
				finXmlG = DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(finG);
			} else
				finXmlG = null;

			return EventDtoToSoapEventDtoConversor.toEventDtos(eventsProvider
					.findEvent(keywords, initXmlG, finXmlG));

		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getMessage());

		} catch (SoapInvalidRankDateException e) {
			throw new InvalidRankDate(e.getMessage());
		}
	}

	@Override
	public ReplyDto replyEvent(Long eventId, String userId, Boolean type)
			throws InstanceNotFoundException, InputValidationException,
			ExpiredEventException, CompleteEventException {

		try {
			// Hacer clase para convertir dto a soapdto

			ReplyDto replyDto = ReplyDtoToSoapReplyDtoConversor
					.toReplyDto(eventsProvider
							.replyEvent(userId, eventId, type));

			return replyDto;

		} catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getMessage());

		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo()
					.getInstanceId(), e.getFaultInfo().getInstanceType());

		} catch (SoapExpiredEventException e) {
			throw new ExpiredEventException(e.getMessage());

		} catch (SoapCompleteEventException e) {
			throw new CompleteEventException(e.getMessage());
		}

	}

	@Override
	public List<ReplyDto> getRepliesByEvent(Long eventId, Boolean type) {

		return ReplyDtoToSoapReplyDtoConversor.toReplyDtos(eventsProvider
				.getRepliesByEvent(eventId, type));

	}

	@Override
	public List<ReplyDto> getRepliesByUser(String userId, Boolean type) {

		return ReplyDtoToSoapReplyDtoConversor.toReplyDtos(eventsProvider
				.getRepliesByUser(userId, type));

	}

	@Override
	public List<EventByUserDto> assistedEventsByUser(String userId) {

		return EventByUserDtoToSoapEventByUserDtoConversor
				.toEventByUserDtos(eventsProvider.assistedEventsByUser(userId));
	}

}
