package bgc.billing.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bgc.billing.audit4bil.App;
import bgc.billing.model.Audit;

/**
 * @author id842863 Rest client for sending request to elastic search directly
 *
 */
public class RestClient {

	private static final int socket_timeout = 3000;
	private static final int connection_timeout = 3000;
	private static final String content_type = "application/json";

	private static final Logger log = LoggerFactory.getLogger(RestClient.class);

	private HttpClient getHTTPClientForDev() {
		return getHTTPClient("BillingAppDev.jks", "LSnso*^55098");
	}

	// no authentication currently on elasticsearch server, once it is implemented we use this method
	private HttpClient getHTTPClient(String certificatePath,
			String certificatePassword) {

		//setup timeout values
		HttpParams my_httpParams = new BasicHttpParams();
		//wait 3 sec till connection is established
		HttpConnectionParams.setConnectionTimeout(my_httpParams, connection_timeout);
		//wait 1 sec for response
		HttpConnectionParams.setSoTimeout(my_httpParams, socket_timeout);

		HttpClient client = new DefaultHttpClient(my_httpParams);
/*		SSLContext sslContext;
		try {

			sslContext = SSLContext.getInstance("TLSv1.1");

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		return client;
	}

	public void sendMessage(Audit audit,String url)
	{
		HttpClient httpClient = null;
		try{
	//		httpClient = new DefaultHttpClient();
			httpClient = getHTTPClientForDev();

			HttpPost postRequest = new HttpPost(url);

				StringEntity input = new StringEntity(audit.toString());
				input.setContentType(content_type);
				postRequest.setEntity(input);

				HttpResponse response = httpClient.execute(postRequest);

				if (response.getStatusLine().getStatusCode() != 201) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
				}

				BufferedReader br = new BufferedReader(
		                        new InputStreamReader((response.getEntity().getContent())));

				String output;
				log.debug("Output from Server ....");
				while ((output = br.readLine()) != null) {
					log.debug(output);
				}

		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		finally{
			if(httpClient!=null)
			{
				httpClient.getConnectionManager().shutdown();
			}
		}

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
//
//		String urlOne = "http://df8d5.web.bc:15222/CSL/services/BelgacomStandardRouter";
//		String urlTwo = "https://apigw-cloud-dev.bc/middleware/topicpublish/1.0";
//		String urlThree = "https://apigw-cots-itt.bc/middleware/topicpublish/1.0";
		RestClient restClient = new RestClient();
		restClient.sendMessage(App.createAudit(),"http://el4118.bc:9200/test/audit");
	}
}
