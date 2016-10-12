package app;

import consumer.AmqConsumer;
import consumer.KafkaConsumer;
import db.DatabaseManager;
import model.stateMachine.ProductionStateMachine;

/**
 * Main class.
 * Entry point for application.
 * @author Daniel
 *
 */
@SuppressWarnings("all")
public class Main {
	
	//mongodb manager
	public static DatabaseManager dbManager = new DatabaseManager();
	
	//state machine
	private static ProductionStateMachine stateMachine = new ProductionStateMachine();
	
	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		//new KafkaConsumer(Constants.KAFKA_PORT, Constants.KAFKA_TOPIC, stateMachine).start();
		new Thread(new AmqConsumer(Constants.AMQ_TOPIC, Constants.AMQ_PORT)).start();
	}
}