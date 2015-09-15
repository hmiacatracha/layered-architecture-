package es.udc.ws.app.serviceutil;

import es.udc.ws.app.dto.ReplyDto;
import es.udc.ws.app.model.reply.Reply;

import java.util.ArrayList;
import java.util.List;

public class ReplyToReplyDtoConversor {

	public static ReplyDto toReplyDto(Reply reply) {
		return new ReplyDto(reply.getReplyID(), reply.getDateReply(),
				reply.getUserID(), reply.getEventID(), reply.getTypeReply());

	}

	public static Reply toReply(ReplyDto replyDto) {
		String description = "anulada el porque de No en la capa de servicio";
		return new Reply(replyDto.getReplyID(), description,
				replyDto.getUserID(), replyDto.getEventID(),
				replyDto.getTypeReply());
	}

	public static List<ReplyDto> toReplyDtos(List<Reply> replies) {
		List<ReplyDto> replyDtos = new ArrayList<>(replies.size());
		for (int i = 0; i < replies.size(); i++) {
			Reply response = replies.get(i);
			replyDtos.add(toReplyDto(response));
		}
		return replyDtos;
	}

}
