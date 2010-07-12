
package paper4all.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for delete-file-from-hubResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="delete-file-from-hubResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="success-result" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delete-file-from-hubResponse", propOrder = {
    "successResult"
})
public class DeleteFileFromHubResponse {

    @XmlElement(name = "success-result")
    protected boolean successResult;

    /**
     * Gets the value of the successResult property.
     * 
     */
    public boolean isSuccessResult() {
        return successResult;
    }

    /**
     * Sets the value of the successResult property.
     * 
     */
    public void setSuccessResult(boolean value) {
        this.successResult = value;
    }

}
