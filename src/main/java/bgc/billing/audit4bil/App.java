package bgc.billing.audit4bil;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;

import bgc.billing.model.Audit;
import bgc.billing.model.ExtraKeyVal;
import bgc.billing.model.Audit.Status;

/**
 * Hello world!
 *
 */
public class App {

	private static final int numberOfTasks = 1;

	public static void main(String[] args) {
		System.out.println("Hello World!");
		try {
			Producer producer = Producer.getInstance();
			Properties properties = new Properties();
			properties.setProperty(Producer.PROPERTY_TOPIC, "B010-CACS-v1");
			properties.setProperty(Producer.PROPERTY_ENVIRONMENT, "ITT");
			properties.setProperty(Producer.PROPERTY_NUMBEROFTHREADS, "10");
			properties.setProperty(Producer.PROPERTY_QUEUESIZE, "100");
			properties.setProperty(Producer.PROPERTY_ISACTIVE, "true");
			properties.setProperty(Producer.PROPERTY_USEELASTIC, "false");
			producer.configProducer(properties);
			producer.setActive(true);
			for (int i = 0; i < numberOfTasks; i++) {
				Audit audit = createAudit();
				Producer.getInstance().sendMessage(audit);
			}
			Producer.getInstance().close(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Audit createAudit() throws JsonProcessingException {
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
