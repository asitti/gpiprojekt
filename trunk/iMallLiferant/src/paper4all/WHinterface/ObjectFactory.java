
package paper4all.WHinterface;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the paper4all.WHinterface package. 
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

    private final static QName _SendSaledProductsToStorage_QNAME = new QName("http://service.server.endlessStorage/", "sendSaledProductsToStorage");
    private final static QName _GetInventoryFromStorage_QNAME = new QName("http://service.server.endlessStorage/", "getInventoryFromStorage");
    private final static QName _Arrivals_QNAME = new QName("http://service.server.endlessStorage/", "arrivals");
    private final static QName _PickUpReservationFromStorage_QNAME = new QName("http://service.server.endlessStorage/", "pickUpReservationFromStorage");
    private final static QName _GetInventoryFromStorageResponse_QNAME = new QName("http://service.server.endlessStorage/", "getInventoryFromStorageResponse");
    private final static QName _SendReservationToStorage_QNAME = new QName("http://service.server.endlessStorage/", "sendReservationToStorage");
    private final static QName _GetSGTINWhereaboutsFromStorageResponse_QNAME = new QName("http://service.server.endlessStorage/", "getSGTINWhereaboutsFromStorageResponse");
    private final static QName _SendOrderToStorage_QNAME = new QName("http://service.server.endlessStorage/", "sendOrderToStorage");
    private final static QName _GetSGTINWhereaboutsFromStorage_QNAME = new QName("http://service.server.endlessStorage/", "getSGTINWhereaboutsFromStorage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: paper4all.WHinterface
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Reservation }
     * 
     */
    public Reservation createReservation() {
        return new Reservation();
    }

    /**
     * Create an instance of {@link GetSGTINWhereaboutsFromStorage }
     * 
     */
    public GetSGTINWhereaboutsFromStorage createGetSGTINWhereaboutsFromStorage() {
        return new GetSGTINWhereaboutsFromStorage();
    }

    /**
     * Create an instance of {@link Inventory }
     * 
     */
    public Inventory createInventory() {
        return new Inventory();
    }

    /**
     * Create an instance of {@link SgtinWhereAbouts }
     * 
     */
    public SgtinWhereAbouts createSgtinWhereAbouts() {
        return new SgtinWhereAbouts();
    }

    /**
     * Create an instance of {@link SendReservationToStorage }
     * 
     */
    public SendReservationToStorage createSendReservationToStorage() {
        return new SendReservationToStorage();
    }

    /**
     * Create an instance of {@link SgtinContainer }
     * 
     */
    public SgtinContainer createSgtinContainer() {
        return new SgtinContainer();
    }

    /**
     * Create an instance of {@link GetSGTINWhereaboutsFromStorageResponse }
     * 
     */
    public GetSGTINWhereaboutsFromStorageResponse createGetSGTINWhereaboutsFromStorageResponse() {
        return new GetSGTINWhereaboutsFromStorageResponse();
    }

    /**
     * Create an instance of {@link ReservationPickup }
     * 
     */
    public ReservationPickup createReservationPickup() {
        return new ReservationPickup();
    }

    /**
     * Create an instance of {@link GetInventoryFromStorageResponse }
     * 
     */
    public GetInventoryFromStorageResponse createGetInventoryFromStorageResponse() {
        return new GetInventoryFromStorageResponse();
    }

    /**
     * Create an instance of {@link Arrivals }
     * 
     */
    public Arrivals createArrivals() {
        return new Arrivals();
    }

    /**
     * Create an instance of {@link SaledProducts }
     * 
     */
    public SaledProducts createSaledProducts() {
        return new SaledProducts();
    }

    /**
     * Create an instance of {@link SendOrderToStorage }
     * 
     */
    public SendOrderToStorage createSendOrderToStorage() {
        return new SendOrderToStorage();
    }

    /**
     * Create an instance of {@link GetInventoryFromStorage }
     * 
     */
    public GetInventoryFromStorage createGetInventoryFromStorage() {
        return new GetInventoryFromStorage();
    }

    /**
     * Create an instance of {@link Product }
     * 
     */
    public Product createProduct() {
        return new Product();
    }

    /**
     * Create an instance of {@link PickUpReservationFromStorage }
     * 
     */
    public PickUpReservationFromStorage createPickUpReservationFromStorage() {
        return new PickUpReservationFromStorage();
    }

    /**
     * Create an instance of {@link Order }
     * 
     */
    public Order createOrder() {
        return new Order();
    }

    /**
     * Create an instance of {@link SendSaledProductsToStorage }
     * 
     */
    public SendSaledProductsToStorage createSendSaledProductsToStorage() {
        return new SendSaledProductsToStorage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendSaledProductsToStorage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.server.endlessStorage/", name = "sendSaledProductsToStorage")
    public JAXBElement<SendSaledProductsToStorage> createSendSaledProductsToStorage(SendSaledProductsToStorage value) {
        return new JAXBElement<SendSaledProductsToStorage>(_SendSaledProductsToStorage_QNAME, SendSaledProductsToStorage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInventoryFromStorage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.server.endlessStorage/", name = "getInventoryFromStorage")
    public JAXBElement<GetInventoryFromStorage> createGetInventoryFromStorage(GetInventoryFromStorage value) {
        return new JAXBElement<GetInventoryFromStorage>(_GetInventoryFromStorage_QNAME, GetInventoryFromStorage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Arrivals }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.server.endlessStorage/", name = "arrivals")
    public JAXBElement<Arrivals> createArrivals(Arrivals value) {
        return new JAXBElement<Arrivals>(_Arrivals_QNAME, Arrivals.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PickUpReservationFromStorage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.server.endlessStorage/", name = "pickUpReservationFromStorage")
    public JAXBElement<PickUpReservationFromStorage> createPickUpReservationFromStorage(PickUpReservationFromStorage value) {
        return new JAXBElement<PickUpReservationFromStorage>(_PickUpReservationFromStorage_QNAME, PickUpReservationFromStorage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInventoryFromStorageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.server.endlessStorage/", name = "getInventoryFromStorageResponse")
    public JAXBElement<GetInventoryFromStorageResponse> createGetInventoryFromStorageResponse(GetInventoryFromStorageResponse value) {
        return new JAXBElement<GetInventoryFromStorageResponse>(_GetInventoryFromStorageResponse_QNAME, GetInventoryFromStorageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendReservationToStorage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.server.endlessStorage/", name = "sendReservationToStorage")
    public JAXBElement<SendReservationToStorage> createSendReservationToStorage(SendReservationToStorage value) {
        return new JAXBElement<SendReservationToStorage>(_SendReservationToStorage_QNAME, SendReservationToStorage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSGTINWhereaboutsFromStorageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.server.endlessStorage/", name = "getSGTINWhereaboutsFromStorageResponse")
    public JAXBElement<GetSGTINWhereaboutsFromStorageResponse> createGetSGTINWhereaboutsFromStorageResponse(GetSGTINWhereaboutsFromStorageResponse value) {
        return new JAXBElement<GetSGTINWhereaboutsFromStorageResponse>(_GetSGTINWhereaboutsFromStorageResponse_QNAME, GetSGTINWhereaboutsFromStorageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendOrderToStorage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.server.endlessStorage/", name = "sendOrderToStorage")
    public JAXBElement<SendOrderToStorage> createSendOrderToStorage(SendOrderToStorage value) {
        return new JAXBElement<SendOrderToStorage>(_SendOrderToStorage_QNAME, SendOrderToStorage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSGTINWhereaboutsFromStorage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.server.endlessStorage/", name = "getSGTINWhereaboutsFromStorage")
    public JAXBElement<GetSGTINWhereaboutsFromStorage> createGetSGTINWhereaboutsFromStorage(GetSGTINWhereaboutsFromStorage value) {
        return new JAXBElement<GetSGTINWhereaboutsFromStorage>(_GetSGTINWhereaboutsFromStorage_QNAME, GetSGTINWhereaboutsFromStorage.class, null, value);
    }

}
