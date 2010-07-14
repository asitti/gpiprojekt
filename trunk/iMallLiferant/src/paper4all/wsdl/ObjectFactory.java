
package paper4all.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the paper4all.wsdl package. 
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

    private final static QName _ProcessIncomingOrderResponse_QNAME = new QName("http://services.paper4all/", "process-incoming-orderResponse");
    private final static QName _ProcessIncomingOrder_QNAME = new QName("http://services.paper4all/", "process-incoming-order");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: paper4all.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProcessIncomingOrder }
     * 
     */
    public ProcessIncomingOrder createProcessIncomingOrder() {
        return new ProcessIncomingOrder();
    }

    /**
     * Create an instance of {@link ProcessIncomingOrderResponse }
     * 
     */
    public ProcessIncomingOrderResponse createProcessIncomingOrderResponse() {
        return new ProcessIncomingOrderResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessIncomingOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.paper4all/", name = "process-incoming-orderResponse")
    public JAXBElement<ProcessIncomingOrderResponse> createProcessIncomingOrderResponse(ProcessIncomingOrderResponse value) {
        return new JAXBElement<ProcessIncomingOrderResponse>(_ProcessIncomingOrderResponse_QNAME, ProcessIncomingOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessIncomingOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.paper4all/", name = "process-incoming-order")
    public JAXBElement<ProcessIncomingOrder> createProcessIncomingOrder(ProcessIncomingOrder value) {
        return new JAXBElement<ProcessIncomingOrder>(_ProcessIncomingOrder_QNAME, ProcessIncomingOrder.class, null, value);
    }

}
