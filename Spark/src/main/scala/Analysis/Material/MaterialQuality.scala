package Analysis.Material

import Analysis.AnalysisParent
import Controller.{KafkaController, MongoController}
import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import org.apache.spark.rdd.RDD

/**
  * Created by fabian on 12.11.16.
  */

class MaterialQuality extends AnalysisParent{

  override val kafkaTopicSend: String = "MaterialQuality" //this.getClass.getSimpleName.replace("$", "")

  override def runAnalysis(rdd: RDD[ManufacturingData], kafkaController: KafkaController, mongoController: MongoController): Unit = {

    val map = rdd.map(manuData => mapping(manuData))
      .groupByKey()
      .sortByKey()
      .map(x => (x._1, sum(x._2)))
      .collect()
      .map(elem => elem._1 -> elem._2)
      .toMap

    val json = JsonParser.mapToJsonDouble(map)

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

  override def checkElement(element: MachineData): Double ={
    -1.0
  }

  //  def calculatePercentage(v: (String, Long), map: Map[String, Double]): Unit = {
//    val key = v._1
//    val value = map.get(key)
//    if (value.isEmpty) {
//      mapPercentage += (key -> (0 + "%"))
//      return
//    }
//    mapPercentage += (key -> (((value.get/v._2)*100).toString + "%"))
//  }

//  def updateMap(manuData: ManufacturingData): Unit ={
//    val key = manuData.materialNumber
//    val value = map.get(key)
//
//    if(value.isEmpty){
//      map += (key -> 0)
//    }
//    if (manuData.analysisData.overallStatus.equals("NOK")) {
//      map.update(key, map(key) + 1)
//    }
//  }
}
