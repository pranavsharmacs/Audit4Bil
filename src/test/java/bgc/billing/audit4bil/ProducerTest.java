package bgc.billing.audit4bil;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bgc.billing.model.Audit;
import bgc.billing.model.Audit.Status;
import bgc.billing.model.ExtraKeyVal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.io.Resources;

@RunWith(MockitoJUnitRunner.class)
public class ProducerTest {

	private static final String PROPERTY_FILE_ONE = "producer1.props";
	private static final String PROPERTY_FILE_TWO = "producer2.props";

	/**
	 * 1 setConfigLocation Test Cases
	 */

	/**
	 * 1.1 setConfigLocation - pass the property file path and check if the field topic in Producer is assigned the value from the property file
	 * @throws Exception
	 */
	@Test
	public void testSetConfigLocation() throws Exception {
		String path = Resources.getResource(PROPERTY_FILE_ONE).getPath();
		Producer producer = Producer.getInstance();
		producer.setConfigLocation(path);
		Properties properties = new Properties();
		properties.load(Resources.getResource(PROPERTY_FILE_ONE)
				.openStream());
		String topic = (String) properties.get(Producer.PROPERTY_TOPIC);
		assertTrue("lol wut", topic.equals(producer.getTopic()));
	}

	/**
	 * 1.2 setConfigLocation - pass a property file with missing "topic". Expected result - IOException
	 * @throws IOException
	 */
	@Test(expected=IOException.class)
	public void testTopicMissingInConfigFile() throws IOException
	{
		String path = Resources.getResource(PROPERTY_FILE_TWO).getPath();
		Producer.getInstance().setConfigLocation(path);
	}

	/**
	 * 1.3 setConfigLocation - optional fields like queueSize and numberOfThreads should be picked from the properties file if present,
	 * if not the default values should be taken.
	 * Test if queueSize(present in config file)  is picked from file
	 * @throws Exception
	 */
	@Test
	public void testOptionalFieldsInConfigFile() throws Exception {
		String path = Resources.getResource(PROPERTY_FILE_ONE).getPath();
		Producer producer = Producer.getInstance();
		producer.setConfigLocation(path);
		int queueSizeNow = producer.getQueueSize();
		Properties properties = new Properties();
		properties.load(Resources.getResource(PROPERTY_FILE_ONE)
				.openStream());
		int configQueueSize =  Integer.parseInt((String) properties.get(Producer.PROPERTY_QUEUESIZE));
		assertTrue(queueSizeNow==configQueueSize);
	}

	/**
	 * 1.4 setConfigLocation - optional fields like queueSize and numberOfThreads should be picked from the properties file if present,
	 * if not the default values should be taken.
	 * Test if numberOfThreads(not present in config file) has default value
	 * @throws Exception
	 */
	@Test
	public void testOptionalFieldsNotInConfigFile() throws Exception {
		String path = Resources.getResource(PROPERTY_FILE_ONE).getPath();
		Producer producer = Producer.getInstance();
		int defaultNumOfThreads = producer.getNumberOfThreads();
		producer.setConfigLocation(path);
		int numberOfThreadsNow = producer.getNumberOfThreads();
		Properties properties = new Properties();
		properties.load(Resources.getResource(PROPERTY_FILE_ONE)
				.openStream());
		assertTrue(numberOfThreadsNow==defaultNumOfThreads);
	}

	/**
	 * 2 sendMessage Test Cases
	 */
	@Test
	public void testSendMessage() throws Exception {
		String path = Resources.getResource(PROPERTY_FILE_ONE).getPath();
		Producer producer = Producer.getInstance();
		producer.setConfigLocation(path);
//		when(producer.getNumberOfThreads()).thenReturn(3);
		producer.sendMessage(createAudit());
	}

	/**
	 * 1 close Test Cases
	 */
	@Test
	public void testExecutePendingTasks() throws Exception {
		String path = Resources.getResource(PROPERTY_FILE_ONE).getPath();
		Producer producer = Producer.getInstance();
		producer.setConfigLocation(path);
		producer.close(true);
	}

	@Test
	public void testShutDownNow() throws Exception {
		String path = Resources.getResource(PROPERTY_FILE_ONE).getPath();
		Producer producer = Producer.getInstance();
		producer.setConfigLocation(path);
		producer.close(false);
	}

	/**
	 * creates dummy Audit object for test
	 *
	 * @return
	 * @throws JsonProcessingException
	 */
	private Audit createAudit() throws JsonProcessingException {
		Audit audit = new Audit();
		Date then = new Date();

		audit.setComponent("CTT");
		audit.setStartDateTime(then);
		audit.setActionName("bgc.services.csl.account.v10");
		audit.setActionSubName("GetBalance");
		audit.setCorrelationId("abc-" + new Random().nextInt(100000));
		audit.setCorrelationId2("0039-jhd-748966");
		audit.setConsumerId("09");
		audit.setEndUserID("ID876321");
		Status s = Status.valueOf("SUCCESS");
		audit.setStatus(s);
		ExtraKeyVal xKeyVal = new ExtraKeyVal();
		audit.setExtraKeyVal(xKeyVal);
		xKeyVal.setCUSTID("123");
		xKeyVal.setNA("0475662039");
		// add new Key Val pair in the extraKeyVal property
		xKeyVal.setAdditionalProperty("NewProperty", "SomeValue");

		Date now = new Date();
		audit.setEndDateTime(now);
		audit.setTimeTakenAction(audit.getEndDateTime().getTime()
				- audit.getStartDateTime().getTime());
		return audit;
	}

}
