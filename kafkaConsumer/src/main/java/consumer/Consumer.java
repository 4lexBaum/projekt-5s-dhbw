package consumer;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.AbstractExecutionThreadService;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.apache.log4j.BasicConfigurator;

import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import converter.JSONConverter;

/**
 * Class Consumer.
 * Creates a thread which connects to the
 * kafka server. The messages are then converted into
 * Java objects using a Converter object.
 * @author Daniel
 *
 */
public class Consumer extends AbstractExecutionThreadService {
    private String topicName;
    //private Logger logger = LoggerFactory.getLogger(Consumer.class);

    private ConsumerConfig consumerConfig;
    private JSONConverter converter = new JSONConverter();
    
    /**
     * Main method.
     * @param args
     */
    public static void main(String[] args) {
    	final String PORT = "2181";
    	
    	//determine ip depeding on the operating system
    	String ip = (System.getProperty("os.name").toLowerCase().matches("(.*)windows(.*)")) 
			? "192.168.99.100" : "127.0.0.1";

    	//BasicConfigurator.configure();
        new Consumer(ip + ":" + PORT, "prodData").start();
    }

    /**
     * Constructor Consumer.
     * Creates the properties for the kafka server.
     * @param server
     * @param topicName
     */
    private Consumer(String server, String topicName) {
    	//config kafka
        Properties properties = new Properties();
        properties.put("bootstrap.servers", server);
        properties.put("zookeeper.connect", server);
        properties.put("group.id", "kafka-consumer");
        properties.put("client.id", this.getClass().getSimpleName());
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
        properties.put("partition.assignment.strategy", "range");
        
        this.consumerConfig = new ConsumerConfig(properties);
        this.topicName = topicName;
    }

    /**
     * Run method.
     * Connects to kafka and starts
     * reading from the kafka broker.
     */
    @Override
    public void run() {
        ConsumerConnector connector = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
        Map<String, List<KafkaStream<byte[], byte[]>>> messages = connector.createMessageStreams(ImmutableMap.of(topicName, 1));
        List<KafkaStream<byte[], byte[]>> messageStreams = messages.get(topicName);
        ExecutorService executorService = Executors.newFixedThreadPool(messageStreams.size());

        for(final KafkaStream<byte[], byte[]> messageStream : messageStreams) {
            executorService.submit(new Runnable() {
            	
            	/**
            	 * Run method.
            	 * Iterate messageStream.
            	 */
            	public void run() {
            		for(MessageAndMetadata<byte[], byte[]> messageAndMetadata : messageStream) {
            			//logger.info("Received: {}", new String(messageAndMetadata.message()));
            			converter.convert(new String(messageAndMetadata.message()));
            		}
            	}
            });
        }
    }
}