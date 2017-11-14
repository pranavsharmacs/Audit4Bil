package bgc.billing.webservices;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bgc.objects.vss.context.v4.Context;

public class MessageHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);
    private Context context = null;

    public MessageHandler()
    {
    	context = createContext();
    }

    /**
     * Handles SOAP message.
     * SOAP header will be added only for outbound request.
     * If SOAP header does not already exist, then method will created new SOAP header.
     * A Context object is marshelled to the SOAP header.
     *
     * @param soapMessageContext SOAP message context to get SOAP message from
     * @return true
     */
    public boolean handleMessage(SOAPMessageContext soapMessageContext) {
        try {
        	//if this is a request, true for outbound messages, false for inbound
        	Boolean isRequest = (Boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        	if(isRequest!=null && isRequest)
        	{
                SOAPMessage message = soapMessageContext.getMessage();
                SOAPHeader header = message.getSOAPHeader();
                SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
                if (header == null) {
                    header = envelope.addHeader();
                }
                final Marshaller marshaller = JAXBContext.newInstance(Context.class).createMarshaller();
                marshaller.marshal(context, header);
                message.saveChanges();
                //TODO: remove this writer when the testing is finished
                StringWriter writer = new StringWriter();
                message.writeTo(new StringOutputStream(writer));
                LOGGER.debug("SOAP message: \n" + writer.toString());
        	}

        } catch (SOAPException e) {
            LOGGER.error("Error occurred while adding credentials to SOAP header.", e);
        } catch (IOException e) {
            LOGGER.error("Error occurred while writing message to output stream.", e);
        } catch (JAXBException e) {
        	 LOGGER.error("Error occurred while marshalling message to output stream.", e);
		}
        return true;
    }

    private Context createContext()
    {
    	Context context = new Context();
    	context.setConsumerApplicationId("Audit4log");
    	context.setCorrelationId("cor1Id");
    	return context;
    }
    //TODO: remove this class after testing is finished
    private static class StringOutputStream extends OutputStream {

        private StringWriter writer;

        public StringOutputStream(StringWriter writer) {
            this.writer = writer;
        }

        @Override
        public void write(int b) throws IOException {
            writer.write(b);
        }
    }

    public boolean handleFault(SOAPMessageContext context) {
        LOGGER.debug("handleFault has been invoked.");
        return true;
    }

    public void close(MessageContext context) {
        LOGGER.debug("close has been invoked.");
    }

    public Set<QName> getHeaders() {
        LOGGER.debug("getHeaders has been invoked.");
        return null;
    }
}