
package bc.intra.middleware.api.services.pubsub.push;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import bc.intra.middleware.api.schema.pubsub.PublishMessagesType;


/**
 * <p>Java class for PublishRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PublishRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="topicName" type="{http://middleware.intra.bc/api/schema/pubsub}TopicNameType"/>
 *         &lt;element name="messages" type="{http://middleware.intra.bc/api/schema/pubsub}PublishMessagesType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PublishRequestType", propOrder = {
    "topicName",
    "messages"
})
public class PublishRequestType {

    @XmlElement(required = true)
    protected String topicName;
    @XmlElement(required = true)
    protected PublishMessagesType messages;

    /**
     * Gets the value of the topicName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * Sets the value of the topicName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTopicName(String value) {
        this.topicName = value;
    }

    /**
     * Gets the value of the messages property.
     * 
     * @return
     *     possible object is
     *     {@link PublishMessagesType }
     *     
     */
    public PublishMessagesType getMessages() {
        return messages;
    }

    /**
     * Sets the value of the messages property.
     * 
     * @param value
     *     allowed object is
     *     {@link PublishMessagesType }
     *     
     */
    public void setMessages(PublishMessagesType value) {
        this.messages = value;
    }

}
