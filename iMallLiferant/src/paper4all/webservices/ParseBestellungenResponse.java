
package paper4all.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for parse-bestellungenResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="parse-bestellungenResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="interchange-result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parse-bestellungenResponse", propOrder = {
    "interchangeResult"
})
public class ParseBestellungenResponse {

    @XmlElement(name = "interchange-result")
    protected String interchangeResult;

    /**
     * Gets the value of the interchangeResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterchangeResult() {
        return interchangeResult;
    }

    /**
     * Sets the value of the interchangeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterchangeResult(String value) {
        this.interchangeResult = value;
    }

}
