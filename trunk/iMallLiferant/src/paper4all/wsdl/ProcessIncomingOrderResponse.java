
package paper4all.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for process-incoming-orderResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="process-incoming-orderResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="success-result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "process-incoming-orderResponse", propOrder = {
    "successResult"
})
public class ProcessIncomingOrderResponse {

    @XmlElement(name = "success-result")
    protected String successResult;

    /**
     * Gets the value of the successResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuccessResult() {
        return successResult;
    }

    /**
     * Sets the value of the successResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuccessResult(String value) {
        this.successResult = value;
    }

}