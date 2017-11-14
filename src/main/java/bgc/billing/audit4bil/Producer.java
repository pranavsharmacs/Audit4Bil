package bgc.billing.audit4bil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bgc.billing.model.Audit;
import bgc.billing.model.Config;

/**
 * @author id842863 This producer will send a message of type Audit to the topic
 *         in Json format via SOAP calls.
 */

public final class Producer {

	//config properties : declared in this class as we want them to be accessible from here
	public static final String PROPERTY_TOPIC = "afb.topic";
	public static final String PROPERTY_ENVIRONMENT = "afb.environment";
	public static final String PROPERTY_QUEUESIZE = "afb.queueSize";
	public static final String PROPERTY_NUMBEROFTHREADS = "afb.numberOfThreads";
	public static final String PROPERTY_ISACTIVE = "afb.isActive";
	public static final String PROPERTY_USEELASTIC = "afb.useElastic";
	public static final String PROPERTY_URL = "afb.url";

	private Config config;
	private ExecutorService executorService;
	private boolean isActive = false;

	private static final Logger log = LoggerFactory.getLogger(Producer.class);

	public String getTopic() {
		return config.getTopic();
	}

	public int getQueueSize() {
		return config.getQueueSize();
	}

	public int getNumberOfThreads() {
		return config.getNumberOfThreads();
	}

	private Producer() {
	}

	/**
	 * For lazy singleton initialisation
	 */
	private static class Holder {
		public static final Producer INSTANCE = new Producer();
	}

	/**
	 * @return singleton instance of Producer
	 */
	public static Producer getInstance() {
		return Holder.INSTANCE;
	}

	/**
	 * @param path
	 *            Set the property file location
	 * @throws IOException
	 *             If properties file cannot be opened.
	 */
	public void setConfigLocation(String path) throws IOException {
		FileInputStream instream = new FileInputStream(new File(path));
		Properties properties = new Properties();
		properties.load(instream);
		configProducer(properties);
	}

	/**
	 * @param properties
	 *            Read and assign the properties
	 * @throws IOException
	 *             If property "topic" or "environment" not found.
	 */
	public void configProducer(Properties properties) throws IOException {
		setDefaultValues();
		String topic = (String) properties.get(PROPERTY_TOPIC);
		if (StringUtils.isEmpty(topic))
			throw new IOException("Empty or missing property - afb.topic");
		config.setTopic(topic);

		//properties for REST call to elastic search
		String elasticFlag = (String) properties.get(PROPERTY_USEELASTIC);
		config.setUseElastic(Boolean.parseBoolean(elasticFlag));
		config.setIndex(getIndexFromTopic(topic));

		String environment = (String) properties.get(PROPERTY_ENVIRONMENT);
		if (StringUtils.isEmpty(environment))
			throw new IOException("Empty or missing property - afb.environment");
		setEnvironment(environment);

		String activeFlag = (String) properties.get(PROPERTY_ISACTIVE);
		isActive = Boolean.parseBoolean(activeFlag);
		config.setUrl(getURL(config.getEnvironment(), config.isUseElastic(), config.getIndex()));

		int propQueueSize = getIntFromObject(properties.get(PROPERTY_QUEUESIZE));
		if (propQueueSize > 0)
			config.setQueueSize(propQueueSize);
		int propNumOfThreads = getIntFromObject(properties
				.get(PROPERTY_NUMBEROFTHREADS));
		if (propNumOfThreads > 0)
			config.setNumberOfThreads(propNumOfThreads);
		initExecutorService();
	}

	/**
	 * derive index name from the topic name
	 * @param topic
	 * @throws IOException - if the topic name is in wrong format
	 */
	private String getIndexFromTopic(String topic) throws IOException
	{
		//commenting the changes to be done for new topic name format
//		String[] parts = topic.split("\\+");
//		if(parts.length==2)
		String[] parts = topic.split("-");
		if(parts.length==3)
		{
			return parts[1].toLowerCase();
		}
		throw new IOException("Incorrect format used for property - afb.topic");
	}

	/**
	 * sets environment between PRO and UAT based on the configuration. NOTE -
	 * Audit4bil has only 2 environments : PRO - production : UAT - everything else
	 * @param environment
	 *            - provided as a configuration parameter
	 */
	private void setEnvironment(String environment) {
		if (Constants.ENVIRONMENT_PRO.equalsIgnoreCase(environment)) {
			config.setEnvironment(Constants.ENVIRONMENT_PRO);
		} else {
			config.setEnvironment(Constants.ENVIRONMENT_UAT);
		}
	}

	private String getURL(String environment,boolean restFlag, String index)
	{
		String url = "";
		if(environment.equalsIgnoreCase(Constants.ENVIRONMENT_PRO) && restFlag)
		{
			url = Constants.URL_REST_PRO + index + "/audit";
		}
		if(environment.equalsIgnoreCase(Constants.ENVIRONMENT_UAT) && restFlag)
		{
			url = Constants.URL_REST_UAT + index + "/audit";
		}
		return url;
	}

	/**
	 * set default values of optional properties
	 */
	private void setDefaultValues() {
		config = new Config();
		config.setQueueSize(Constants.DEFAULT_QUEUESIZE);
		config.setNumberOfThreads(Constants.DEFAULT_NUMBEROFTHREADS);
	}

	/**
	 * Initialize ExecutorService with max number of threads. Define a blocking
	 * queue with fixed capacity. If the number of tasks queued exceed the
	 * capacity, new tasks will be rejected with RejectedExecutionException.
	 */
	private void initExecutorService() {
		final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(
				config.getQueueSize());
		executorService = new ThreadPoolExecutor(config.getNumberOfThreads(),
				config.getNumberOfThreads(), 0L, TimeUnit.MILLISECONDS, queue);
	}

	/**
	 * @param obj
	 *            object to be converted to int
	 * @return int value if object can be cast to int, else 0
	 */
	private int getIntFromObject(Object obj) {
		String property = (String) obj;
		try {
			return Integer.parseInt(property);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Send the audit object.
	 *
	 * @param audit
	 *            The Audit object to be sent
	 */
	public void sendMessage(Audit audit) {
		if (isActive) {
			try {
				log.info("Sending message to topic : " + config.getTopic());
				AuditTask task = new AuditTask(audit,
						System.currentTimeMillis(), config);
				executorService.execute(task);
			} catch (RejectedExecutionException e) {
				log.error("RejectedExecutionException.Check your blocking queue size ? "
						+ audit.toString());
			} catch (Exception e) {
				log.error("Error when sending message : " + audit.toString());
				log.error(e.getMessage());
			}
		}
	}

	/**
	 * Close the producer.
	 *
	 * @param executePendingTasks
	 *            true - execute pending tasks before shutdown (max one minute).
	 *            false - attempt to stop executing tasks and shutdown
	 * @throws InterruptedException
	 */
	public void close(boolean executePendingTasks) throws InterruptedException {
		if(executorService!=null)
		{
			if (executePendingTasks) {
				executorService.shutdown();
				final boolean done = executorService.awaitTermination(1,
						TimeUnit.MINUTES);
				log.debug("All tasks were executed before shutdown ? {}", done);
			} else {
				final List<Runnable> rejected = executorService.shutdownNow();
				log.debug("Rejected tasks: {}", rejected.size());
			}
		}
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
