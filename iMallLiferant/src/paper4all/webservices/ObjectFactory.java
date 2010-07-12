
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

    private final static QName _ParseBestellungenResponse_QNAME = new QName("http://webservices.paper4all/", "parse-bestellungenResponse");
    private final static QName _ReceiveAString_QNAME = new QName("http://webservices.paper4all/", "receive-a-string");
    private final static QName _HelloBuchhaltungResponse_QNAME = new QName("http://webservices.paper4all/", "helloBuchhaltungResponse");
    private final static QName _MultiplyTwoNumbersResponse_QNAME = new QName("http://webservices.paper4all/", "multiply-two-numbersResponse");
    private final static QName _ReceiveAStringResponse_QNAME = new QName("http://webservices.paper4all/", "receive-a-stringResponse");
    private final static QName _MultiplyTwoNumbers_QNAME = new QName("http://webservices.paper4all/", "multiply-two-numbers");
    private final static QName _HelloBuchhaltung_QNAME = new QName("http://webservices.paper4all/", "helloBuchhaltung");
    private final static QName _ParseBestellungen_QNAME = new QName("http://webservices.paper4all/", "parse-bestellungen");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: paper4all.webservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ParseBestellungenResponse }
     * 
     */
    public ParseBestellungenResponse createParseBestellungenResponse() {
        return new ParseBestellungenResponse();
    }

    /**
     * Create an instance of {@link MultiplyTwoNumbers }
     * 
     */
    public MultiplyTwoNumbers createMultiplyTwoNumbers() {
        return new MultiplyTwoNumbers();
    }

    /**
     * Create an instance of {@link HelloBuchhaltung }
     * 
     */
    public HelloBuchhaltung createHelloBuchhaltung() {
        return new HelloBuchhaltung();
    }

    /**
     * Create an instance of {@link HelloBuchhaltungResponse }
     * 
     */
    public HelloBuchhaltungResponse createHelloBuchhaltungResponse() {
        return new HelloBuchhaltungResponse();
    }

    /**
     * Create an instance of {@link MultiplyTwoNumbersResponse }
     * 
     */
    public MultiplyTwoNumbersResponse createMultiplyTwoNumbersResponse() {
        return new MultiplyTwoNumbersResponse();
    }

    /**
     * Create an instance of {@link ParseBestellungen }
     * 
     */
    public ParseBestellungen createParseBestellungen() {
        return new ParseBestellungen();
    }

    /**
     * Create an instance of {@link ReceiveAString }
     * 
     */
    public ReceiveAString createReceiveAString() {
        return new ReceiveAString();
    }

    /**
     * Create an instance of {@link ReceiveAStringResponse }
     * 
     */
    public ReceiveAStringResponse createReceiveAStringResponse() {
        return new ReceiveAStringResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParseBestellungenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.paper4all/", name = "parse-bestellungenResponse")
    public JAXBElement<ParseBestellungenResponse> createParseBestellungenResponse(ParseBestellungenResponse value) {
        return new JAXBElement<ParseBestellungenResponse>(_ParseBestellungenResponse_QNAME, ParseBestellungenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveAString }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.paper4all/", name = "receive-a-string")
    public JAXBElement<ReceiveAString> createReceiveAString(ReceiveAString value) {
        return new JAXBElement<ReceiveAString>(_ReceiveAString_QNAME, ReceiveAString.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloBuchhaltungResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.paper4all/", name = "helloBuchhaltungResponse")
    public JAXBElement<HelloBuchhaltungResponse> createHelloBuchhaltungResponse(HelloBuchhaltungResponse value) {
        return new JAXBElement<HelloBuchhaltungResponse>(_HelloBuchhaltungResponse_QNAME, HelloBuchhaltungResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultiplyTwoNumbersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.paper4all/", name = "multiply-two-numbersResponse")
    public JAXBElement<MultiplyTwoNumbersResponse> createMultiplyTwoNumbersResponse(MultiplyTwoNumbersResponse value) {
        return new JAXBElement<MultiplyTwoNumbersResponse>(_MultiplyTwoNumbersResponse_QNAME, MultiplyTwoNumbersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveAStringResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.paper4all/", name = "receive-a-stringResponse")
    public JAXBElement<ReceiveAStringResponse> createReceiveAStringResponse(ReceiveAStringResponse value) {
        return new JAXBElement<ReceiveAStringResponse>(_ReceiveAStringResponse_QNAME, ReceiveAStringResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultiplyTwoNumbers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.paper4all/", name = "multiply-two-numbers")
    public JAXBElement<MultiplyTwoNumbers> createMultiplyTwoNumbers(MultiplyTwoNumbers value) {
        return new JAXBElement<MultiplyTwoNumbers>(_MultiplyTwoNumbers_QNAME, MultiplyTwoNumbers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloBuchhaltung }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.paper4all/", name = "helloBuchhaltung")
    public JAXBElement<HelloBuchhaltung> createHelloBuchhaltung(HelloBuchhaltung value) {
        return new JAXBElement<HelloBuchhaltung>(_HelloBuchhaltung_QNAME, HelloBuchhaltung.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParseBestellungen }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.paper4all/", name = "parse-bestellungen")
    public JAXBElement<ParseBestellungen> createParseBestellungen(ParseBestellungen value) {
        return new JAXBElement<ParseBestellungen>(_ParseBestellungen_QNAME, ParseBestellungen.class, null, value);
    }

}
