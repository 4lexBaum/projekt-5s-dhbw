package app;

import consumer.AmqConsumer;
import consumer.MachineDataConsumer;
import consumer.SpectralAnalysisConsumer;

import model.stateMachine.ProductionStateMachine;

/**
 * Main class.
 * Entry point for application.
 * @author Daniel
 *
 */
public class Main {

	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		//execute kafka consumer
		MachineDataConsumer.getConsumer(Constants.KAFKA_PORT, Constants.KAFKA_TOPIC).start();
		
		//execute activeMQ consumer
		new Thread(AmqConsumer.getConsumer(Constants.AMQ_PORT, Constants.AMQ_TOPIC)).start();
		
		//initializes spectral analysis consumer
		SpectralAnalysisConsumer.getConsumer();
		
		//initialize state machine
		ProductionStateMachine.getStateMachine();
	}
}