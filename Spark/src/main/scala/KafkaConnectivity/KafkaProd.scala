package KafkaConnectivity

/**
  * Created by fabian on 04.11.16.
  */

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class KafkaProd {

  private val mandatoryOptions: Map[String, Any] = Map(
    "bootstrap.servers" -> "kafka:9092",
    "acks" -> "all",
    "batch.size" -> 16384,
    "linger.ms" -> 1,
    "buffer.memory" -> 33554432,
    "key.serializer" -> "org.apache.kafka.common.serialization.StringSerializer",
    "value.serializer" -> "org.apache.kafka.common.serialization.StringSerializer")

  /**
    * Convert Map to Kafka Property object
    * @param properties Property Map for Serializer, zookeeper, etc
    * @return Properties
    */

  def extractOptions(properties: Map[String, Any]): Properties = {
    val props = new Properties()
    properties.foreach { case (key, value) => props.put(key, value.toString) }
    props
  }

  /**
    * Create Kafka Producer
    * @param properties Kafka Properties for Serializer, zookeeper, etc
    * @return new KafkaProducer
    */

  def getProducer(properties: Map[String, Any]): KafkaProducer[String, String] = {
    new KafkaProducer[String, String](extractOptions(properties))
  }

  /**
    * Close KafkaProducer
    * @param kafkaProducer KafkaProducer
    */

  def close(kafkaProducer: KafkaProducer[String, String]): Unit = kafkaProducer.close()

  /**
    * Send new message with KafkaProducer
    * @param kafkaProducer KafkaProducer
    * @param topic Topic the messages is available at
    * @param message Message itself
    */

  def send(kafkaProducer: KafkaProducer[String, String], topic: String, message: String): Unit = {
    val record = new ProducerRecord(topic, "", message)
    kafkaProducer.send(record)
    kafkaProducer.flush()
  }

  def getMandatoryOptions: Map[String, Any] ={
    mandatoryOptions
  }
}
