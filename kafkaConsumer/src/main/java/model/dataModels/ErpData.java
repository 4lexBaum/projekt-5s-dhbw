package model.dataModels;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class ErpData.
 * The XMl data is converted into
 * objects of this class.
 * @author Daniel
 *
 */
@XmlRootElement
public class ErpData extends Data {
	private String customerNumber;
	private String materialNumber;
	private String orderNumber;
	private String timeStamp;
	
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
	
	@XmlElement
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public String getMaterialNumber() {
		return materialNumber;
	}
	
	@XmlElement
	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}
	
	@XmlElement
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	
	@XmlElement
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}
