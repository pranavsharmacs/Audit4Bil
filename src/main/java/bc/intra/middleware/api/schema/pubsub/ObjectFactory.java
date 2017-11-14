
package bc.intra.middleware.api.schema.pubsub;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the bc.intra.middleware.api.schema.pubsub package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: bc.intra.middleware.api.schema.pubsub
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PublishMessagesType }
     * 
     */
    public PublishMessagesType createPublishMessagesType() {
        return new PublishMessagesType();
    }

    /**
     * Create an instance of {@link SubscriptionType }
     * 
     */
    public SubscriptionType createSubscriptionType() {
        return new SubscriptionType();
    }

    /**
     * Create an instance of {@link ReceivedMessageType }
     * 
     */
    public ReceivedMessageType createReceivedMessageType() {
        return new ReceivedMessageType();
    }

    /**
     * Create an instance of {@link SubscriptionMessageType }
     * 
     */
    public SubscriptionMessageType createSubscriptionMessageType() {
        return new SubscriptionMessageType();
    }

    /**
     * Create an instance of {@link MessageIdType }
     * 
     */
    public MessageIdType createMessageIdType() {
        return new MessageIdType();
    }

    /**
     * Create an instance of {@link AttributeType }
     * 
     */
    public AttributeType createAttributeType() {
        return new AttributeType();
    }

    /**
     * Create an instance of {@link PublishMessageType }
     * 
     */
    public PublishMessageType createPublishMessageType() {
        return new PublishMessageType();
    }

    /**
     * Create an instance of {@link AckIdsType }
     * 
     */
    public AckIdsType createAckIdsType() {
        return new AckIdsType();
    }

    /**
     * Create an instance of {@link KeyValueType }
     * 
     */
    public KeyValueType createKeyValueType() {
        return new KeyValueType();
    }

}
