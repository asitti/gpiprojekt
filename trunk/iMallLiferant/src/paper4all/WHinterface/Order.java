
package paper4all.WHinterface;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for order complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="order">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="traderGLN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="supplierGLN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="product" type="{http://gpi/DataTypes}product" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="orderId" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="orderDate" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "order", namespace = "http://gpi/DataTypes", propOrder = {
    "traderGLN",
    "supplierGLN",
    "product"
})
public class Order {

    @XmlElement(required = true)
    protected String traderGLN;
    @XmlElement(required = true)
    protected String supplierGLN;
    @XmlElement(required = true)
    protected List<Product> product;
    @XmlAttribute(required = true)
    protected BigInteger orderId;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar orderDate;

    /**
     * Gets the value of the traderGLN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTraderGLN() {
        return traderGLN;
    }

    /**
     * Sets the value of the traderGLN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTraderGLN(String value) {
        this.traderGLN = value;
    }

    /**
     * Gets the value of the supplierGLN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierGLN() {
        return supplierGLN;
    }

    /**
     * Sets the value of the supplierGLN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierGLN(String value) {
        this.supplierGLN = value;
    }

    /**
     * Gets the value of the product property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the product property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProduct().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Product }
     * 
     * 
     */
    public List<Product> getProduct() {
        if (product == null) {
            product = new ArrayList<Product>();
        }
        return this.product;
    }

    /**
     * Gets the value of the orderId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOrderId() {
        return orderId;
    }

    /**
     * Sets the value of the orderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOrderId(BigInteger value) {
        this.orderId = value;
    }

    /**
     * Gets the value of the orderDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the value of the orderDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrderDate(XMLGregorianCalendar value) {
        this.orderDate = value;
    }

}
