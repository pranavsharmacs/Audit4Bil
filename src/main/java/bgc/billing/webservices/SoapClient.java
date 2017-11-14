package bgc.billing.webservices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import bc.intra.middleware.api.schema.pubsub.PublishMessageType;
import bc.intra.middleware.api.schema.pubsub.PublishMessagesType;
import bc.intra.middleware.api.services.pubsub.push.PublishFaultMessage;
import bc.intra.middleware.api.services.pubsub.push.PublishPortype;
import bc.intra.middleware.api.services.pubsub.push.PublishRequestType;
import bc.intra.middleware.api.services.pubsub.push.PublishResponseType;
import bc.intra.middleware.api.services.pubsub.push.PublishService;

public class SoapClient {

	private MessageHandler messageHandler = new MessageHandler();

	public static void main(String[] args) {

		//debug ON

		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");

		String urlOne = "http://df8d5.web.bc:15222/CSL/services/BelgacomStandardRouter";
		String urlTwo = "https://apigw-cloud-dev.bc/middleware/topicpublish/1.0";
		try{
			SoapClient soapClient = new SoapClient();
			soapClient.requestPublish(urlTwo);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void requestPublish(String url) throws MalformedURLException,
			PublishFaultMessage {
		PublishService publish = new PublishService();
		PublishPortype pubPort = publish.getPublishPort();
		BindingProvider bindingProvider = (BindingProvider) pubPort;
		configProvider(bindingProvider, url);

		PublishRequestType parameters = new PublishRequestType();
		//TODO: remove the hardcoded topic and message
		parameters.setTopicName("B010-Audit4bil-v1");
		PublishMessagesType value = new PublishMessagesType();
		PublishMessageType e = new PublishMessageType();
		e.setData("message to be sent");
		value.getMessage().add(e);
		value.getMessage().add(e);
		parameters.setMessages(value);
		PublishResponseType publishResponse = pubPort.publish(parameters);
		System.out.println(publishResponse.getMessageIds().getMessageId());
	}

	private void configProvider(BindingProvider bindingProvider,
			String endPointURL) {

		// override the default endpoint URL
		bindingProvider.getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);
		// load keystore for SSL handshake
		try {
			SSLContext sc = getCustomSSLContext();
			bindingProvider
			.getRequestContext()
			.put("com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory",
					sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// add the SOAP header as a Context object
		Binding binding = bindingProvider.getBinding();
		List<Handler> handlersList = new ArrayList<Handler>();
		handlersList.add(messageHandler);

		// setting handlerChain list after all handlers were added to list
		binding.setHandlerChain(handlersList);
	}

	private SSLContext getCustomSSLContext()
			throws NoSuchAlgorithmException, KeyStoreException,
			CertificateException, FileNotFoundException, IOException,
			UnrecoverableKeyException, KeyManagementException {

		//keystore
		String certPasswd = "LSnso*^55098";
		String certPath = "BillingAppDev.jks";

		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(ClassLoader.getSystemResourceAsStream(certPath), certPasswd.toCharArray());

		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
				.getDefaultAlgorithm());
		kmf.init(ks, certPasswd.toCharArray());

		//truststore
		String certPasswdAPIM = "khdif*(21&*H";
		String certPathAPIM = "APIM-Internal-TrustStore-Dev.jks";

		KeyStore trusStore = KeyStore.getInstance(KeyStore.getDefaultType());
		trusStore.load(ClassLoader.getSystemResourceAsStream(certPathAPIM), certPasswdAPIM.toCharArray());

		TrustManagerFactory tmf = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(trusStore);


		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		//TODO: remove trustEverything when a working server certificate is available
		sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
//		sc.init(kmf.getKeyManagers(), trustEverything(), null);

		return sc;

	}

	private TrustManager[] trustEverything() {
		// set up a TrustManager that trusts everything
		TrustManager[] tm = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				System.out.println("getAcceptedIssuers =============");
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs,
					String authType) {
				System.out.println("checkClientTrusted =============");
			}

			public void checkServerTrusted(X509Certificate[] certs,
					String authType) {
				System.out.println("checkServerTrusted =============");
			}
		} };
		return tm;
	}

//	private RequestDataUserLogonType getRequestLogon() {
//		RequestDataUserLogonType request = new RequestDataUserLogonType();
//		request.setClientId(49);
//		request.setPassword("D3wint3D");
//		request.setLanguage("EN");
//		request.setUser("842863");
//		request.setUserPassword("");
//		request.setPasswordCheck("N");
//		return request;
//	}
}
