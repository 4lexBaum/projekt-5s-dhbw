package model.dataModels;

/**
 * Class ProductionData.
 * @author Daniel
 *
 */
public class MachineData extends Data {
	private String value;
	private String status;
	private String itemName;
	private long timestamp;
	
	/**
	 * Constructor.
	 */
	public MachineData() {}
	
	/**
	 * Creates a string representation
	 * of this object.
	 * @return
	 */
	@Override
	public String toString() {
		return value + " " + status + " " + itemName + " " + timestamp;
	}

	/*
	 * Getters and Setters. 
	 */
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}