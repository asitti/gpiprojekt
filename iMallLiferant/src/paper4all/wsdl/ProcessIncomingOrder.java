
package paper4all.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for process-incoming-order complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="process-incoming-order">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="xmlOrderContent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="invoiceTemplateContent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "process-incoming-order", propOrder = {
    "xmlOrderContent",
    "invoiceTemplateContent"
})
public class ProcessIncomingOrder {

    protected String xmlOrderContent;
    protected String invoiceTemplateContent;

    /**
     * Gets the value of the xmlOrderContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlOrderContent() {
        return xmlOrderContent;
    }

    /**
     * Sets the value of the xmlOrderContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlOrderContent(String value) {
        this.xmlOrderContent = value;
    }

    /**
     * Gets the value of the invoiceTemplateContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceTemplateContent() {
        return invoiceTemplateContent;
    }

    /**
     * Sets the value of the invoiceTemplateContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceTemplateContent(String value) {
        this.invoiceTemplateContent = value;
    }

}
