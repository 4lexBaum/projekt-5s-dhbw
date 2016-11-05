package KafkaConnectivity

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

import org.apache.log4j.{Level, Logger}

import scala.collection.mutable.ListBuffer

object KafkaConsumer {

  var dataList = new ListBuffer[ManufacturingData]()
  val kafka = ProducerForKafka.getProducer(ProducerForKafka.mandatoryOptions)

  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setAppName("KafkaConsumer")
    val ssc = new StreamingContext(sparkConf, Seconds(1))
    ssc.checkpoint("checkpoint")

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "kafka:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "example",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topicsSet = List("prodData").toSet
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topicsSet, kafkaParams))

    stream.map(record => record.value)
      .foreachRDD(dataRDD => dataRDD.foreach{
        production => appendList(production)
      })

    ssc.start()
    ssc.awaitTermination()
  }

  def appendList(data: String): Unit = {
    val manufacturingData = new ManufacturingData(data)
    dataList += manufacturingData
    print("############################################################################################################################################\n" + data +
      "\n############################################################################################################################################")
    ProducerForKafka.send(kafka, "kafkatest", data)
    println("Message sent")
  }
}
