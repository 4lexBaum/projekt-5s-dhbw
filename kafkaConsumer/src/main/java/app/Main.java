package app;

import consumer.KafkaConsumer;
import db.DatabaseManager;
import model.stateMachine.ProductionStateMachine;

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
		new KafkaConsumer(Constants.KAFKA_PORT, Constants.KAFKA_TOPIC, stateMachine).start();
	}
}