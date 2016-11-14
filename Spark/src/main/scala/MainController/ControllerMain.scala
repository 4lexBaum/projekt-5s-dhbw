import Analysis.AnalysisController
import KafkaConnectivity.KafkaController
import MongoConnectivity.MongoConsumer
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fabian on 13.11.16.
  */

object ControllerMain {

  val analysisController: AnalysisController = new AnalysisController()
  val mongoConsumer: MongoConsumer = new MongoConsumer()
  val sc: SparkContext = new SparkContext(new SparkConf().setAppName("DataInputStreams"))


  def main(args: Array[String]): Unit = {

    //Node nicht schnell genug online
    Thread.sleep(70000)

    //mongo analysis for first time
    analysisController.runAllAnalysis(mongoConsumer.getMongoData(sc))

    //mit zwei sparkcontexten fuckt spark rum, also bei mongo und hier übergeben
    KafkaController.startKafkaInputStream(sc: SparkContext, JsonHandling.JsonParser.jsonToManufacturingData, analysisController.runAllAnalysis)

  }




}
