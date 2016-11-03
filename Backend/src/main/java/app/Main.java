package app;

//import org.apache.log4j.BasicConfigurator;

import consumer.ErpDataConsumer;
import consumer.MachineDataConsumer;
import consumer.SpectralAnalysisConsumer;
import consumer.TestConsumer;
import model.stateMachine.ProductionMonitor;

/**
 * Main class.
 * Entry point for the application.
 * @author Daniel
 *
 */
public class Main {

	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		//BasicConfigurator.configure();
		//execute erp data consumer
		ErpDataConsumer.initialize();
				
		//execute machine data consumer
		MachineDataConsumer.initialize();
		
		TestConsumer.initialize();
		
		//initialize spectral analysis consumer
		SpectralAnalysisConsumer.initialize();
		
		//initialize production monitor
		ProductionMonitor.initialize();
	}
}