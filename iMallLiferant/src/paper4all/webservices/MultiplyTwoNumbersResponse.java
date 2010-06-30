
package paper4all.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for multiply-two-numbersResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="multiply-two-numbersResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="multiply-result" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "multiply-two-numbersResponse", propOrder = {
    "multiplyResult"
})
public class MultiplyTwoNumbersResponse {

    @XmlElement(name = "multiply-result")
    protected double multiplyResult;

    /**
     * Gets the value of the multiplyResult property.
     * 
     */
    public double getMultiplyResult() {
        return multiplyResult;
    }

    /**
     * Sets the value of the multiplyResult property.
     * 
     */
    public void setMultiplyResult(double value) {
        this.multiplyResult = value;
    }

}
