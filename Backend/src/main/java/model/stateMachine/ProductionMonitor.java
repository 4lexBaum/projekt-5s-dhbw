package model.stateMachine;

import consumer.ErpDataConsumer;

/**
 * Class ProductionMonitor.
 * This class is responsible for
 * creating a new state machine as soon as
 * a new production begins.
 * @author Daniel
 *
 */
public class ProductionMonitor {
	//singleton instance
	private static ProductionMonitor productionMonitor;
	
	/**
	 * Constructor.
	 */
	private ProductionMonitor() {}
	
	/**
	 * Initializes the production monitor.
	 */
	public static void initialize() {
		if(productionMonitor == null) {
			productionMonitor = new ProductionMonitor();
			
			//set monitor on machine data listener
			ErpDataConsumer.setOnErpDataListener(data -> {
			
				//create new state machine if new production begins
				int serialNumber = new ProductionStateMachine(data).getSerialNumber();
				System.out.println("Production Monitor: Created new state machine: " + serialNumber);
			});
		}
	}
}