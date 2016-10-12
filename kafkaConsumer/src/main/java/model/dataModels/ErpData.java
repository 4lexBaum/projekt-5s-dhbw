package model.dataModels;

public class ErpData extends Data {
	String customerNumber;
	String materialNumber;
	String orderNumber;
	String timeStamp;
	
	/**
	 * Print string representation of object.
	 * @return
	 */
	public String toString() {
		return customerNumber + " " + materialNumber + " "
			+ orderNumber + " " + timeStamp;
	}
	
	/*
	 * Getters and Setters.
	 */
	
	public String getCustomerNumber() {
		return customerNumber;
	}
	
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public String getMaterialNumber() {
		return materialNumber;
	}
	
	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}
