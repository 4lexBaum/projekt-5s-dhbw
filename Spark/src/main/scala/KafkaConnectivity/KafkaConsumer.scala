package KafkaConnectivity

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

object KafkaConsumer {

  def main(args: Array[String]) {

    val logFile = "/home/fabian/Documents/output.md"
    val sparkConf = new SparkConf().setAppName("KafkaConsumer")
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    val logData = ssc.textFileStream(logFile).cache()
    ssc.checkpoint("checkpoint")

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "kafka:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "example",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topicsSet = List("prodData", "moreTopics").toSet
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topicsSet, kafkaParams))

    val kafka = ProducerForKafka.getProducer(ProducerForKafka.mandatoryOptions)
    //val data = stream.map(record => record.value)
    //ProducerForKafka.send(kafka, "kafkatest", data)

    ssc.start()
    ssc.awaitTermination()
  }


  //  def createString(data: String): String ={
  //
  //    return " "
  //  }
}
