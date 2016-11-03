package consumer;

import java.util.concurrent.CopyOnWriteArrayList;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import app.Constants;

import consumer_listener.ErpDataListener;

import converter.ErpDataConverter;

import model.dataModels.ErpData;

/**
 * Class ErpDataConsumer.
 * This class is responsible for connecting
 * and reading the amqp stream providing the erp data.
 * @author Daniel
 *
 */
public class ErpDataConsumer implements Consumer, Runnable {
	private Connection connection;
	
	//converter to convert the raw data into pojos
	ErpDataConverter converter;
	
	//singleton instance
	private static ErpDataConsumer consumer;
	
	//list of listeners to be notified
    private static CopyOnWriteArrayList<ErpDataListener> listeners;
	
	/**
	 * Constructor.
	 */
	private ErpDataConsumer() {
		listeners = new CopyOnWriteArrayList<>();
		converter = new ErpDataConverter();
		
		//String server = "tcp://activemq:" + Constants.AMQ_PORT;
		String server = "tcp://" + Constants.getIPAddress() + ":" + Constants.AMQ_PORT;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("demo1", "demo1", server);
		
		//create connection to amqp server
		try {
			connection = connectionFactory.createConnection();
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initializes the erp data consumer.
	 */
	public static void initialize() {
		if(consumer == null) {
			consumer = new ErpDataConsumer();
		}
		new Thread(consumer).start();
	}
	
	/**
	 * Registers a listener object
	 * for the erp data event.
	 * @param listener listener to be registered.
	 */
	public static void setOnErpDataListener(ErpDataListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Removes a listener object
	 * from the listeners collection.
	 * @param listener listener to be removed.
	 */
	public static void removeErpDataListener(ErpDataListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Starts the thread.
	 */
	@Override
	public void run() {
		try {
			//create session and destination
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			//create destination
			Destination destination = session.createTopic(Constants.AMQ_TOPIC);
			
			//create consumer
			MessageConsumer consumer = session.createConsumer(destination);
			
			//print messages
			consumer.setMessageListener(message -> {
				if(message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String text = "";
					
					try {
						text = textMessage.getText();
					} catch(JMSException e) {
						e.printStackTrace();
					}
					
					//convert xml message and pass data to data handler
					propagateEvent(converter.convert(text));
				} else {
					System.out.println("Received: " + message);
				}
			});
		} catch(JMSException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * Notifies all listeners that have registered.
     * @param data
     */
    private void propagateEvent(ErpData data) {
    	for(ErpDataListener listener : listeners) {
    		listener.onErpData(data);
    	}
    }
}