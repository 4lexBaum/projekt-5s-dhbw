package consumer;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import app.Constants;

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
	
	/**
	 * Constructor.
	 * Creates connection.
	 * @param topic
	 * @param port
	 */
	public AmqConsumer(String topicname, int port) {
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
	
	@Override
	public void run() {
		try {
			//create session and destination
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(topicname);
			//Destination destination = session.createQueue(Constants.AMQ_QUEUE_NAME);
			//create consumer
			MessageConsumer consumer = session.createConsumer(topic);
			
			//print messages
			consumer.setMessageListener(message -> {
				System.out.println(message);
			});
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
