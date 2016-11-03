package producer;

import java.util.Properties;

import app.Constants;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import model.dataModels.ErpData;
import model.dataModels.ManufacturingData;

/**
 * Class KafkaProducer.
 * This class is responsible for putting the
 * manufacturing data into kafka.
 * @author Daniel
 *
 */
public class KafkaProducer {
	private Producer<String, String> producer;
	
	/**
	 * Constructor.
	 */
	public KafkaProducer() {
		Properties props = new Properties();
		props.put("zk.connect", Constants.getIPAddress() + ":" + Constants.KAFKA_PORT);
        //props.put("zk.connect", "kafka:" + Constants.KAFKA_PORT);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", Constants.getIPAddress() + ":" + Constants.KAFKA_BROKER_PORT);
        //props.put("metadata.broker.list", "kafka:" + Constants.KAFKA_BROKER_PORT);
 
        ProducerConfig config = new ProducerConfig(props);
 
        producer = new Producer<>(config);
	}
	
	/**
	 * Sends messages (manufacturingData) to the kafka broker.
	 * @param manufacturingData
	 */
	public void send(ManufacturingData manufacturingData) {
		KeyedMessage<String, String> data = new KeyedMessage<>(
			Constants.KAFKA_PRODUCER_TOPIC, manufacturingData.toString()
		);
		
        producer.send(data);
        
        //producer must be closed to flush data
        producer.close();
	}
	
	/**
	 * Sends messages (erpData) to the kafka broker.
	 * @param erpData
	 */
	public void send(ErpData erpData) {
		KeyedMessage<String, String> data = new KeyedMessage<>(
			Constants.KAFKA_PRODUCER_TOPIC, erpData.toString()
		);
		
        producer.send(data);
        
        //producer must be closed to flush data
        producer.close();
	}
}