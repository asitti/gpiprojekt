
package paper4all.WHinterface;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sgtinWhereAbouts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sgtinWhereAbouts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sgtin" type="{http://gpi/DataTypes}sgtinContainer" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sgtinWhereAbouts", namespace = "http://gpi/DataTypes", propOrder = {
    "sgtin"
})
public class SgtinWhereAbouts {

    @XmlElement(nillable = true)
    protected List<SgtinContainer> sgtin;

    /**
     * Gets the value of the sgtin property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sgtin property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSgtin().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SgtinContainer }
     * 
     * 
     */
    public List<SgtinContainer> getSgtin() {
        if (sgtin == null) {
            sgtin = new ArrayList<SgtinContainer>();
        }
        return this.sgtin;
    }

}
