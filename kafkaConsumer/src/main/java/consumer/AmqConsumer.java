package consumer;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import app.Constants;
import converter.XMLConverter;

/**
 * Class AmqConsumer.
 * Creates a thread which connects to the
 * AMQP server.
 * @author Daniel
 *
 */
public class AmqConsumer implements Consumer, Runnable {
	
	private Connection connection;
	private String topicname;
	
	private static AmqConsumer consumer;
	
	/**
	 * Constructor AmqConsumer.
	 * Singleton-Pattern! => private constructor.
	 * Creates connection.
	 * @param port
	 * @param topicname
	 */
	private AmqConsumer(int port, String topicname) {
		this.topicname = topicname; 
		String server = "tcp://" + Constants.getIPAddress() + ":" + port;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("demo1", "demo1", server);
		try {
			connection = connectionFactory.createConnection();
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * getConsumer method.
	 * Is used to obtain an instance of the AmqConsumer.
	 * @param port
	 * @param topicname
	 * @return
	 */
	public static AmqConsumer getConsumer(int port, String topicname) {
		if(consumer == null) {
			consumer = new AmqConsumer(port, topicname);
		}
		return consumer;
	}
	
	@Override
	public void run() {
		XMLConverter xmlConverter = new XMLConverter();
		
		try {
			//create session and destination
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = session.createTopic(topicname);
			//create consumer
			MessageConsumer consumer = session.createConsumer(destination);
			
			//print messages
			consumer.setMessageListener(message -> {
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String text = "";
					
					try {
						text = textMessage.getText();
					} catch (JMSException e) {
						e.printStackTrace();
					}
					
					//convert xml message
					System.out.println(text);
					System.out.println(xmlConverter.convert(text));
				} else {
					System.out.println("Received: " + message);
				}
			});
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}
}
