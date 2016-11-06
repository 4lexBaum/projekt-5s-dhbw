package SparkTest

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

/**
 * Hello world!
 *
 */
object App {

  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setAppName("App").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    ssc.checkpoint("checkpoint")

    val kafkaParams = Map[String, Object](
      "metadata.broker.list" -> "172.18.0.2:9092",
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "example",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

//    val topicsMap = "prodData,machineData".split(",").map((_, "2".toInt)).toMap
//    val lines = KafkaUtils.createStream(ssc, "172.18.0.2:9092", "groupID", topicsMap).map(_._2)

    val topicsSet = List("prodData").toSet
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topicsSet, kafkaParams))

    stream.map(record => printToConsole(record.key, record.value))

  def printToConsole(key:String ,value:String): Unit ={
    print("Key: " + key + ", Value:" + value)
  }


}
}
