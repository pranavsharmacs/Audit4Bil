package bgc.billing.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Audit Interface
 * <p>
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"environment", "component", "startDateTime", "endDateTime", "actionName",
		"actionSubName", "correlationId", "correlationId2", "consumerId",
		"endUserID", "extraKeyVal", "timeTakenAction", "status", "errCode",
		"errDetailedDescription" })
public class Audit {

	private ObjectMapper mapper;

	@JsonProperty("environment")
	private String environment;
	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("component")
	private String component;
	@JsonProperty("startDateTime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "CET")
	private Date startDateTime;
	@JsonProperty("endDateTime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "CET")
	private Date endDateTime;
	/**
	 *
	 * (Required)
	 *
	 */
	@JsonProperty("actionName")
	private String actionName;
	@JsonProperty("actionSubName")
	private String actionSubName;
	@JsonProperty("correlationId")
	private String correlationId;
	@JsonProperty("correlationId2")
	private String correlationId2;
	@JsonProperty("consumerId")
	private String consumerId;
	@JsonProperty("endUserID")
	private String endUserID;
	@JsonProperty("extraKeyVal")
	private ExtraKeyVal extraKeyVal;
	@JsonProperty("timeTakenAction")
	private long timeTakenAction;
	@JsonProperty("status")
	private Audit.Status status;
	@JsonProperty("errCode")
	private String errCode;
	@JsonProperty("errDetailedDescription")
	private String errDetailedDescription;



	/**
	 * @return The environment
	 */
	@JsonProperty("environment")
	public String getEnvironment() {
		return environment;
	}

	/**
	 *
	 * @param environment
	 *            The environment
	 */
	@JsonProperty("environment")
	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	/**
	 *
	 * (Required)
	 *
	 * @return The component
	 */
	@JsonProperty("component")
	public String getComponent() {
		return component;
	}

	/**
	 *
	 * (Required)
	 *
	 * @param component
	 *            The component
	 */
	@JsonProperty("component")
	public void setComponent(String component) {
		this.component = component;
	}

	/**
	 *
	 * @return The startDateTime
	 */
	@JsonProperty("startDateTime")
	public Date getStartDateTime() {
		return startDateTime;
	}

	/**
	 *
	 * @param startDateTime
	 *            The startDateTime
	 */
	@JsonProperty("startDateTime")
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	/**
	 *
	 * @return The endDateTime
	 */
	@JsonProperty("endDateTime")
	public Date getEndDateTime() {
		return endDateTime;
	}

	/**
	 *
	 * @param endDateTime
	 *            The endDateTime
	 */
	@JsonProperty("endDateTime")
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	/**
	 *
	 * (Required)
	 *
	 * @return The actionName
	 */
	@JsonProperty("actionName")
	public String getActionName() {
		return actionName;
	}

	/**
	 *
	 * (Required)
	 *
	 * @param actionName
	 *            The actionName
	 */
	@JsonProperty("actionName")
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 *
	 * @return The actionSubName
	 */
	@JsonProperty("actionSubName")
	public String getActionSubName() {
		return actionSubName;
	}

	/**
	 *
	 * @param actionSubName
	 *            The actionSubName
	 */
	@JsonProperty("actionSubName")
	public void setActionSubName(String actionSubName) {
		this.actionSubName = actionSubName;
	}

	/**
	 *
	 * @return The correlationId
	 */
	@JsonProperty("correlationId")
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 *
	 * @param correlationId
	 *            The correlationId
	 */
	@JsonProperty("correlationId")
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 *
	 * @return The correlationId2
	 */
	@JsonProperty("correlationId2")
	public String getCorrelationId2() {
		return correlationId2;
	}

	/**
	 *
	 * @param correlationId2
	 *            The correlationId2
	 */
	@JsonProperty("correlationId2")
	public void setCorrelationId2(String correlationId2) {
		this.correlationId2 = correlationId2;
	}

	/**
	 *
	 * @return The consumerId
	 */
	@JsonProperty("consumerId")
	public String getConsumerId() {
		return consumerId;
	}

	/**
	 *
	 * @param consumerId
	 *            The consumerId
	 */
	@JsonProperty("consumerId")
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	/**
	 *
	 * @return The endUserID
	 */
	@JsonProperty("endUserID")
	public String getEndUserID() {
		return endUserID;
	}

	/**
	 *
	 * @param endUserID
	 *            The endUserID
	 */
	@JsonProperty("endUserID")
	public void setEndUserID(String endUserID) {
		this.endUserID = endUserID;
	}

	/**
	 *
	 * @return The extraKeyVal
	 */
	@JsonProperty("extraKeyVal")
	public ExtraKeyVal getExtraKeyVal() {
		return extraKeyVal;
	}

	/**
	 *
	 * @param extraKeyVal
	 *            The extraKeyVal
	 */
	@JsonProperty("extraKeyVal")
	public void setExtraKeyVal(ExtraKeyVal extraKeyVal) {
		this.extraKeyVal = extraKeyVal;
	}

	/**
	 *
	 * @return The timeTakenAction
	 */
	@JsonProperty("timeTakenAction")
	public long getTimeTakenAction() {
		return timeTakenAction;
	}

	/**
	 *
	 * @param timeTakenAction
	 *            The timeTakenAction
	 */
	@JsonProperty("timeTakenAction")
	public void setTimeTakenAction(long timeTakenAction) {
		this.timeTakenAction = timeTakenAction;
	}

	/**
	 *
	 * @return The status
	 */
	@JsonProperty("status")
	public Audit.Status getStatus() {
		return status;
	}

	/**
	 *
	 * @param status
	 *            The status
	 */
	@JsonProperty("status")
	public void setStatus(Audit.Status status) {
		this.status = status;
	}

	/**
	 *
	 * @return The errCode
	 */
	@JsonProperty("errCode")
	public String getErrCode() {
		return errCode;
	}

	/**
	 *
	 * @param errCode
	 *            The errCode
	 */
	@JsonProperty("errCode")
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	/**
	 *
	 * @return The errDetailedDescription
	 */
	@JsonProperty("errDetailedDescription")
	public String getErrDetailedDescription() {
		return errDetailedDescription;
	}

	/**
	 *
	 * @param errDetailedDescription
	 *            The errDetailedDescription
	 */
	@JsonProperty("errDetailedDescription")
	public void setErrDetailedDescription(String errDetailedDescription) {
		this.errDetailedDescription = errDetailedDescription;
	}

	@Override
	public String toString() {
		if (mapper == null) {
			mapper = new ObjectMapper();
			// AfterburnerModule is available only on Java 7 +. It optimises the underlying serializers and deserializers
	//		mapper.registerModule(new AfterburnerModule());
		}
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return "";
		}
	}
	public enum Status {

		SUCCESS("SUCCESS"), ERROR("ERROR"), WARNING("WARNING"), TIMEOUT(
				"TIMEOUT");
		private final String value;
		private final static Map<String, Audit.Status> CONSTANTS = new HashMap<String, Audit.Status>();

		static {
			for (Audit.Status c : values()) {
				CONSTANTS.put(c.value, c);
			}
		}

		private Status(String value) {
			this.value = value;
		}

		@JsonValue
		@Override
		public String toString() {
			return this.value;
		}

		@JsonCreator
		public static Audit.Status fromValue(String value) {
			Audit.Status constant = CONSTANTS.get(value);
			if (constant == null) {
				throw new IllegalArgumentException(value);
			} else {
				return constant;
			}
		}

	}

}