package es.udc.ws.app.client.ui;

import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import es.udc.ws.app.client.service.ClientEventService;
import es.udc.ws.app.client.service.ClientEventServiceFactory;
import es.udc.ws.app.dto.EventByUserDto;
import es.udc.ws.app.dto.EventDto;
import es.udc.ws.app.dto.ReplyDto;
import es.udc.ws.app.exceptions.CompleteEventException;
import es.udc.ws.app.exceptions.EventHasAttendeesException;
import es.udc.ws.app.exceptions.ExpiredEventException;
import es.udc.ws.app.exceptions.InvalidRankDate;
import es.udc.ws.app.exceptions.ReduceAforoException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class EventServiceClient {

	public static void main(String[] args) {
		if (args.length == 0) {
			printUsageAndExit();
		}

		ClientEventService clientEventService = ClientEventServiceFactory
				.getService();

		/*
		 * [addEvent] EventServiceClient -a
		 * <name><description><dateInit><duration><aforo>
		 */
		if ("-a".equalsIgnoreCase(args[0])) {
			validateArgs(args, 6, new int[] { 4, 5 });
			try {

				DateFormat d = new SimpleDateFormat("dd-MM-yyyy hh:mm");
				Calendar dateInit = new GregorianCalendar();
				dateInit.setTime(d.parse(args[3]));
				EventDto eventDto = new EventDto(null, args[1], args[2],
						dateInit, Long.valueOf(args[4]),
						Integer.valueOf(args[5]), 0);
				String type = "";
				EventDto eventCreated = clientEventService.addEvent(eventDto);

				if (eventCreated.getAforo() == (-1))
					type = "Public Event";
				else if (eventCreated.getAforo() >= 0)
					type = "Private Event";

				System.out
						.println("\n------------------------------- EVENT ----------------------------------------"

								+ "\n	EventId: "
								+ eventCreated.getEventID()
								+ "\n	Name: "
								+ eventCreated.getName()
								+ "\n	Description: "
								+ eventCreated.getDescription()
								+ "\n	Date Init: "
								+ eventCreated.getDateInit().getTime()
								+ "\n	Duration: "
								+ eventCreated.getDuration()
								+ "\n	Aforo: "
								+ eventCreated.getAforo()
								+ "\n	Event type: "
								+ type
								+ "\n	Attendees: "
								+ eventCreated.getAttendees()
								+ "\n------------------------ Event created sucessfully! ---------------------------");

			} catch (NumberFormatException | InputValidationException ex) {
				ex.printStackTrace(System.err);

			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}

		/*
		 * [removeEvent] EventServiceClient -r <eventId>
		 */
		else if ("-r".equalsIgnoreCase(args[0])) {
			try {
				validateArgs(args, 2, new int[] { 1 });
				Long eventId = Long.valueOf(args[1]);
				clientEventService.removeEvent(eventId);
				System.out.println("\nThe item with this id (" + eventId + ") "
						+ " has been removed sucessfully\n");
			} catch (NumberFormatException | InstanceNotFoundException
					| EventHasAttendeesException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}

		}

		/*
		 * [updateEvent] EventServiceClient -u
		 * <eventId><name><description><dateInit><duracion><aforo>
		 */
		else if ("-u".equals(args[0])) {
			try {
				validateArgs(args, 7, new int[] { 1, 5, 6 });
				DateFormat d = new SimpleDateFormat("dd-MM-yyyy hh:mm");
				Calendar dateInit = new GregorianCalendar();
				dateInit.setTime(d.parse(args[4]));

				EventDto event = new EventDto(Long.valueOf(args[1]), args[2],
						args[3], dateInit, Long.valueOf(args[5]),
						Integer.valueOf(args[6]), 0);
				clientEventService.updateEvent(event);

				System.out
						.println("\n------------------------------- EVENT ----------------------------------------"
								+ "\n	Id: "
								+ event.getEventID()
								+ "\n	Name: "
								+ event.getName()
								+ "\n	Descripcion: "
								+ event.getDescription()
								+ "\n	Fecha de inicio: "
								+ event.getDateInit().getTime()
								+ "\n	Duracion evento: "
								+ event.getDuration()
								+ "\n	Aforo: "
								+ event.getAforo()
								+ "\n------------------------ Event updated sucessfully! ---------------------------");
			} catch (NumberFormatException | ParseException
					| InputValidationException | InstanceNotFoundException
					| ReduceAforoException | ExpiredEventException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}

		}
		/*
		 * [findEvent] EventServiceClient -f <eventId>
		 */
		else if ("-f".equals(args[0])) {
			try {
				validateArgs(args, 2, new int[] { 1 });
				Long eventId = Long.valueOf(args[1]);
				EventDto eventFound = clientEventService.findEventById(eventId);
				System.out
						.println("\n------------------------------- EVENT ---------------------------"
								+ "\n	Id: "
								+ eventFound.getEventID()
								+ "\n	Name: "
								+ eventFound.getName()
								+ "\n	Description: "
								+ eventFound.getDescription()
								+ "\n	Start Date: "
								+ eventFound.getDateInit().getTime()
								+ "\n	Duration: "
								+ eventFound.getDuration()
								+ "\n	Capacity: "
								+ eventFound.getAforo()
								+ "\n	Attendees: "
								+ eventFound.getAttendees()
								+ "\n------------------------ Event found! ---------------------------");

			} catch (NumberFormatException | InstanceNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}

		}
		/*
		 * [findByKeywords] EventServiceClient -fk <keywords> <dateInit>
		 * <dateFin>
		 */
		else if ("-fk".equals(args[0])) {
			validateArgs(args, new int[] { 1, 2, 3, 4 });
			DateFormat d = new SimpleDateFormat("dd-MM-yyyy hh:mm");
			Calendar dateInit = new GregorianCalendar();
			Calendar dateFin = new GregorianCalendar();
			List<EventDto> eventsFound = null;

			try {

				if (args.length == 1) { // Buscar todos los eventos
					eventsFound = clientEventService
							.findEvent(null, null, null);
				} else if (args.length == 2) { // Buscar solo por keywords
					eventsFound = clientEventService.findEvent(args[1], null,
							null);
				} else if (args.length == 3) { // Buscar solo por rango de
												// fechas
					dateInit.setTime(d.parse(args[1]));
					dateFin.setTime(d.parse(args[2]));
					eventsFound = clientEventService.findEvent(null, dateInit,
							dateFin);
				} else if (args.length == 4) { // Buscar por ambos
					dateInit.setTime(d.parse(args[2]));
					dateFin.setTime(d.parse(args[3]));
					eventsFound = clientEventService.findEvent(args[1],
							dateInit, dateFin);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (InvalidRankDate | InputValidationException e) {
				e.printStackTrace();
			}

			if (eventsFound != null) {
				System.out
						.println("\n------------------------------ findEvents -----------------------------------");

				for (int i = 0; i < eventsFound.size(); i++) {
					System.out.println("\n	Id: "
							+ eventsFound.get(i).getEventID() + "\n	Name: "
							+ eventsFound.get(i).getName() + "\n	Description: "
							+ eventsFound.get(i).getDescription()
							+ "\n	Start Date: "
							+ eventsFound.get(i).getDateInit().getTime()
							+ "\n	Duration: "
							+ eventsFound.get(i).getDuration()
							+ "\n	Capacity: " + eventsFound.get(i).getAforo()
							+ "\n	Attendees: "
							+ eventsFound.get(i).getAttendees());
				}

				System.out
						.println("\n------------------------------- Items found: "
								+ eventsFound.size()
								+ "---------------------------------\n");

			} else {

				System.out
						.println("\n------------------------------- Items found: 0"
								+ "---------------------------------\n");
			}

		}
		/*
		 * [replyEvent] EventServiceClient -re <eventId> <user> <reply>
		 */
		else if ("-re".equals(args[0])) {
			validateArgs(args, 4, new int[] { 1 });
			Long eventId = Long.valueOf(args[1]);
			ReplyDto reply = null;

			try {
				reply = clientEventService.replyEvent(eventId, args[2],
						Boolean.valueOf(args[3]));

				System.out
						.println("\n------------------------------- REPLY ----------------------------------------"
								+ "\n	ReplyID: "
								+ reply.getReplyID()
								+ "\n	EventID: "
								+ reply.getEventID()
								+ "\n	User: "
								+ reply.getUserID()
								+ "\n	Date Reply: "
								+ reply.getDateReply().getTime()
								+ "\n	Type of answer: "
								+ reply.getTypeReply()
								+ "\n------------------------ Reply created sucessfully! --------------------------");

			} catch (InstanceNotFoundException | InputValidationException
					| ExpiredEventException | CompleteEventException e) {
				e.printStackTrace();
			}

		}
		/*
		 * [getRepliesByEvent] EventServiceClient -gre <eventId> <type>
		 */

		else if ("-gre".equals(args[0])) {

			validateArgs(args, new int[] { 2, 3 });

			Long eventId = Long.valueOf(args[1]);

			List<ReplyDto> repliesFound = null;

			if (args.length == 2) {
				repliesFound = clientEventService.getRepliesByEvent(eventId,
						null);
			} else if (args.length == 3) {
				repliesFound = clientEventService.getRepliesByEvent(eventId,
						Boolean.valueOf(args[2]));
			}

			for (int i = 0; i < repliesFound.size(); i++) {
				System.out.println("\n	ReplyID: "
						+ repliesFound.get(i).getReplyID() + "\n	EventID: "
						+ repliesFound.get(i).getEventID() + "\n	User: "
						+ repliesFound.get(i).getUserID()
						+ "\n	Type of answer: "
						+ repliesFound.get(i).getTypeReply()
						+ "\n	Date Reply: "
						+ repliesFound.get(i).getDateReply().getTime());
			}
			System.out
					.println("\n------------------------------- Items found: "
							+ repliesFound.size()
							+ "---------------------------------\n");

		}
		/*
		 * [getRepliesByUser] EventServiceClient -gru <user>
		 */
		else if ("-gru".equals(args[0])) {
			validateArgs(args, new int[] { 2, 3 });

			String user = args[1];

			List<ReplyDto> repliesFound = null;

			if (args.length == 2) {
				repliesFound = clientEventService.getRepliesByUser(user, null);
			} else if (args.length == 3) {
				repliesFound = clientEventService.getRepliesByUser(user,
						Boolean.valueOf(args[2]));
			}

			for (int i = 0; i < repliesFound.size(); i++) {
				System.out.println("\n	ReplyID: "
						+ repliesFound.get(i).getReplyID() + "\n	EventID: "
						+ repliesFound.get(i).getEventID() + "\n	User: "
						+ repliesFound.get(i).getUserID()
						+ "\n	Type of answer: "
						+ repliesFound.get(i).getTypeReply()
						+ "\n	Date Reply: "
						+ repliesFound.get(i).getDateReply().getTime());
			}

			System.out
					.println("\n------------------------------- Items found: "
							+ repliesFound.size()
							+ "---------------------------------\n");
		}
		/*
		 * [assistedEventsByUser] EventServiceClient -aeu <user>
		 */
		else if ("-aeu".equals(args[0])) {
			validateArgs(args, new int[] { 2 });

			String user = args[1];

			List<EventByUserDto> eventsFound = clientEventService
					.assistedEventsByUser(user);

			for (int i = 0; i < eventsFound.size(); i++) {
				System.out.println("\n	EventName: "
						+ eventsFound.get(i).getEventName() + "\n	Start Date: "
						+ eventsFound.get(i).getDateInit().getTime()
						+ "\n	Date Reply: "
						+ eventsFound.get(i).getDateReply().getTime());
			}

			System.out
					.println("\n------------------------------- items found: "
							+ eventsFound.size()
							+ "---------------------------------\n");
		}

	}

	public static void printUsageAndExit() {
		printUsage();
		System.exit(-1);
	}

	public static void validateArgs(String[] args, int expectedArgs,
			int[] numericArguments) {
		if (expectedArgs != args.length) {
			printUsageAndExit();
		}
		for (int i = 0; i < numericArguments.length; i++) {
			int position = numericArguments[i];
			try {
				Double.parseDouble(args[position]);
			} catch (NumberFormatException n) {
				printUsageAndExit();
			}
		}
	}

	public static void validateArgs(String[] args, int[] expectedArgs) {
		boolean verity = false;
		for (int i = 0; i < expectedArgs.length; i++) {
			if (args.length == expectedArgs[i]) {
				verity = true;
			}
		}
		if (!verity)
			printUsageAndExit();

	}

	public static void printUsage() {
		System.err
				.println("Usage:\n"
						+ "    [addEvent]               EventServiceClient -a <name> <description> <dateInit> <duration> <aforo>\n"
						+ "    [removeEvent]            EventServiceClient -r <eventId>\n"
						+ "    [updateEvent]            EventServiceClient -u <eventId> <name> <description> <dateInit> <duration> <aforo>\n"
						+ "    [findEventById]          EventServiceClient -f <eventId>\n"
						+ "    [findEvents]             EventServiceClient -fk <keywords> <dateInit> <dateFin>\n"
						+ "    [replyEvent]             EventServiceClient -re <eventId> <user> <reply>\n"
						+ "    [getRepliesByEvent]      EventServiceClient -gre <eventId> <type>\n"
						+ "    [getRepliesByUser]       EventServiceClient -gru <user> <type>\n"
						+ "    [assistedEventsByUser]   EventServiceClient -aeu <user>\n");
	}
}