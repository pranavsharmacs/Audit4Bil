package bgc.billing.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bc.middleware.api.pubsub.jibx.PublishMessageType;
import bc.middleware.api.pubsub.jibx.PublishMessagesType;
import bc.middleware.api.pubsub.jibx.PublishRequestType;
import bgc.billing.audit4bil.Constants;
import bgc.billing.audit4bil.Producer;

/**
 * @author id842863 Soap client for old applications which are incompatible with
 *         JaxB 2
 *
 */
public class SoapClientRetro {

	private static String NEW_LINE = "\r\n";
	private static final int socket_timeout = 500;
	private static final int connection_timeout = 3000;

	// DEV
	String urlDev = "https://apigw-cloud-dev.bc/middleware/topicpublish/1.0";

	private static final Logger log = LoggerFactory.getLogger(SoapClientRetro.class);


	public void sendRequest(String environment, String message)
			throws IOException, JiBXException, URISyntaxException {
		HttpClient client = null;
		try {
			String URL;
			if (Constants.ENVIRONMENT_PRO.equalsIgnoreCase(environment)) {
				// for PROD
				client = getHTTPClientForProd();
				URL = Constants.URL_SOAP_PRO;
			} else {
				// for non - PROD
//				client = getHTTPClientForDev();
//				URL = urlDev;
				client = getHTTPClientForUAT();
				URL = Constants.URL_SOAP_UAT;
			}

			HttpPost httpPost = new HttpPost(URL);
			httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");

			String soapRequest = addSoapHeader(createSoapRequest(message));
			log.debug("request to pubsub : " + soapRequest);
			System.out.println(soapRequest);
			StringEntity postEntity = new StringEntity(soapRequest, "UTF-8");
			httpPost.setEntity(postEntity);

			HttpResponse httpResponse = client.execute(httpPost);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = rd.readLine()) != null) {
				sb.append(line).append(NEW_LINE);
			}
	//		System.out.println("response from pubsub : " + sb.toString());
			log.debug("response from pubsub : " + sb.toString());
		} finally {
			client.getConnectionManager().shutdown();
		}
	}

	private HttpClient getHTTPClientForDev() {
		return getHTTPClient("BillingAppDev.jks", "LSnso*^55098");
	}

	private HttpClient getHTTPClientForUAT() {
		return getHTTPClient("BillingAppItt.jks", "KKHH*(&#$*nf90");
	}

	private HttpClient getHTTPClientForProd() {
		return getHTTPClient("BillingAppPro.jks", "DDLJ^(*90ef97w");
	}

	private HttpClient getHTTPClient(String certificatePath,
			String certificatePassword) {

		//setup timeout values
		HttpParams my_httpParams = new BasicHttpParams();
		//wait 3 sec till connection is established
		HttpConnectionParams.setConnectionTimeout(my_httpParams, connection_timeout);
		//wait 1 sec for response
		HttpConnectionParams.setSoTimeout(my_httpParams, socket_timeout);

		HttpClient client = new DefaultHttpClient(my_httpParams);
		SSLContext sslContext;
		try {

			sslContext = SSLContext.getInstance("TLSv1.2");

			// for webapps like CSL, this classloader has access to all
			// classpaths, as there might be more than one !!
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();

			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(classLoader.getResourceAsStream(certificatePath),
					certificatePassword.toCharArray());

			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, certificatePassword.toCharArray());

			// truststore - all ENVs
			String certPasswdAPIM = "khdif*(21&*H";
			String certPathAPIM = "APIM-Internal-TrustStore-Dev.jks";

			KeyStore trusStore = KeyStore
					.getInstance(KeyStore.getDefaultType());
			trusStore.load(classLoader.getResourceAsStream(certPathAPIM),
					certPasswdAPIM.toCharArray());

			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(trusStore);

			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			SSLSocketFactory sf = new SSLSocketFactory(sslContext);
			// sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme scheme = new Scheme("https", sf, 443);
			client.getConnectionManager().getSchemeRegistry().register(scheme);

		} catch (Exception e) {
			log.error("error in getting HTTPCLient : " + e.getMessage());
		}
		return client;
	}

	private String createSoapRequest(String message) throws JiBXException {
		IBindingFactory bfact = BindingDirectory
				.getFactory(PublishRequestType.class);
		IMarshallingContext mctx = bfact.createMarshallingContext();
		PublishRequestType request = new PublishRequestType();
		request.setTopicName(Producer.getInstance().getTopic());
		List<PublishMessageType> messageList = new ArrayList<PublishMessageType>();
		PublishMessageType element = new PublishMessageType();
		element.setData(message);
		messageList.add(element);
		PublishMessagesType messages = new PublishMessagesType();
		messages.setMessageList(messageList);
		request.setMessages(messages);

		StringWriter stringWriter = new StringWriter();
		mctx.setOutput(stringWriter);
		mctx.marshalDocument(request);
		String s = stringWriter.toString();
		log.debug(s);
		return s;
	}

	private String addSoapHeader(String requestData) throws IOException {
		StringBuilder soap = new StringBuilder();
		soap.append("<?xml version=\"1.0\" ?>");
		soap.append("<soapenv:Envelope ");
		soap.append("xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ");
		soap.append("xmlns:urn=\"urn:v4.context.vss.objects.bgc\" ");
		soap.append(">").append(NEW_LINE);
		soap.append("  <soapenv:Header/>").append(NEW_LINE);
		soap.append("  <soapenv:Body>").append(NEW_LINE);

		// copy the request data in the SOAP Body, using the correct indentation
		BufferedReader reader = new BufferedReader(
				new StringReader(requestData));
		String str = "";
		while ((str = reader.readLine()) != null) {
			if (str.trim().length() > 0)
				soap.append("    ").append(str).append(NEW_LINE);
		}
		// done.

		soap.append("  </soapenv:Body>").append(NEW_LINE);
		soap.append("</soapenv:Envelope>").append(NEW_LINE);

		return soap.toString();
	}

	public static void main(String[] args) throws Exception {

		// debug ON
		// System.setProperty("javax.net.debug", "ssl,handshake,trustmanager");

		// System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump",
		// "true");
		// System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump",
		// "true");
		// System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump",
		// "true");
		// System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump",
		// "true");
		Producer producer = Producer.getInstance();
		Properties properties = new Properties();
		properties.setProperty(Producer.PROPERTY_TOPIC, "b010+csl-v1");
		properties.setProperty(Producer.PROPERTY_ENVIRONMENT, "ITT");
		properties.setProperty(Producer.PROPERTY_NUMBEROFTHREADS, "10");
		properties.setProperty(Producer.PROPERTY_QUEUESIZE, "100");
		properties.setProperty(Producer.PROPERTY_ISACTIVE, "true");
		producer.configProducer(properties);
		SoapClientRetro soapClient = new SoapClientRetro();
		soapClient.sendRequest("ITT", "test message");
	}
}
