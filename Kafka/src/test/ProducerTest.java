package test;

import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

@SuppressWarnings("all")
public class ProducerTest {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("zk.connect", "localhost:2181");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", "localhost:9092");
 
        ProducerConfig config = new ProducerConfig(props);
 
        Producer<String, String> producer = new Producer<String, String>(config);
 
        KeyedMessage<String, String> data = new KeyedMessage<String, String>("demo", generateText());
        producer.send(data);
        producer.close();
    }
    
    public static String generateText() {
    	String text = "";
    	int textLength = (int) ((Math.random() * 1000) + 100);
    	
    	for(int i = 0; i < textLength; i++) {
    		text += generateWord() + " ";
    	}
    	return text;
    }
    
    public static String generateWord() {
    	char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    	int wordLength = (int) ((Math.random() * 10) + 1);
    	StringBuilder sb = new StringBuilder();
    	Random random = new Random();
    	
    	for (int i = 0; i < wordLength; i++) {
    	    char c = chars[random.nextInt(chars.length)];
    	    sb.append(c);
    	}
    	return sb.toString();
    }
}