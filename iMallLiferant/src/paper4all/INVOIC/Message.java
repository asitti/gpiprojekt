//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.07.14 at 03:45:33 PM CEST 
//


package paper4all.INVOIC;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}Header"/>
 *         &lt;element ref="{}Segment"/>
 *         &lt;element ref="{}SegmentGroup"/>
 *         &lt;element ref="{}Trailer"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "header",
    "segment",
    "segmentGroup",
    "trailer"
})
@XmlRootElement(name = "Message")
public class Message {

    @XmlElement(name = "Header")
    protected Header header;
    @XmlElement(name = "Segment")
    protected Segment segment;
    @XmlElement(name = "SegmentGroup")
    protected SegmentGroup segmentGroup;
    @XmlElement(name = "Trailer")
    protected Trailer trailer;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link Header }
     *     
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link Header }
     *     
     */
    public void setHeader(Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the segment property.
     * 
     * @return
     *     possible object is
     *     {@link Segment }
     *     
     */
    public Segment getSegment() {
        return segment;
    }

    /**
     * Sets the value of the segment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Segment }
     *     
     */
    public void setSegment(Segment value) {
        this.segment = value;
    }

    /**
     * Gets the value of the segmentGroup property.
     * 
     * @return
     *     possible object is
     *     {@link SegmentGroup }
     *     
     */
    public SegmentGroup getSegmentGroup() {
        return segmentGroup;
    }

    /**
     * Sets the value of the segmentGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link SegmentGroup }
     *     
     */
    public void setSegmentGroup(SegmentGroup value) {
        this.segmentGroup = value;
    }

    /**
     * Gets the value of the trailer property.
     * 
     * @return
     *     possible object is
     *     {@link Trailer }
     *     
     */
    public Trailer getTrailer() {
        return trailer;
    }

    /**
     * Sets the value of the trailer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Trailer }
     *     
     */
    public void setTrailer(Trailer value) {
        this.trailer = value;
    }

}
