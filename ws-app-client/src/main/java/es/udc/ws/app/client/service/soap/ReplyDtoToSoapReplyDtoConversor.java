package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.dto.EventDto;
import es.udc.ws.app.dto.ReplyDto;

public class ReplyDtoToSoapReplyDtoConversor {

	public static es.udc.ws.app.client.service.soap.wsdl.ReplyDto toSoapReplyDto(
			ReplyDto reply) {

		es.udc.ws.app.client.service.soap.wsdl.ReplyDto soapReplyDto = new es.udc.ws.app.client.service.soap.wsdl.ReplyDto();

		soapReplyDto.setReplyID(reply.getReplyID());
		soapReplyDto.setEventID(reply.getEventID());
		// Mirar XMLGragorianCalendar
		// soapReplyDto.setDateReply(reply.getDateReply());
		soapReplyDto.setTypeReply(reply.getTypeReply());
		soapReplyDto.setUserID(reply.getUserID());

		return soapReplyDto;
	}

	public static ReplyDto toReplyDto(
			es.udc.ws.app.client.service.soap.wsdl.ReplyDto reply) {

		return new ReplyDto(reply.getReplyID(), reply.getDateReply()
				.toGregorianCalendar(), reply.getUserID(), reply.getEventID(),
				reply.isTypeReply());
	}

	public static List<ReplyDto> toReplyDtos(
			List<es.udc.ws.app.client.service.soap.wsdl.ReplyDto> replies) {

		List<ReplyDto> replyDtos = new ArrayList<>(replies.size());
		
		for (int i = 0; i < replies.size(); i++) {
			es.udc.ws.app.client.service.soap.wsdl.ReplyDto reply = replies
					.get(i);
			replyDtos.add(toReplyDto(reply));
		}

		return replyDtos;
	}

}
