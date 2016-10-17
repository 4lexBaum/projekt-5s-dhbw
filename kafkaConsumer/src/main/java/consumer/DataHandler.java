package consumer;

import db.DatabaseManager;
import model.dataModels.Data;
import model.dataModels.ErpData;
import model.dataModels.MachineData;
import model.dataModels.ManufacturingData;
import model.dataModels.SpectralAnalysisData;

public class DataHandler {
	private ManufacturingData currentData;
	private ManufacturingData previousData;
	
	//singleton instance
	private static DataHandler dataHandler;
	
	/**
	 * Constructor DataHandler.
	 * Singleton-Pattern! => private constructor.
	 */
	private DataHandler() {
		currentData = new ManufacturingData();
	}
	
	/**
	 * getDataHandler method.
	 * Is used to obtain an instance of the DataHandler.
	 * @return
	 */
	public static DataHandler getDataHandler() {
		if(dataHandler == null) {
			dataHandler = new DataHandler();
		}
		return dataHandler;
	}
	
	/**
	 * Adds data provided by the consumers.
	 * @param data
	 */
	public void addConsumerData(Data data) {
		if(data instanceof ErpData) {
			swap();
			currentData.setErpData((ErpData) data);
		} else if(data instanceof MachineData) {
			currentData.appendMachineData((MachineData) data);
		} else if(data instanceof SpectralAnalysisData) {
			previousData.setAnalysisData((SpectralAnalysisData) data);
			DatabaseManager.getManager().insertManifacturingDocument(previousData);	
		}
	}
	
	/**
	 * Swaps currentData and previousData.
	 */
	private void swap() {
		previousData = currentData;
		currentData = new ManufacturingData();
	}
}