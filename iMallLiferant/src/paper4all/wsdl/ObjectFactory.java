
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

    private final static QName _XmlToEdiResponse_QNAME = new QName("http://services.paper4all/", "xml-to-ediResponse");
    private final static QName _XmlToEdi_QNAME = new QName("http://services.paper4all/", "xml-to-edi");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: paper4all.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link XmlToEdiResponse }
     * 
     */
    public XmlToEdiResponse createXmlToEdiResponse() {
        return new XmlToEdiResponse();
    }

    /**
     * Create an instance of {@link XmlToEdi }
     * 
     */
    public XmlToEdi createXmlToEdi() {
        return new XmlToEdi();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XmlToEdiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.paper4all/", name = "xml-to-ediResponse")
    public JAXBElement<XmlToEdiResponse> createXmlToEdiResponse(XmlToEdiResponse value) {
        return new JAXBElement<XmlToEdiResponse>(_XmlToEdiResponse_QNAME, XmlToEdiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XmlToEdi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.paper4all/", name = "xml-to-edi")
    public JAXBElement<XmlToEdi> createXmlToEdi(XmlToEdi value) {
        return new JAXBElement<XmlToEdi>(_XmlToEdi_QNAME, XmlToEdi.class, null, value);
    }

}
