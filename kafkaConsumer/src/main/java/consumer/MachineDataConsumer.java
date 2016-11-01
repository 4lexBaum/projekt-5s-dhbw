package consumer;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.AbstractExecutionThreadService;

import app.Constants;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import model.dataModels.MachineData;

import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import converter.MachineDataConverter;

/**
 * Class Consumer.
 * Creates a thread which connects to the
 * kafka server. The messages are then converted into
 * Java objects using a Converter object.
 * @author Daniel
 *
 */
public class MachineDataConsumer extends AbstractExecutionThreadService implements Consumer {
    private ConsumerConfig consumerConfig;
    private String topicName;
    
    //singleton instance
    private static MachineDataConsumer consumer;
    
    //list of listeners to be notified in case of an event
    private static ArrayList<MachineDataListener> listeners;
   
    /**
     * Constructor Consumer.
     * Singleton-Pattern! => private constructor.
     * Creates the properties for the kafka server.
     * @param port
     * @param topicName
     */
    private MachineDataConsumer(int port, String topicName) {
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
        
        //init listeners list
        listeners = new ArrayList<>();
    }
    
    /**
     * getConsumer method.
     * Is used to obtain an instance of the KafkaConsumer.
     * @param port
     * @param topicname
     * @param stateMachine
     * @return
     */
    public static MachineDataConsumer getConsumer(int port, String topicname) {
    	if(consumer == null) {
    		consumer = new MachineDataConsumer(port, topicname);
    	}
    	return consumer;
    }
    
    /**
     * Saves a listener object.
     * @param listener
     */
    public static void setOnMachineDataListener(MachineDataListener listener) {
    	listeners.add(listener);
    }
    
    /**
     * Notifies all listeners.
     * @param data
     */
    private void propagateEvent(MachineData data) {
    	for(MachineDataListener listener : listeners) {
    		listener.onMachineData(data);
    	}
    }

    /**
     * Run method.
     * Connects to kafka and starts
     * reading from the kafka broker.
     */
    @Override
    public void run() {
    	
    	//connect to kafka
        ConsumerConnector connector = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
        Map<String, List<KafkaStream<byte[], byte[]>>> messages = connector.createMessageStreams(ImmutableMap.of(topicName, 1));
        List<KafkaStream<byte[], byte[]>> messageStreams = messages.get(topicName);
        ExecutorService executorService = Executors.newFixedThreadPool(messageStreams.size());

        //iterate over streams
        for(final KafkaStream<byte[], byte[]> messageStream : messageStreams) {
            executorService.submit(() -> {
        		MachineDataConverter converter = new MachineDataConverter();
        		
        		for(MessageAndMetadata<byte[], byte[]> messageAndMetadata : messageStream) {
        			
        			//convert message to string
        			MachineData data = converter.convert(
    					new String(messageAndMetadata.message())
					);
        			
        			//notify all other listeners
        			propagateEvent(data);
        			
        			//pass machine data to data handler
        			DataHandler.getDataHandler().addConsumerData(data);
        		}
            });
        }
    }
}