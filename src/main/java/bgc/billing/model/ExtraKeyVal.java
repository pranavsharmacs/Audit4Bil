package bgc.billing.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "CUSTID", "NA" })
public class ExtraKeyVal {

	@JsonProperty("CUSTID")
	private String cUSTID;
	@JsonProperty("NA")
	private String nA;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The cUSTID
	 */
	@JsonProperty("CUSTID")
	public String getCUSTID() {
		return cUSTID;
	}

	/**
	 * 
	 * @param cUSTID
	 *            The CUSTID
	 */
	@JsonProperty("CUSTID")
	public void setCUSTID(String cUSTID) {
		this.cUSTID = cUSTID;
	}

	/**
	 * 
	 * @return The nA
	 */
	@JsonProperty("NA")
	public String getNA() {
		return nA;
	}

	/**
	 * 
	 * @param nA
	 *            The NA
	 */
	@JsonProperty("NA")
	public void setNA(String nA) {
		this.nA = nA;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
