
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.udc.ws.app.client.service.soap.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddEvent_QNAME = new QName("http://soap.ws.udc.es/", "addEvent");
    private final static QName _FindEvent_QNAME = new QName("http://soap.ws.udc.es/", "findEvent");
    private final static QName _FindEventByIdResponse_QNAME = new QName("http://soap.ws.udc.es/", "findEventByIdResponse");
    private final static QName _FindEventById_QNAME = new QName("http://soap.ws.udc.es/", "findEventById");
    private final static QName _SoapInstanceNotFoundException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInstanceNotFoundException");
    private final static QName _SoapInvalidRankDateException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInvalidRankDateException");
    private final static QName _SoapReduceAforoException_QNAME = new QName("http://soap.ws.udc.es/", "SoapReduceAforoException");
    private final static QName _ReplyEvent_QNAME = new QName("http://soap.ws.udc.es/", "replyEvent");
    private final static QName _GetRepliesByEventResponse_QNAME = new QName("http://soap.ws.udc.es/", "getRepliesByEventResponse");
    private final static QName _GetRepliesByUserResponse_QNAME = new QName("http://soap.ws.udc.es/", "getRepliesByUserResponse");
    private final static QName _GetRepliesByEvent_QNAME = new QName("http://soap.ws.udc.es/", "getRepliesByEvent");
    private final static QName _ReplyEventResponse_QNAME = new QName("http://soap.ws.udc.es/", "replyEventResponse");
    private final static QName _UpdateEvent_QNAME = new QName("http://soap.ws.udc.es/", "updateEvent");
    private final static QName _AddEventResponse_QNAME = new QName("http://soap.ws.udc.es/", "addEventResponse");
    private final static QName _AssistedEventsByUserResponse_QNAME = new QName("http://soap.ws.udc.es/", "assistedEventsByUserResponse");
    private final static QName _SoapCompleteEventException_QNAME = new QName("http://soap.ws.udc.es/", "SoapCompleteEventException");
    private final static QName _UpdateEventResponse_QNAME = new QName("http://soap.ws.udc.es/", "updateEventResponse");
    private final static QName _SoapExpiredEventException_QNAME = new QName("http://soap.ws.udc.es/", "SoapExpiredEventException");
    private final static QName _FindEventResponse_QNAME = new QName("http://soap.ws.udc.es/", "findEventResponse");
    private final static QName _SoapEventHasAttendeesException_QNAME = new QName("http://soap.ws.udc.es/", "SoapEventHasAttendeesException");
    private final static QName _AssistedEventsByUser_QNAME = new QName("http://soap.ws.udc.es/", "assistedEventsByUser");
    private final static QName _RemoveEvent_QNAME = new QName("http://soap.ws.udc.es/", "removeEvent");
    private final static QName _RemoveEventResponse_QNAME = new QName("http://soap.ws.udc.es/", "removeEventResponse");
    private final static QName _SoapInputValidationException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInputValidationException");
    private final static QName _GetRepliesByUser_QNAME = new QName("http://soap.ws.udc.es/", "getRepliesByUser");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.udc.ws.app.client.service.soap.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SoapInstanceNotFoundExceptionInfo }
     * 
     */
    public SoapInstanceNotFoundExceptionInfo createSoapInstanceNotFoundExceptionInfo() {
        return new SoapInstanceNotFoundExceptionInfo();
    }

    /**
     * Create an instance of {@link ReplyEvent }
     * 
     */
    public ReplyEvent createReplyEvent() {
        return new ReplyEvent();
    }

    /**
     * Create an instance of {@link GetRepliesByEventResponse }
     * 
     */
    public GetRepliesByEventResponse createGetRepliesByEventResponse() {
        return new GetRepliesByEventResponse();
    }

    /**
     * Create an instance of {@link GetRepliesByUserResponse }
     * 
     */
    public GetRepliesByUserResponse createGetRepliesByUserResponse() {
        return new GetRepliesByUserResponse();
    }

    /**
     * Create an instance of {@link AddEvent }
     * 
     */
    public AddEvent createAddEvent() {
        return new AddEvent();
    }

    /**
     * Create an instance of {@link FindEvent }
     * 
     */
    public FindEvent createFindEvent() {
        return new FindEvent();
    }

    /**
     * Create an instance of {@link FindEventById }
     * 
     */
    public FindEventById createFindEventById() {
        return new FindEventById();
    }

    /**
     * Create an instance of {@link FindEventByIdResponse }
     * 
     */
    public FindEventByIdResponse createFindEventByIdResponse() {
        return new FindEventByIdResponse();
    }

    /**
     * Create an instance of {@link RemoveEventResponse }
     * 
     */
    public RemoveEventResponse createRemoveEventResponse() {
        return new RemoveEventResponse();
    }

    /**
     * Create an instance of {@link AssistedEventsByUser }
     * 
     */
    public AssistedEventsByUser createAssistedEventsByUser() {
        return new AssistedEventsByUser();
    }

    /**
     * Create an instance of {@link RemoveEvent }
     * 
     */
    public RemoveEvent createRemoveEvent() {
        return new RemoveEvent();
    }

    /**
     * Create an instance of {@link GetRepliesByUser }
     * 
     */
    public GetRepliesByUser createGetRepliesByUser() {
        return new GetRepliesByUser();
    }

    /**
     * Create an instance of {@link ReplyEventResponse }
     * 
     */
    public ReplyEventResponse createReplyEventResponse() {
        return new ReplyEventResponse();
    }

    /**
     * Create an instance of {@link GetRepliesByEvent }
     * 
     */
    public GetRepliesByEvent createGetRepliesByEvent() {
        return new GetRepliesByEvent();
    }

    /**
     * Create an instance of {@link UpdateEvent }
     * 
     */
    public UpdateEvent createUpdateEvent() {
        return new UpdateEvent();
    }

    /**
     * Create an instance of {@link AssistedEventsByUserResponse }
     * 
     */
    public AssistedEventsByUserResponse createAssistedEventsByUserResponse() {
        return new AssistedEventsByUserResponse();
    }

    /**
     * Create an instance of {@link AddEventResponse }
     * 
     */
    public AddEventResponse createAddEventResponse() {
        return new AddEventResponse();
    }

    /**
     * Create an instance of {@link FindEventResponse }
     * 
     */
    public FindEventResponse createFindEventResponse() {
        return new FindEventResponse();
    }

    /**
     * Create an instance of {@link UpdateEventResponse }
     * 
     */
    public UpdateEventResponse createUpdateEventResponse() {
        return new UpdateEventResponse();
    }

    /**
     * Create an instance of {@link EventByUserDto }
     * 
     */
    public EventByUserDto createEventByUserDto() {
        return new EventByUserDto();
    }

    /**
     * Create an instance of {@link ReplyDto }
     * 
     */
    public ReplyDto createReplyDto() {
        return new ReplyDto();
    }

    /**
     * Create an instance of {@link EventDto }
     * 
     */
    public EventDto createEventDto() {
        return new EventDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEvent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "addEvent")
    public JAXBElement<AddEvent> createAddEvent(AddEvent value) {
        return new JAXBElement<AddEvent>(_AddEvent_QNAME, AddEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindEvent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findEvent")
    public JAXBElement<FindEvent> createFindEvent(FindEvent value) {
        return new JAXBElement<FindEvent>(_FindEvent_QNAME, FindEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindEventByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findEventByIdResponse")
    public JAXBElement<FindEventByIdResponse> createFindEventByIdResponse(FindEventByIdResponse value) {
        return new JAXBElement<FindEventByIdResponse>(_FindEventByIdResponse_QNAME, FindEventByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindEventById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findEventById")
    public JAXBElement<FindEventById> createFindEventById(FindEventById value) {
        return new JAXBElement<FindEventById>(_FindEventById_QNAME, FindEventById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapInstanceNotFoundExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInstanceNotFoundException")
    public JAXBElement<SoapInstanceNotFoundExceptionInfo> createSoapInstanceNotFoundException(SoapInstanceNotFoundExceptionInfo value) {
        return new JAXBElement<SoapInstanceNotFoundExceptionInfo>(_SoapInstanceNotFoundException_QNAME, SoapInstanceNotFoundExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInvalidRankDateException")
    public JAXBElement<String> createSoapInvalidRankDateException(String value) {
        return new JAXBElement<String>(_SoapInvalidRankDateException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapReduceAforoException")
    public JAXBElement<String> createSoapReduceAforoException(String value) {
        return new JAXBElement<String>(_SoapReduceAforoException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReplyEvent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "replyEvent")
    public JAXBElement<ReplyEvent> createReplyEvent(ReplyEvent value) {
        return new JAXBElement<ReplyEvent>(_ReplyEvent_QNAME, ReplyEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRepliesByEventResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "getRepliesByEventResponse")
    public JAXBElement<GetRepliesByEventResponse> createGetRepliesByEventResponse(GetRepliesByEventResponse value) {
        return new JAXBElement<GetRepliesByEventResponse>(_GetRepliesByEventResponse_QNAME, GetRepliesByEventResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRepliesByUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "getRepliesByUserResponse")
    public JAXBElement<GetRepliesByUserResponse> createGetRepliesByUserResponse(GetRepliesByUserResponse value) {
        return new JAXBElement<GetRepliesByUserResponse>(_GetRepliesByUserResponse_QNAME, GetRepliesByUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRepliesByEvent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "getRepliesByEvent")
    public JAXBElement<GetRepliesByEvent> createGetRepliesByEvent(GetRepliesByEvent value) {
        return new JAXBElement<GetRepliesByEvent>(_GetRepliesByEvent_QNAME, GetRepliesByEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReplyEventResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "replyEventResponse")
    public JAXBElement<ReplyEventResponse> createReplyEventResponse(ReplyEventResponse value) {
        return new JAXBElement<ReplyEventResponse>(_ReplyEventResponse_QNAME, ReplyEventResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateEvent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "updateEvent")
    public JAXBElement<UpdateEvent> createUpdateEvent(UpdateEvent value) {
        return new JAXBElement<UpdateEvent>(_UpdateEvent_QNAME, UpdateEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEventResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "addEventResponse")
    public JAXBElement<AddEventResponse> createAddEventResponse(AddEventResponse value) {
        return new JAXBElement<AddEventResponse>(_AddEventResponse_QNAME, AddEventResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssistedEventsByUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "assistedEventsByUserResponse")
    public JAXBElement<AssistedEventsByUserResponse> createAssistedEventsByUserResponse(AssistedEventsByUserResponse value) {
        return new JAXBElement<AssistedEventsByUserResponse>(_AssistedEventsByUserResponse_QNAME, AssistedEventsByUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapCompleteEventException")
    public JAXBElement<String> createSoapCompleteEventException(String value) {
        return new JAXBElement<String>(_SoapCompleteEventException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateEventResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "updateEventResponse")
    public JAXBElement<UpdateEventResponse> createUpdateEventResponse(UpdateEventResponse value) {
        return new JAXBElement<UpdateEventResponse>(_UpdateEventResponse_QNAME, UpdateEventResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapExpiredEventException")
    public JAXBElement<String> createSoapExpiredEventException(String value) {
        return new JAXBElement<String>(_SoapExpiredEventException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindEventResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findEventResponse")
    public JAXBElement<FindEventResponse> createFindEventResponse(FindEventResponse value) {
        return new JAXBElement<FindEventResponse>(_FindEventResponse_QNAME, FindEventResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapEventHasAttendeesException")
    public JAXBElement<String> createSoapEventHasAttendeesException(String value) {
        return new JAXBElement<String>(_SoapEventHasAttendeesException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssistedEventsByUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "assistedEventsByUser")
    public JAXBElement<AssistedEventsByUser> createAssistedEventsByUser(AssistedEventsByUser value) {
        return new JAXBElement<AssistedEventsByUser>(_AssistedEventsByUser_QNAME, AssistedEventsByUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveEvent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "removeEvent")
    public JAXBElement<RemoveEvent> createRemoveEvent(RemoveEvent value) {
        return new JAXBElement<RemoveEvent>(_RemoveEvent_QNAME, RemoveEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveEventResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "removeEventResponse")
    public JAXBElement<RemoveEventResponse> createRemoveEventResponse(RemoveEventResponse value) {
        return new JAXBElement<RemoveEventResponse>(_RemoveEventResponse_QNAME, RemoveEventResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInputValidationException")
    public JAXBElement<String> createSoapInputValidationException(String value) {
        return new JAXBElement<String>(_SoapInputValidationException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRepliesByUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "getRepliesByUser")
    public JAXBElement<GetRepliesByUser> createGetRepliesByUser(GetRepliesByUser value) {
        return new JAXBElement<GetRepliesByUser>(_GetRepliesByUser_QNAME, GetRepliesByUser.class, null, value);
    }

}
