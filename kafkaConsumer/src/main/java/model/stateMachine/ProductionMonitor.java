package model.stateMachine;

import java.util.ArrayList;

import consumer.ErpDataConsumer;

public class ProductionMonitor {
	
	//singleton instance
	private static ProductionMonitor productionMonitor;
	
	private static ArrayList<ProductionStateMachine> stateMachines;
	
	/**
	 * Constructor.
	 * This constructor is private
	 * because of the singleton pattern.
	 */
	private ProductionMonitor() {
		stateMachines = new ArrayList<>();
	}
	
	/**
	 * Initializes the production monitor.
	 */
	public static void initialize() {
		if(productionMonitor == null) {
			productionMonitor = new ProductionMonitor();
			
			//set monitor on machine data listener
			ErpDataConsumer.setOnErpDataListener(data -> {
			
				//create new state machine if new production begins
				stateMachines.add(new ProductionStateMachine(data));
			});
		}
	}
}