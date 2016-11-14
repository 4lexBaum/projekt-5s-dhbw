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
  //val sc: SparkContext = new SparkContext(new SparkConf().setAppName("DataInputStreams").setMaster("local[*]"))


  def main(args: Array[String]): Unit = {

    //Node nicht schnell genug online, evtl. geht das (noch nicht getestet)
    //Thread.sleep(60000)

    //mongo analysis for first time
    //analysisController.runAllAnalysis(mongoConsumer.getMongoData)

    //mit zwei streams fuckt spark irgendwie rum, kp warum
    KafkaController.startKafkaInputStream(JsonHandling.JsonParser.jsonToManufacturingData, analysisController.runAllAnalysis)

  }




}
