package app;

import consumer.AmqConsumer;
import consumer.KafkaConsumer;
import model.dataModels.ManufacturingData;
import model.stateMachine.ProductionStateMachine;

/**
 * Main class.
 * Entry point for application.
 * @author Daniel
 *
 */
@SuppressWarnings("all")
public class Main {
	
	public static ManufacturingData currentData;
	public static ManufacturingData previousData;
	
	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		//KafkaConsumer.getConsumer(Constants.KAFKA_PORT, Constants.KAFKA_TOPIC).start();
		new Thread(AmqConsumer.getConsumer(Constants.AMQ_PORT, Constants.AMQ_TOPIC)).start();
	}
}