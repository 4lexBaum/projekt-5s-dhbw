package KafkaConnectivity

import JsonHandling.ManufacturingData
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

import scala.collection.mutable.ListBuffer

object KafkaConsumer {

  val kafkaParams: Map[String, Object] = Map(
    "bootstrap.servers" -> "kafka:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "GroupID",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  /**
    * Create Spark StreamingContext and set checkpoint
    * Batch interval ist set to 32s
    * @return Spark StreamingContext
    */

  def getStreamingContext: StreamingContext = {
    val sparkConf = new SparkConf().setAppName("KafkaConsumer")
    val streamingContext = new StreamingContext(sparkConf, Seconds(300))
    streamingContext.checkpoint("checkpoint")
    streamingContext
  }

  /**
    * start Kafka Inputstream
    * @param streamingContext Spark StreamingContext
    * @param topics Set of topics to listen to
    * @param params Kafka Params for zookeeper, groupID, etc.
    * @param processValue Function for processing the incoming records
    */

  def startStream(streamingContext: StreamingContext, topics: Set[String], params: Map[String, Object], processValue: (String) => ManufacturingData): DStream[ManufacturingData] = {
    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams))

    val rdd = stream.map(record => processValue(record.value()))

    streamingContext.start()
    streamingContext.awaitTermination()
    rdd
  }

  //    stream.map(record => record.value)
  //      .foreachRDD(rddData => rddData.foreach{element =>
  //        AnalysisController.addValue(element)})
}
