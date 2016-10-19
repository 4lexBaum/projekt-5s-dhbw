package consumer;

import java.util.ArrayList;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import app.Constants;
import converter.ErpDataConverter;
import model.dataModels.ErpData;

/**
 * Class ErpDataConsumer.
 * Creates a thread which connects to the
 * amqp server.
 * @author Daniel
 *
 */
public class ErpDataConsumer implements Consumer, Runnable {
	private Connection connection;
	private String topicname;
	
	//singleton instance
	private static ErpDataConsumer consumer;
	
	//list of listeners to be notified in case of an event
    private static ArrayList<ErpDataListener> listeners;
	
	/**
	 * Constructor AmqConsumer.
	 * Singleton-Pattern! => private constructor.
	 * Creates connection.
	 * @param port
	 * @param topicname
	 */
	private ErpDataConsumer(int port, String topicname) {
		this.topicname = topicname; 
		String server = "tcp://" + Constants.getIPAddress() + ":" + port;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("demo1", "demo1", server);
		
		try {
			connection = connectionFactory.createConnection();
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		listeners = new ArrayList<>();
	}
	
	public static void setOnErpDataListener(ErpDataListener listener) {
		listeners.add(listener);
	}
	
	public static void removeErpDataListener(ErpDataListener listener) {
		listeners.remove(listener);
	}
	
	/**
     * Notifies all listeners.
     * @param data
     */
    private void propagateEvent(ErpData data) {
    	for(ErpDataListener listener : listeners) {
    		listener.onErpData(data);
    	}
    }
	
	/**
	 * getConsumer method.
	 * Is used to obtain an instance of the AmqConsumer.
	 * @param port
	 * @param topicname
	 * @return
	 */
	public static ErpDataConsumer getConsumer(int port, String topicname) {
		if(consumer == null) {
			consumer = new ErpDataConsumer(port, topicname);
		}
		return consumer;
	}
	
	@Override
	public void run() {
		try {
			//create session and destination
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			//create destination
			Destination destination = session.createTopic(topicname);
			
			//create consumer
			MessageConsumer consumer = session.createConsumer(destination);
			
			//print messages
			consumer.setMessageListener(message -> {
				if(message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String text = "";
					
					try {
						text = textMessage.getText();
					} catch (JMSException e) {
						e.printStackTrace();
					}
					
					//convert xml message and pass data to data handler
					ErpDataConverter xmlConverter = new ErpDataConverter();
					propagateEvent((ErpData) xmlConverter.convert(text));
				} else {
					System.out.println("Received: " + message);
				}
			});
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
