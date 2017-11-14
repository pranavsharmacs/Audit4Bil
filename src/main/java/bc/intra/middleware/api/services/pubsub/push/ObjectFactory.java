
package bc.intra.middleware.api.services.pubsub.push;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import bc.intra.middleware.api.schema.fault.FaultType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the bc.intra.middleware.api.services.pubsub.push package. 
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

    private final static QName _PublishRequest_QNAME = new QName("http://middleware.intra.bc/api/services/pubsub/push", "PublishRequest");
    private final static QName _PublishResponse_QNAME = new QName("http://middleware.intra.bc/api/services/pubsub/push", "PublishResponse");
    private final static QName _PublishFault_QNAME = new QName("http://middleware.intra.bc/api/services/pubsub/push", "PublishFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: bc.intra.middleware.api.services.pubsub.push
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PublishRequestType }
     * 
     */
    public PublishRequestType createPublishRequestType() {
        return new PublishRequestType();
    }

    /**
     * Create an instance of {@link PublishResponseType }
     * 
     */
    public PublishResponseType createPublishResponseType() {
        return new PublishResponseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PublishRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://middleware.intra.bc/api/services/pubsub/push", name = "PublishRequest")
    public JAXBElement<PublishRequestType> createPublishRequest(PublishRequestType value) {
        return new JAXBElement<PublishRequestType>(_PublishRequest_QNAME, PublishRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PublishResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://middleware.intra.bc/api/services/pubsub/push", name = "PublishResponse")
    public JAXBElement<PublishResponseType> createPublishResponse(PublishResponseType value) {
        return new JAXBElement<PublishResponseType>(_PublishResponse_QNAME, PublishResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://middleware.intra.bc/api/services/pubsub/push", name = "PublishFault")
    public JAXBElement<FaultType> createPublishFault(FaultType value) {
        return new JAXBElement<FaultType>(_PublishFault_QNAME, FaultType.class, null, value);
    }

}
