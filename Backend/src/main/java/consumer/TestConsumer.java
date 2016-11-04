package consumer;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.AbstractExecutionThreadService;

import app.Constants;

import consumer_listener.MachineDataListener;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;

import kafka.javaapi.consumer.ConsumerConnector;

import kafka.message.MessageAndMetadata;

import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class Consumer.
 * This class is responsible for connecting
 * and reading the kafka stream providing the machine data.
 * @author Daniel
 *
 */
public class TestConsumer extends AbstractExecutionThreadService implements Consumer {
    private ConsumerConfig consumerConfig;
    
    //singleton instance
    private static TestConsumer consumer;
    
    //list of listeners to be notified
    private static CopyOnWriteArrayList<MachineDataListener> listeners;
   
    /**
     * Constructor Consumer.
     */
    private TestConsumer() {
    	listeners = new CopyOnWriteArrayList<>();
    	
    	//String server = "kafka:" + Constants.KAFKA_PORT;
    	String server = Constants.getIPAddress() + ":" + Constants.KAFKA_PORT;
    	
    	//config kafka
        Properties properties = new Properties();
        properties.put("bootstrap.servers", server);
        properties.put("zookeeper.connect", server);
        properties.put("group.id", "kafka-consumer");
        properties.put("client.id", this.getClass().getSimpleName());
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
        properties.put("partition.assignment.strategy", "range");
        
        //create config
        this.consumerConfig = new ConsumerConfig(properties);
    }
    
    /**
     * Initializes the machien data consumer.
     */
    public static void initialize() {
    	if(consumer == null) {
    		consumer = new TestConsumer();
    	}
    	consumer.start();
    }
    
    /**
     * Registers a listener object
     * for the machine data event.
     * @param listener listener to be registered.
     */
    public static void setOnMachineDataListener(MachineDataListener listener) {
    	listeners.add(listener);
    }
    
    /**
     * Removes a listener object
     * from the listeners collection.
     * @param listener
     */
    public static void removeMachineDataListener(MachineDataListener listener) {
    	listeners.remove(listener);
    }
    
    /**
     * Starts the thread.
     */
    @Override
    public void run() {
    	//connect to kafka
        ConsumerConnector connector = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
        Map<String, List<KafkaStream<byte[], byte[]>>> messages = connector.createMessageStreams(
    		ImmutableMap.of(Constants.KAFKA_PRODUCER_TOPIC, 1)
		);
        List<KafkaStream<byte[], byte[]>> messageStreams = messages.get(Constants.KAFKA_PRODUCER_TOPIC);
        ExecutorService executorService = Executors.newFixedThreadPool(messageStreams.size());

        //iterate streams
        for(final KafkaStream<byte[], byte[]> messageStream : messageStreams) {
            executorService.submit(() -> {
        		for(MessageAndMetadata<byte[], byte[]> messageAndMetadata : messageStream) {
        			System.out.println(new String(messageAndMetadata.message()));
        		}
            });
        }
    }
}