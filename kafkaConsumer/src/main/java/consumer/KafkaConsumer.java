package consumer;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.AbstractExecutionThreadService;

import app.Constants;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import model.dataModels.ManufacturingData;
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
    private ConsumerConfig consumerConfig;
    private String topicName;
    private JSONConverter converter = new JSONConverter();
    
    private static KafkaConsumer consumer;
   
    /**
     * Constructor Consumer.
     * Singleton-Pattern! => private constructor.
     * Creates the properties for the kafka server.
     * @param port
     * @param topicName
     */
    private KafkaConsumer(int port, String topicName) {
    	String server = Constants.getIPAddress() + ":" + port;
    	
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
     * getConsumer method.
     * Is used to obtain an instance of the KafkaConsumer.
     * @param port
     * @param topicname
     * @param stateMachine
     * @return
     */
    public static KafkaConsumer getConsumer(int port, String topicname) {
    	if(consumer == null) {
    		consumer = new KafkaConsumer(port, topicname);
    	}
    	return consumer;
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
            		int counter = 0;
            		
            		for(MessageAndMetadata<byte[], byte[]> messageAndMetadata : messageStream) {
            			ManufacturingData data = converter.convert(new String(messageAndMetadata.message()));
            			ProductionStateMachine.getStateMachine().trigger(data);
            			
            			if(data.getItemName().equals("L1") && data.getValue().equals("false")) {
            				if(counter > 0) {
            					
            				}
            			}
            		}
            	}
            });
        }
    }
}