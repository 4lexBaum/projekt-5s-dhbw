package Analysis.Customer

import Analysis.AnalysisParent
import Controller.{KafkaController, MongoController}
import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import org.apache.spark.rdd.RDD

/**
  * Created by fabian on 06.11.16.
  */

class CustomerQuality extends AnalysisParent{

  override val kafkaTopicSend: String = "CustomerQuality"//this.getClass.getSimpleName.replace("$", "")

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
      (manufacturingData.customerNumber, 1)
    }else{
      (manufacturingData.customerNumber, 0)

    }
  }
  override def checkElement(element: MachineData): Double = {
    -1.0
  }


  //  def calculatePercentage(v: (String, Long), map: Map[String, Double]): (String, String) = {
//    val key = v._1
//    val value = map.get(key)
//    if (value.isEmpty) {
//      key -> (0 + "%")
//    }
//    key -> (((value.get/v._2)*100).toString + "%")
//  }

//  def updateMap(manuData: ManufacturingData): Unit ={
//    val key = manuData.customerNumber
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
