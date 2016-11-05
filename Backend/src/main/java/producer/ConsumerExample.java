package producer;

import java.util.*;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class ConsumerExample {
	static ConsumerConnector consumerConnector;
	
	public static void main(String[] args) {
	Properties props = new Properties();
        props.put("zookeeper.connect", "127.0.0.1:2181");
        props.put("group.id", "test-group");
        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
        
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put("demo", new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> stream =  consumerMap.get("demo").get(0);
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        
        while(it.hasNext()) {
            System.out.println(new String(it.next().message()));
        }
    }
}