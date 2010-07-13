
package paper4all.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xml-to-ediResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xml-to-ediResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="filenameEDI-result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xml-to-ediResponse", propOrder = {
    "filenameEDIResult"
})
public class XmlToEdiResponse {

    @XmlElement(name = "filenameEDI-result")
    protected String filenameEDIResult;

    /**
     * Gets the value of the filenameEDIResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilenameEDIResult() {
        return filenameEDIResult;
    }

    /**
     * Sets the value of the filenameEDIResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilenameEDIResult(String value) {
        this.filenameEDIResult = value;
    }

}
