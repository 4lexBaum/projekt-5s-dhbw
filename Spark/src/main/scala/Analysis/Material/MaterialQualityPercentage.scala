package Analysis.Material

import Analysis.AnalysisParent
import Controller.{KafkaController, MongoController}
import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import org.apache.spark.rdd.RDD

/**
  * Created by fabian on 18.11.16.
  */

class MaterialQualityPercentage extends AnalysisParent{

  override val kafkaTopicSend: String = "MaterialQualityPercentage"//this.getClass.getSimpleName.replace("$", "")

  override def runAnalysis(rdd: RDD[ManufacturingData], kafkaController: KafkaController, mongoController: MongoController): Unit = {

    val total = rdd.map(manuData => (manuData.materialNumber,1.0))
      .reduceByKey(_ + _)

    val bad = rdd.map(manuData => mapping(manuData))
      .reduceByKey(_ + _)

    val finalRdd = total
      .join(bad)
      .map(x => (x._1, %(x._2._1,x._2._2)))
      .collect()
      .map(elem => elem._1 -> elem._2)
      .toMap

    val json = JsonParser.mapToJsonString(map)

    mongoController.writeAnalysisToMongo(json, kafkaTopicSend)
    kafkaController.sendStringViaKafka(json, kafkaTopicSend)
  }

  override def mapping(manufacturingData: ManufacturingData): (String, Double) ={

    if (manufacturingData.analysisData.overallStatus.equals("NOK")) {
      (manufacturingData.materialNumber, 1)
    }else{
      (manufacturingData.materialNumber, 0)

    }
  }

  override def checkElement(element: MachineData): Double = {
    -1.0
  }
}
