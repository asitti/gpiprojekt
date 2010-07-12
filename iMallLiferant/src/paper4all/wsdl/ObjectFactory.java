
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

    private final static QName _ReadFromInbox_QNAME = new QName("http://services.paper4all/", "read-from-inbox");
    private final static QName _ReadFromInboxResponse_QNAME = new QName("http://services.paper4all/", "read-from-inboxResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: paper4all.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReadFromInbox }
     * 
     */
    public ReadFromInbox createReadFromInbox() {
        return new ReadFromInbox();
    }

    /**
     * Create an instance of {@link ReadFromInboxResponse }
     * 
     */
    public ReadFromInboxResponse createReadFromInboxResponse() {
        return new ReadFromInboxResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadFromInbox }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.paper4all/", name = "read-from-inbox")
    public JAXBElement<ReadFromInbox> createReadFromInbox(ReadFromInbox value) {
        return new JAXBElement<ReadFromInbox>(_ReadFromInbox_QNAME, ReadFromInbox.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadFromInboxResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.paper4all/", name = "read-from-inboxResponse")
    public JAXBElement<ReadFromInboxResponse> createReadFromInboxResponse(ReadFromInboxResponse value) {
        return new JAXBElement<ReadFromInboxResponse>(_ReadFromInboxResponse_QNAME, ReadFromInboxResponse.class, null, value);
    }

}
