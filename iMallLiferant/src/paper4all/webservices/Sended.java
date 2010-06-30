
package paper4all.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sended complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sended">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="geschickt" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="gtin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preis" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="sgtin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="verlangt" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sended", propOrder = {
    "geschickt",
    "gtin",
    "preis",
    "sgtin",
    "verlangt"
})
public class Sended {

    protected int geschickt;
    protected String gtin;
    protected double preis;
    protected String sgtin;
    protected int verlangt;

    /**
     * Gets the value of the geschickt property.
     * 
     */
    public int getGeschickt() {
        return geschickt;
    }

    /**
     * Sets the value of the geschickt property.
     * 
     */
    public void setGeschickt(int value) {
        this.geschickt = value;
    }

    /**
     * Gets the value of the gtin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGtin() {
        return gtin;
    }

    /**
     * Sets the value of the gtin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGtin(String value) {
        this.gtin = value;
    }

    /**
     * Gets the value of the preis property.
     * 
     */
    public double getPreis() {
        return preis;
    }

    /**
     * Sets the value of the preis property.
     * 
     */
    public void setPreis(double value) {
        this.preis = value;
    }

    /**
     * Gets the value of the sgtin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSgtin() {
        return sgtin;
    }

    /**
     * Sets the value of the sgtin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSgtin(String value) {
        this.sgtin = value;
    }

    /**
     * Gets the value of the verlangt property.
     * 
     */
    public int getVerlangt() {
        return verlangt;
    }

    /**
     * Sets the value of the verlangt property.
     * 
     */
    public void setVerlangt(int value) {
        this.verlangt = value;
    }

}
