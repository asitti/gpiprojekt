
package paper4all.webservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the paper4all.webservices package. 
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

    private final static QName _HelloLagerResponse_QNAME = new QName("http://webservices.paper4all/", "helloLagerResponse");
    private final static QName _AddTwoNumbers_QNAME = new QName("http://webservices.paper4all/", "add-two-numbers");
    private final static QName _AddTwoNumbersResponse_QNAME = new QName("http://webservices.paper4all/", "add-two-numbersResponse");
    private final static QName _SendedProductsResponse_QNAME = new QName("http://webservices.paper4all/", "sended-productsResponse");
    private final static QName _HelloLager_QNAME = new QName("http://webservices.paper4all/", "helloLager");
    private final static QName _SendedProducts_QNAME = new QName("http://webservices.paper4all/", "sended-products");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: paper4all.webservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddTwoNumbersResponse }
     * 
     */
    public AddTwoNumbersResponse createAddTwoNumbersResponse() {
        return new AddTwoNumbersResponse();
    }

    /**
     * Create an instance of {@link SendedProductsResponse }
     * 
     */
    public SendedProductsResponse createSendedProductsResponse() {
        return new SendedProductsResponse();
    }

    /**
     * Create an instance of {@link HelloLager }
     * 
     */
    public HelloLager createHelloLager() {
        return new HelloLager();
    }

    /**
     * Create an instance of {@link AddTwoNumbers }
     * 
     */
    public AddTwoNumbers createAddTwoNumbers() {
        return new AddTwoNumbers();
    }

    /**
     * Create an instance of {@link SendedProducts }
     * 
     */
    public SendedProducts createSendedProducts() {
        return new SendedProducts();
    }

    /**
     * Create an instance of {@link Sended }
     * 
     */
    public Sended createSended() {
        return new Sended();
    }

    /**
     * Create an instance of {@link HelloLagerResponse }
     * 
     */
    public HelloLagerResponse createHelloLagerResponse() {
        return new HelloLagerResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloLagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.paper4all/", name = "helloLagerResponse")
    public JAXBElement<HelloLagerResponse> createHelloLagerResponse(HelloLagerResponse value) {
        return new JAXBElement<HelloLagerResponse>(_HelloLagerResponse_QNAME, HelloLagerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddTwoNumbers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.paper4all/", name = "add-two-numbers")
    public JAXBElement<AddTwoNumbers> createAddTwoNumbers(AddTwoNumbers value) {
        return new JAXBElement<AddTwoNumbers>(_AddTwoNumbers_QNAME, AddTwoNumbers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddTwoNumbersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.paper4all/", name = "add-two-numbersResponse")
    public JAXBElement<AddTwoNumbersResponse> createAddTwoNumbersResponse(AddTwoNumbersResponse value) {
        return new JAXBElement<AddTwoNumbersResponse>(_AddTwoNumbersResponse_QNAME, AddTwoNumbersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendedProductsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.paper4all/", name = "sended-productsResponse")
    public JAXBElement<SendedProductsResponse> createSendedProductsResponse(SendedProductsResponse value) {
        return new JAXBElement<SendedProductsResponse>(_SendedProductsResponse_QNAME, SendedProductsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloLager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.paper4all/", name = "helloLager")
    public JAXBElement<HelloLager> createHelloLager(HelloLager value) {
        return new JAXBElement<HelloLager>(_HelloLager_QNAME, HelloLager.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendedProducts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.paper4all/", name = "sended-products")
    public JAXBElement<SendedProducts> createSendedProducts(SendedProducts value) {
        return new JAXBElement<SendedProducts>(_SendedProducts_QNAME, SendedProducts.class, null, value);
    }

}
