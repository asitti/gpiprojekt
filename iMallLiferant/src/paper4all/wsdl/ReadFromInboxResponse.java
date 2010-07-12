
package paper4all.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for read-from-inboxResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="read-from-inboxResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="filename-result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "read-from-inboxResponse", propOrder = {
    "filenameResult"
})
public class ReadFromInboxResponse {

    @XmlElement(name = "filename-result")
    protected String filenameResult;

    /**
     * Gets the value of the filenameResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilenameResult() {
        return filenameResult;
    }

    /**
     * Sets the value of the filenameResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilenameResult(String value) {
        this.filenameResult = value;
    }

}
