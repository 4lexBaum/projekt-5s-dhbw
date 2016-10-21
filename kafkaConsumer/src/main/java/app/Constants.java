package app;

/**
 * Class Constants.
 * Contains all constants used
 * in the application.
 * @author Daniel
 *
 */
public class Constants {

	//kafka constants
	public static final int KAFKA_PORT = 2181;
	public static final String KAFKA_TOPIC = "prodData";
	
	//activeMQ constants
	public static final int AMQ_PORT = 32846;
	public static final String AMQ_TOPIC = "m_orders";
	
	//file system constants
	public static final String PATH_SPECTRAL_ANALYSIS = "/Users/Daniel/Documents/tmp/"; //"/Users/Philip/Documents/Studium/5. Semester/IndustrielleProzesse/TestData";
	
	//mongodb constants
	public static final String DATABASE_NAME = "oip_taktstrasse";
	public static final String DATABASE_COLLECTION_NAME = "manufacturingData";
	
	/**
	 * Returns ip address according
	 * to the operating system.
	 * @return
	 */
	public static String getIPAddress() {
		return (System.getProperty("os.name").toLowerCase().matches("(.*)windows(.*)")) 
			? "192.168.99.100" : "127.0.0.1";
	}
	
	/**
	 * Private constructor prevents
	 * instantiations of this class.
	 */
	private Constants() {}
}