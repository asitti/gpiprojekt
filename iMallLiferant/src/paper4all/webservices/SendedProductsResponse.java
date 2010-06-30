
package paper4all.webservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sended-productsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sended-productsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sended-result" type="{http://webservices.paper4all/}sended" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sended-productsResponse", propOrder = {
    "sendedResult"
})
public class SendedProductsResponse {

    @XmlElement(name = "sended-result")
    protected List<Sended> sendedResult;

    /**
     * Gets the value of the sendedResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sendedResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSendedResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Sended }
     * 
     * 
     */
    public List<Sended> getSendedResult() {
        if (sendedResult == null) {
            sendedResult = new ArrayList<Sended>();
        }
        return this.sendedResult;
    }

}
