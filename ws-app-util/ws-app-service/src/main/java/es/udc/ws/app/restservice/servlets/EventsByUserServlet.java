package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.app.dto.EventByUserDto;
import es.udc.ws.app.model.event.Event;
import es.udc.ws.app.model.eventservice.EventServiceFactory;
import es.udc.ws.app.model.reply.Reply;
import es.udc.ws.app.xml.XmlEventByUserDtoConversor;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class EventsByUserServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String userID = request.getParameter("userID");
		
		if (userID == null) {
			ServletUtils
					.writeServiceResponse(
							response,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid request: 'userId' parameter is mandatory.")),
							null);
			return;
		}
		
		List<Reply> repliedEvents;
		List<EventByUserDto> assistedEvents = new ArrayList<EventByUserDto>();;
		
		Calendar now = Calendar.getInstance();
		repliedEvents = EventServiceFactory.getService().getRepliesByUser(userID,true);
		Long eventID;
		Event event;
		
		for (int i = 0; i < repliedEvents.size(); i++) {
			eventID = repliedEvents.get(i).getEventID();
			try {
				event = EventServiceFactory.getService().findEventById(eventID);
				if (now.after(event.getDateInit())) {
					assistedEvents.add(new EventByUserDto(event.getName(),
							event.getDateInit(), repliedEvents.get(i)
									.getDateReply()));
				}

			} catch (InstanceNotFoundException e) {
//				ServletUtils.writeServiceResponse(response,
//						HttpServletResponse.SC_NOT_FOUND,
//						XmlExceptionConversor.toInstanceNotFoundExceptionXml(e),
//						null);
//				return;
			}
		}
		
		ServletUtils.writeServiceResponse(response,
				HttpServletResponse.SC_OK,
				XmlEventByUserDtoConversor.toXml(assistedEvents),null);		
	}

}
