
package paper4all.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for receive-a-stringResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="receive-a-stringResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fileName-result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "receive-a-stringResponse", propOrder = {
    "fileNameResult"
})
public class ReceiveAStringResponse {

    @XmlElement(name = "fileName-result")
    protected String fileNameResult;

    /**
     * Gets the value of the fileNameResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileNameResult() {
        return fileNameResult;
    }

    /**
     * Sets the value of the fileNameResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileNameResult(String value) {
        this.fileNameResult = value;
    }

}
