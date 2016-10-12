package app;

import consumer.KafkaConsumer;

public class Main {
	
	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		KafkaConsumer kafkaConsumer = new KafkaConsumer(Constants.KAFKA_PORT, Constants.KAFKA_TOPIC);
		kafkaConsumer.start();
	}
}
