
package paper4all.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for add-two-numbers complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="add-two-numbers">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="first" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="second" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "add-two-numbers", propOrder = {
    "first",
    "second"
})
public class AddTwoNumbers {

    protected double first;
    protected double second;

    /**
     * Gets the value of the first property.
     * 
     */
    public double getFirst() {
        return first;
    }

    /**
     * Sets the value of the first property.
     * 
     */
    public void setFirst(double value) {
        this.first = value;
    }

    /**
     * Gets the value of the second property.
     * 
     */
    public double getSecond() {
        return second;
    }

    /**
     * Sets the value of the second property.
     * 
     */
    public void setSecond(double value) {
        this.second = value;
    }

}
