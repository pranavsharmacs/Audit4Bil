
package bc.intra.middleware.api.schema.fault;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FaultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FaultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="faultCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="faultDetailCodes" type="{http://middleware.intra.bc/api/schema/fault}FaultDetailCodesType"/>
 *         &lt;element name="faultIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FaultType", propOrder = {
    "faultCode",
    "faultDetailCodes",
    "faultIdentifier"
})
public class FaultType {

    @XmlElement(required = true)
    protected String faultCode;
    @XmlElement(required = true)
    protected FaultDetailCodesType faultDetailCodes;
    @XmlElement(required = true)
    protected String faultIdentifier;

    /**
     * Gets the value of the faultCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultCode() {
        return faultCode;
    }

    /**
     * Sets the value of the faultCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultCode(String value) {
        this.faultCode = value;
    }

    /**
     * Gets the value of the faultDetailCodes property.
     * 
     * @return
     *     possible object is
     *     {@link FaultDetailCodesType }
     *     
     */
    public FaultDetailCodesType getFaultDetailCodes() {
        return faultDetailCodes;
    }

    /**
     * Sets the value of the faultDetailCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link FaultDetailCodesType }
     *     
     */
    public void setFaultDetailCodes(FaultDetailCodesType value) {
        this.faultDetailCodes = value;
    }

    /**
     * Gets the value of the faultIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultIdentifier() {
        return faultIdentifier;
    }

    /**
     * Sets the value of the faultIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultIdentifier(String value) {
        this.faultIdentifier = value;
    }

}
