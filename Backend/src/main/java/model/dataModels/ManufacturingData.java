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
	 * Constructor.
	 */
	public ManufacturingData() {}
	
	/**
	 * Creates a string representation
	 * of this object.
	 * @return
	 */
	@Override
	public String toString() {
		return customerNumber + " " + materialNumber + " "
		+ orderNumber + " " + timeStamp + " "
		+ machineData + " " + analysisData;
	}
	
	/*
	 * Getters and Setters. 
	 */
	
	/**
	 * Adds erp data.
	 * @param data
	 */
	public void setErpData(ErpData data) {
		this.customerNumber = data.getCustomerNumber();
		this.materialNumber = data.getMaterialNumber();
		this.orderNumber = data.getOrderNumber();
		this.timeStamp = data.getTimeStamp();
	}
	
	/**
	 * Appends machine data to the array.
	 * @param data
	 */
	public void appendMachineData(MachineData data) {
		if(this.machineData == null) {
			this.machineData = new MachineData[1];
			machineData[0] = data;
		} else {
			int length = this.machineData.length;
			MachineData[] temp = new MachineData[length + 1];
			
			for(int i = 0; i < length; i++) {
				temp[i] = this.machineData[i];
			}
			temp[length] = data;
			this.machineData = temp;
		}
	}
	
	/**
	 * Adds spectral analysis data.
	 * @param analysisData
	 */
	public void setAnalysisData(SpectralAnalysisData analysisData) {
		this.analysisData = analysisData;
	}
	
	public String getCustomerNumber() {
		return customerNumber;
	}

	public String getMaterialNumber() {
		return materialNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public MachineData[] getMachineData() {
		return machineData;
	}

	public SpectralAnalysisData getAnalysisData() {
		return analysisData;
	}
}