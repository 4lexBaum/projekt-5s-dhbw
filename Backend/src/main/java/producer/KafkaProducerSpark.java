package producer;

import java.util.Properties;

import app.Constants;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import model.dataModels.ErpData;

/**
 * Class KafkaProducer.
 * This class is responsible for putting the
 * manufacturing data into kafka.
 * @author Daniel
 *
 */
@SuppressWarnings("all")
public class KafkaProducerSpark {
	private Producer<String, String> producer;
	
	/**
	 * Constructor.
	 */
	public KafkaProducerSpark() {
		Properties props = new Properties();
        props.put("zk.connect", Constants.TEST_LOCAL 
    		? Constants.getIPAddress() + ":" + Constants.KAFKA_PORT 
			: "kafka:" + Constants.KAFKA_PORT);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", Constants.TEST_LOCAL 
    		? Constants.getIPAddress() + ":" + Constants.KAFKA_BROKER_PORT
			: "kafka:" + Constants.KAFKA_BROKER_PORT);
 
        ProducerConfig config = new ProducerConfig(props);
        producer = new Producer<>(config);
	}
	
	/**
	 * Sends messages to the kafka broker.
	 * @param data (json string to be sent)
	 * @param topic (topic for the data)
	 */
	public void send(String data, String topic) {
		KeyedMessage<String, String> msg = new KeyedMessage<>(
			topic, data
		);
		
        producer.send(msg);
        
        //producer must be closed to flush data
        producer.close();
	}
}