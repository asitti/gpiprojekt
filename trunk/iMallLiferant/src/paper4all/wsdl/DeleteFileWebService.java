
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
@WebService(name = "DeleteFileWebService", targetNamespace = "http://services.paper4all/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface DeleteFileWebService {


    /**
     * 
     * @param fileName
     * @return
     *     returns boolean
     */
    @WebMethod(operationName = "delete-file-from-hub")
    @WebResult(name = "success-result", targetNamespace = "")
    @RequestWrapper(localName = "delete-file-from-hub", targetNamespace = "http://services.paper4all/", className = "paper4all.wsdl.DeleteFileFromHub")
    @ResponseWrapper(localName = "delete-file-from-hubResponse", targetNamespace = "http://services.paper4all/", className = "paper4all.wsdl.DeleteFileFromHubResponse")
    public boolean deleteFileFromHub(
        @WebParam(name = "fileName", targetNamespace = "")
        String fileName);

}
