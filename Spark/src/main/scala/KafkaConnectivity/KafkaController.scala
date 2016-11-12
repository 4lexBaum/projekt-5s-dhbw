package KafkaConnectivity

import JsonParser.{JsonParser, ManufacturingData}
import Analysis.{AnalysisController, AnalysisParent}
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

/**
  * Created by fabian on 05.11.16.
  */
object KafkaController {

  val kafkaTopicsReceive: Set[String] = Set("manufacturingData")
  val kafkaTopicsSend: String = "kafkatest"

  /**
    * Main method
    * @param args nothing required
    */

  def main(args: Array[String]): Unit = {

    //    val json = "\n{\"customerNumber\":\"4715\",\"materialNumber\":\"9823\",\"orderNumber\":\"c5cc96a3-208d-48e9-9d7e-6fa50c0494f0\"," +
    //      "\"timeStamp\":\"2016-11-05T16:36:15.122+01:00\",\"machineData\":[{\"value\":\"false\",\"status\":\"GOOD\",\"itemName\":\"L1\"," +
    //      "\"timestamp\":1478360177499},{\"value\":\"true\",\"status\":\"GOOD\",\"itemName\":\"L1\",\"timestamp\":1478360208669}," +
    //      "{\"value\":\"false\",\"status\":\"GOOD\",\"itemName\":\"L2\",\"timestamp\":1478360278739},{\"value\":\"true\",\"status\":\"GOOD\"," +
    //      "\"itemName\":\"L2\",\"timestamp\":1478360308789},{\"value\":\"false\",\"status\":\"GOOD\",\"itemName\":\"L3\",\"timestamp\":1478360378799}," +
    //      "{\"value\":\"true\",\"status\":\"GOOD\",\"itemName\":\"MILLING\",\"timestamp\":1478360408849},{\"value\":\"12480\"," +
    //      "\"status\":\"GOOD\",\"itemName\":\"MILLING_SPEED\",\"timestamp\":1478360418869},{\"value\":\"149.84\",\"status\":\"GOOD\"," +
    //      "\"itemName\":\"MILLING_HEAT\",\"timestamp\":1478360468909},{\"value\":\"172.316\",\"status\":\"GOOD\",\"itemName\":\"MILLING_HEAT\"," +
    //      "\"timestamp\":1478360518969},{\"value\":\"15000\",\"status\":\"GOOD\",\"itemName\":\"MILLING_SPEED\",\"timestamp\":1478360589049}," +
    //      "{\"value\":\"224.76\",\"status\":\"GOOD\",\"itemName\":\"MILLING_HEAT\",\"timestamp\":1478360599079},{\"value\":\"209.77599999999998\"," +
    //      "\"status\":\"GOOD\",\"itemName\":\"MILLING_HEAT\",\"timestamp\":1478360669139},{\"value\":\"0\",\"status\":\"GOOD\"," +
    //      "\"itemName\":\"MILLING_SPEED\",\"timestamp\":1478360729199},{\"value\":\"130.98\",\"status\":\"GOOD\",\"itemName\":\"MILLING_HEAT\"," +
    //      "\"timestamp\":1478360739259},{\"value\":\"39.769999999999996\",\"status\":\"GOOD\",\"itemName\":\"MILLING_HEAT\"," +
    //      "\"timestamp\":1478360769289},{\"value\":\"false\",\"status\":\"GOOD\",\"itemName\":\"MILLING\",\"timestamp\":1478360779329}," +
    //      "{\"value\":\"true\",\"status\":\"GOOD\",\"itemName\":\"L3\",\"timestamp\":1478360819359},{\"value\":\"false\",\"status\":\"GOOD\"," +
    //      "\"itemName\":\"L4\",\"timestamp\":1478360869429},{\"value\":\"true\",\"status\":\"GOOD\",\"itemName\":\"DRILLING\",\"timestamp\":1478360909509}" +
    //      ",{\"value\":\"15500\",\"status\":\"GOOD\",\"itemName\":\"DRILLING_SPEED\",\"timestamp\":1478360919549}," +
    //      "{\"value\":\"207.8\",\"status\":\"GOOD\",\"itemName\":\"DRILLING_HEAT\",\"timestamp\":1478360969579}," +
    //      "{\"value\":\"238.97\",\"status\":\"GOOD\",\"itemName\":\"DRILLING_HEAT\",\"timestamp\":1478361019629}," +
    //      "{\"value\":\"18500\",\"status\":\"GOOD\",\"itemName\":\"DRILLING_SPEED\",\"timestamp\":1478361089669}," +
    //      "{\"value\":\"311.70000000000005\",\"status\":\"GOOD\",\"itemName\":\"DRILLING_HEAT\",\"timestamp\":1478361099709}," +
    //      "{\"value\":\"290.92\",\"status\":\"GOOD\",\"itemName\":\"DRILLING_HEAT\",\"timestamp\":1478361169789}," +
    //      "{\"value\":\"0\",\"status\":\"GOOD\",\"itemName\":\"DRILLING_SPEED\",\"timestamp\":1478361219819}," +
    //      "{\"value\":\"120.0\",\"status\":\"GOOD\",\"itemName\":\"DRILLING_HEAT\",\"timestamp\":1478361229829}," +
    //      "{\"value\":\"false\",\"status\":\"GOOD\",\"itemName\":\"DRILLING\",\"timestamp\":1478361239879}," +
    //      "{\"value\":\"true\",\"status\":\"GOOD\",\"itemName\":\"L4\",\"timestamp\":1478361269949}," +
    //      "{\"value\":\"false\",\"status\":\"GOOD\",\"itemName\":\"L5\",\"timestamp\":1478361370019}," +
    //      "{\"value\":\"true\",\"status\":\"GOOD\",\"itemName\":\"L5\",\"timestamp\":1478361400119}],\"analysisData\":{\"em1\":663.0442583140905," +
    //      "\"em2\":36.4468245352747,\"a1\":22.554859129554828,\"a2\":9.473040834413027,\"b1\":14954.869842934473,\"b2\":345.2622571073438," +
    //      "\"overallStatus\":\"NOK\",\"ts_start\":1478360304766,\"ts_stop\":1478360308768}}\n"

    //    val data = addValue(json)

    //Send random string to make topic available for nodejs server
    //Otherwise topic not found exception.


    val source: String = Source.fromFile("file:///home/fabian/Documents/GitProjects/projekt-5s-dhbw/Spark/JsonTestData").getLines.mkString
    addValue(source)

    //KafkaConsumer.startStream(KafkaConsumer.getStreamingContext,
    //  kafkaTopicsReceive, KafkaConsumer.kafkaParams, addValue)

  }

  /**
    * Add Json String to ManufacturingData List and send it back to Kafka as Json
    * @param inputData Json String of ManufacturingData
    */

  def addValue(inputData: String): Unit = {

    val manufacturingData = JsonParser.jsonToManufacturingData(inputData)
    AnalysisController.runAllAnalysis(manufacturingData)

//    val message = JsonParser.manufacturingDataToJson(manufacturingData)
//
//    sendStringViaKafka(message, kafkaTopicsSend)

  }

  /**
    * Send String via Kafka to topic and close Producer afterwards
    * @param message Message itself
    * @param topic The topic the message is available at
    */

  def sendStringViaKafka(message: String, topic: String): Unit = {
    val kafka = KafkaProd.getProducer(KafkaProd.mandatoryOptions)
    KafkaProd.send(kafka, kafkaTopicsSend, message)
    kafka.close()
  }

}

