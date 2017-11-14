package bgc.billing.audit4bil;

import java.net.SocketTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bgc.billing.model.Audit;
import bgc.billing.model.Config;
import bgc.billing.webservices.RestClient;
import bgc.billing.webservices.SoapClientRetro;

/**
 * @author id842863
 *
 */
public class AuditTask implements Runnable {

	private final int numOfSeconds = 5;
	private Audit audit;
	private long submitTime;
	private long startTime;
	private Config config;
	private static final Logger log = LoggerFactory.getLogger(AuditTask.class);


	/**
	 * @param audit
	 *            Audit object to be pushed
	 * @param submitTime
	 *            time at which this task was submitted
	 * @param environment
	 */
	public AuditTask(Audit audit, long submitTime, Config config) {
		this.audit = audit;
		this.submitTime = submitTime;
		this.config = config;
	}


	public void run() {
		pushMessage();
	}

	/**
	 * Method which sends the Soap message
	 */
	private void pushMessage() {
		startTime = System.currentTimeMillis();
		log.debug("time spent in queue :  " + (startTime - submitTime)
				+ " milliseconds");
		try {
			if(config.isUseElastic())
			{
				RestClient restClient = new RestClient();
				restClient.sendMessage(audit,config.getUrl());
			}
			else{
				SoapClientRetro soapClient = new SoapClientRetro();
				soapClient.sendRequest(config.getEnvironment(), audit.toString());
			}

		} catch (SocketTimeoutException e) {
			//log level set to trace as a timeout is expected. We have set a low timeout value as we dont really care about the response.
			log.trace("Timeout error when sending audit message : " + e.getMessage());
		}
		catch (Exception e) {
			log.error("error when sending audit message : " + e.getMessage());
		}
		finally{
			long finishTime = System.currentTimeMillis();
			log.debug("time spent in execution :  " + (finishTime - startTime)
					+ " milliseconds");
		}
	}

	/**
	 * Method which pretends to send the Soap message but all it does is sleep.
	 * To be used for Testing
	 */
	private void pushMessageDummy() {
		startTime = System.currentTimeMillis();
		log.debug("time spent in queue :  " + (startTime - submitTime)
				+ " milliseconds");
		try {
			log.debug("sleeping for " + numOfSeconds + " seconds");
			Thread.sleep(numOfSeconds * 1000);
		} catch (InterruptedException e) {
			log.error("error when sending audit message : " + e.getMessage());
		}

		long finishTime = System.currentTimeMillis();
		log.debug("time spent in execution :  " + (finishTime - startTime)
				+ " milliseconds");
	}

}
