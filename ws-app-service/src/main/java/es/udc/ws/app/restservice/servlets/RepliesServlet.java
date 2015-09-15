package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.app.dto.ReplyDto;
import es.udc.ws.app.exceptions.CompleteEventException;
import es.udc.ws.app.exceptions.ExpiredEventException;
import es.udc.ws.app.model.eventservice.EventServiceFactory;
import es.udc.ws.app.model.reply.Reply;
import es.udc.ws.app.serviceutil.ReplyToReplyDtoConversor;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlReplyDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class RepliesServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String eventIDString = request.getParameter("eventID");

		if (eventIDString == null) {
			ServletUtils
					.writeServiceResponse(
							response,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid request: 'eventId' parameter is mandatory.")),
							null);
			return;
		}

		Long eventID;
		try {
			eventID = Long.parseLong(eventIDString);

		} catch (NumberFormatException e) {
			ServletUtils
					.writeServiceResponse(
							response,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "parameter 'eventID' is invalid '"
													+ eventIDString + "'")),
							null);
			return;
		}

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

		String typeReplyString = request.getParameter("type");
		if (typeReplyString == null) {
			ServletUtils
					.writeServiceResponse(
							response,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid request: 'typeReply' parameter is mandatory.")),
							null);
			return;
		}

		Boolean typeReply;
		typeReply = Boolean.parseBoolean(typeReplyString);
		Reply reply;

		try {
			reply = EventServiceFactory.getService().replyEvent(userID,
					eventID, typeReply, null);

		} catch (InputValidationException ex) {
			ServletUtils.writeServiceResponse(response,
					HttpServletResponse.SC_BAD_REQUEST,
					XmlExceptionConversor.toInputValidationExceptionXml(ex),
					null);
			return;

		} catch (ExpiredEventException ex) {
			ServletUtils.writeServiceResponse(response,
					HttpServletResponse.SC_FORBIDDEN,
					XmlExceptionConversor.toExpiredEventExceptionXml(ex), null);
			return;

		} catch (CompleteEventException ex) {
			ServletUtils
					.writeServiceResponse(response,
							HttpServletResponse.SC_FORBIDDEN,
							XmlExceptionConversor
									.toCompleteEventExceptionXml(ex), null);
			return;

		} catch (InstanceNotFoundException ex) {
			ServletUtils.writeServiceResponse(response,
					HttpServletResponse.SC_NOT_FOUND,
					XmlExceptionConversor.toInstanceNotFoundExceptionXml(ex),
					null);
			return;
		}

		ReplyDto replyDto = ReplyToReplyDtoConversor.toReplyDto(reply);

		String replyURL = request.getRequestURL().append("/")
				.append(reply.getReplyID()).toString();
		Map<String, String> headers = new HashMap<>(1);
		headers.put("Location", replyURL);

		ServletUtils.writeServiceResponse(response,
				HttpServletResponse.SC_CREATED,
				XmlReplyDtoConversor.toXml(replyDto), headers);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String typeReplyString = request.getParameter("type");
		Boolean typeReply = null;
		String userID = request.getParameter("userID");
		String eventIDString = request.getParameter("eventID");

		if (userID != null) {

			List<Reply> replies;

			if (typeReplyString != null) {
				typeReply = Boolean.valueOf(typeReplyString);
			}

			replies = EventServiceFactory.getService().getRepliesByUser(userID,
					typeReply);

			List<ReplyDto> repliesDtos = ReplyToReplyDtoConversor
					.toReplyDtos(replies);

			ServletUtils.writeServiceResponse(response,
					HttpServletResponse.SC_OK,
					XmlReplyDtoConversor.toXml(repliesDtos), null);

		} else if (eventIDString != null) {

			Long eventID;
			try {
				eventID = Long.parseLong(eventIDString);
			} catch (NumberFormatException e) {
				ServletUtils
						.writeServiceResponse(
								response,
								HttpServletResponse.SC_BAD_REQUEST,
								XmlExceptionConversor
										.toInputValidationExceptionXml(new InputValidationException(
												"Invalid Request: "
														+ "parameter 'eventId' is invalid '"
														+ eventIDString + "'")),
								null);
				return;
			}

			List<Reply> replies;

			if (typeReplyString != null) {
				typeReply = Boolean.valueOf(typeReplyString);
			}

			replies = EventServiceFactory.getService().getRepliesByEvent(
					eventID, typeReply);

			List<ReplyDto> repliesDtos = ReplyToReplyDtoConversor
					.toReplyDtos(replies);

			ServletUtils.writeServiceResponse(response,
					HttpServletResponse.SC_OK,
					XmlReplyDtoConversor.toXml(repliesDtos), null);
		}

	}

}
