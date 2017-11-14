
package bc.intra.middleware.api.services.pubsub.push;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import bc.intra.middleware.api.schema.pubsub.MessageIdType;


/**
 * <p>Java class for PublishResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PublishResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageIds" type="{http://middleware.intra.bc/api/schema/pubsub}messageIdType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PublishResponseType", propOrder = {
    "messageIds"
})
public class PublishResponseType {

    @XmlElement(required = true)
    protected MessageIdType messageIds;

    /**
     * Gets the value of the messageIds property.
     * 
     * @return
     *     possible object is
     *     {@link MessageIdType }
     *     
     */
    public MessageIdType getMessageIds() {
        return messageIds;
    }

    /**
     * Sets the value of the messageIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageIdType }
     *     
     */
    public void setMessageIds(MessageIdType value) {
        this.messageIds = value;
    }

}
