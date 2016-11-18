package Controller

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fabian on 13.11.16.
  */

object MainController {

  val analysisController: AnalysisController = new AnalysisController()
  val sc: SparkContext = new SparkContext(new SparkConf().setAppName("DataInputStreams"))
  val mongoController: MongoController = new MongoController()
  val kafkaController: KafkaController = new KafkaController()


  def main(args: Array[String]): Unit = {

    // Node takes some time to go online, before that, data cannot be received
    Thread.sleep(70000)

    // Mongo analysis to show something in UI
    mongoController.runAnalysisWithMongoData(analysisController,sc)

    // Start Stream for Spark Streaming Analysis
    kafkaController.startKafkaInputStream(sc, JsonHandling.JsonParser.jsonToManufacturingData, analysisController.runAllAnalysis)

  }

}
