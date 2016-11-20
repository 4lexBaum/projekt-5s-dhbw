package Analysis.General

import Analysis.AnalysisParent
import Controller.{KafkaController, MongoController}
import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import org.apache.spark.rdd.RDD

/**
  * Created by fabian on 20.11.16.
  */
class OverallOrderAmount extends AnalysisParent{

  override val kafkaTopicSend: String = "OverallOrderAmount"//this.getClass.getSimpleName.replace("$", "")

  override def runAnalysis(rdd: RDD[ManufacturingData], kafkaController: KafkaController, mongoController: MongoController): Unit = {

    val map = rdd.map(manuData => mapping(manuData))
      .countByKey()

    val json = JsonParser.mapToJsonLong(map)

    mongoController.writeAnalysisToMongo(json, kafkaTopicSend)
    kafkaController.sendStringViaKafka(json, kafkaTopicSend)
  }

  override def mapping(manufacturingData: ManufacturingData): (String, Double) ={
    ("key", 1.0)
  }

  override def checkElement(element: MachineData): Double = {
    -1.0
  }

}
