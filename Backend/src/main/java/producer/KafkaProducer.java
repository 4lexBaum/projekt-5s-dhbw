package producer;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer {
	
	private ProducerConfig config;
	//singleton instance
	private static KafkaProducer producer;
	
	private KafkaProducer(){
		Properties props = new Properties();
	    props.put("zk.connect", "127.0.0.1:2181");
	    props.put("serializer.class", "kafka.serializer.StringEncoder");
	    props.put("metadata.broker.list", "127.0.0.1:9092");
	    props.put("producer.type","async");
	    config = new ProducerConfig(props);
	}
	
	public void sendMessage(String topic, String message){
		Producer<String, String> producer = new Producer<String, String>(config);
		KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, message);
		producer.send(data);
		producer.send(data);
		producer.send(data);
		producer.close();
	}
	
	public static KafkaProducer getKafkaProducer(){
		if(KafkaProducer.producer == null){
			KafkaProducer.producer = new KafkaProducer();
		}
		
		return KafkaProducer.producer;
	}	
	
	public static void main(String[] args){
		
		KafkaProducer.getKafkaProducer().sendMessage("123", "Hallo123");
	}
}
