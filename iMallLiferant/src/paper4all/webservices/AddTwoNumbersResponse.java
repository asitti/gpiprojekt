
package paper4all.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for add-two-numbersResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="add-two-numbersResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="add-result" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "add-two-numbersResponse", propOrder = {
    "addResult"
})
public class AddTwoNumbersResponse {

    @XmlElement(name = "add-result")
    protected double addResult;

    /**
     * Gets the value of the addResult property.
     * 
     */
    public double getAddResult() {
        return addResult;
    }

    /**
     * Sets the value of the addResult property.
     * 
     */
    public void setAddResult(double value) {
        this.addResult = value;
    }

}
