package bgc.billing.audit4bil;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bgc.billing.model.Audit;
import bgc.billing.model.Config;

public class Profiler {

	private static final Logger log = LoggerFactory.getLogger(Profiler.class);

	// private int numberOfTasks = 100;
	// private int numberOfThreads = 10;
	// private int queueSize = 50;
	public static final int DEFAULT_NUMBEROFTASKS = 50;
	public static final String DEFAULT_ENVIRONMENT = "DEV";

	public static void loadTest(Config config) {
		try {
			Producer producer = Producer.getInstance();
			Properties properties = new Properties();
			properties.setProperty(Producer.PROPERTY_TOPIC, config.getTopic());
			properties.setProperty(Producer.PROPERTY_NUMBEROFTHREADS,
					Integer.toString(config.getNumberOfThreads()));
			properties.setProperty(Producer.PROPERTY_QUEUESIZE,
					Integer.toString(config.getQueueSize()));
			producer.configProducer(properties);
			for (int i = 0; i < config.getNumberOfTasks(); i++) {
				Audit audit = new Audit();
				Producer.getInstance().sendMessage(audit);
			}
			Producer.getInstance().close(true);
		} catch (Exception e) {
			log.error("error while performing load test" + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void testDefaultValues()
	{
		Config config = new Config();
		config.setEnvironment(DEFAULT_ENVIRONMENT);
		config.setTopic("topic");
		config.setNumberOfTasks(DEFAULT_NUMBEROFTASKS);
		config.setNumberOfThreads(Constants.DEFAULT_NUMBEROFTHREADS);
		config.setQueueSize(Constants.DEFAULT_QUEUESIZE);
		loadTest(config);
	}


	public static void main(String[] args)
	{
		testDefaultValues();
	}

}
