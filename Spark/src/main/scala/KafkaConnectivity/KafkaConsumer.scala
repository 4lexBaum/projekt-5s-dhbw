package KafkaConnectivity

import JsonHandling.ManufacturingData
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe


class KafkaConsumer {

  private val kafkaParams: Map[String, Object] = Map(
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

  def getStreamingContext(sc: SparkContext): StreamingContext = {
    //val conf = new SparkConf().setAppName("KafkaStream")
    val streamingContext = new StreamingContext(sc, Seconds(100))
    streamingContext.checkpoint("KafkaCheckpoint")
    streamingContext
  }

  /**
    * start Kafka Inputstream
    * @param streamingContext Spark StreamingContext
    * @param topics Set of topics to listen to
    * @param params Kafka Params for zookeeper, groupID, etc.
    * @param transformInput Function for processing the incoming records
    */

  def startStream(streamingContext: StreamingContext, topics: Set[String], params: Map[String, Object],
                  transformInput: (String) => ManufacturingData, transformForAnalysis: (RDD[ManufacturingData]) => Unit) = {
    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams))

    val tempStream = stream.map(record => transformInput(record.value()))
    tempStream.foreachRDD(rdd => transformForAnalysis(rdd))

    streamingContext.start()
    streamingContext.awaitTermination()
  }

  def getKafkaParams: Map[String, Object] ={
    kafkaParams
  }
}
