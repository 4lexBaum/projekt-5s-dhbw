package consumer;

/**
 * Class ProductionData.
 * The JSON data is converted into
 * objects of this class.
 * @author Daniel
 *
 */
public class ProductionData {
	private String value;
	private String status;
	private String itemName;
	private long timestamp;
	
	/**
	 * Empty constructor.
	 */
	public ProductionData() {}
	
	/**
	 * toString method.
	 */
	@Override
	public String toString() {
		return value + " " + status + " " + itemName + " " + timestamp;
	}
}
