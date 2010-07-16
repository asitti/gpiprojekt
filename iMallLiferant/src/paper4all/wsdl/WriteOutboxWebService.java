
package paper4all.wsdl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "WriteOutboxWebService", targetNamespace = "http://services.paper4all/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface WriteOutboxWebService {


    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod(operationName = "write-to-outbox")
    @WebResult(name = "success-result", targetNamespace = "")
    @RequestWrapper(localName = "write-to-outbox", targetNamespace = "http://services.paper4all/", className = "paper4all.wsdl.WriteToOutbox")
    @ResponseWrapper(localName = "write-to-outboxResponse", targetNamespace = "http://services.paper4all/", className = "paper4all.wsdl.WriteToOutboxResponse")
    public boolean writeToOutbox(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}