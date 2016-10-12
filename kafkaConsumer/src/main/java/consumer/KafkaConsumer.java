package consumer;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.AbstractExecutionThreadService;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import model.stateMachine.ProductionStateMachine;

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
public class KafkaConsumer extends AbstractExecutionThreadService implements Consumer {
    private String topicName;

    private ConsumerConfig consumerConfig;
    private JSONConverter converter = new JSONConverter();
    private ProductionStateMachine stateMachine;
   
    /**
     * Constructor Consumer.
     * Creates the properties for the kafka server.
     * @param port
     * @param topicName
     */
    public KafkaConsumer(int port, String topicName, ProductionStateMachine stateMachine) {
    	
    	//determine ip depeding on the operating system
    	String ip = (System.getProperty("os.name").toLowerCase().matches("(.*)windows(.*)")) 
			? "192.168.99.100" : "127.0.0.1";
    	
    	String server = ip + ":" + port;
    	
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
        
        this.stateMachine = stateMachine;
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
            			stateMachine.trigger(converter.convert(new String(messageAndMetadata.message())));
            		}
            	}
            });
        }
    }
}