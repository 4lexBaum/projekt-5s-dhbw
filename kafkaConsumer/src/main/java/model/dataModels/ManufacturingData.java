package model.dataModels;

/**
 * Class ManufacturingData.
 * @author Daniel
 *
 */
public class ManufacturingData {
	private String customerNumber;
	private String materialNumber;
	private String orderNumber;
	private String timeStamp;
	
	private MachineData[] machineData;
	private SpectralAnalysisData analysisData;
	
	/**
	 * Constructor ManufacturingData.
	 * @param data
	 */
	public ManufacturingData(ErpData data) {
		this.customerNumber = data.getCustomerNumber();
		this.materialNumber = data.getMaterialNumber();
		this.orderNumber = data.getOrderNumber();
		this.timeStamp = data.getTimeStamp();
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
	
	public MachineData[] getMachineData() {
		return machineData;
	}
	
	/**
	 * Appends machineData to the array.
	 * @param data
	 */
	public void appendMachineData(MachineData data) {
		if(this.machineData == null) {
			this.machineData = new MachineData[1];
		} else {
			int length = this.machineData.length;
			MachineData[] temp = new MachineData[length + 1];
			
			for(int i = 0; i < length; i++) {
				temp[i] = this.machineData[i];
			}
			temp[length + 1] = data;
			this.machineData = temp;
		}
	}
	
	public SpectralAnalysisData getAnalysisData() {
		return analysisData;
	}
	
	public void setAnalysisData(SpectralAnalysisData analysisData) {
		this.analysisData = analysisData;
	}
}
