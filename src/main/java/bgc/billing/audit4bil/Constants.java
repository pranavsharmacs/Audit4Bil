package bgc.billing.audit4bil;

public class Constants {

	//default values
	public static final int DEFAULT_QUEUESIZE = 50;
	public static final int DEFAULT_NUMBEROFTHREADS = 10;

	//environments
	public static final String ENVIRONMENT_PRO = "PRO";
	public static final String ENVIRONMENT_UAT = "UAT";

	//webservice URLs

	//REST (ElasticSearch)
	//UAT
	public static final String URL_REST_UAT = "http://el1796:9200/";
	//PRO
	public static final String URL_REST_PRO = "http://el1796:9200/";

	//SOAP
	// UAT (mapped to ITT URL of pubsub)
	public static final String URL_SOAP_UAT = "https://apigw-cots-itt.bc/middleware/topicpublish/1.0";
	// PRO
	public static final String URL_SOAP_PRO = "https://apigw-cots.bc/middleware/topicpublish/1.0";
}
