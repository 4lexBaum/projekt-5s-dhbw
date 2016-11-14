package Analysis

import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import KafkaConnectivity.KafkaController
import org.apache.spark.rdd.RDD

import collection._

/**
  * Created by fabian on 06.11.16.
  */
object QualityCustomer extends AnalysisParent{

  override val kafkaTopicSend: String = "QualityCustomer"//this.getClass.getSimpleName.replace("$", "")

  override def runAnalysis(rdd: RDD[ManufacturingData]): Unit = {

    val map = rdd.map(manuData => mapping(manuData))
      .groupByKey()
      .sortByKey()
      .map(x => (x._1, sum(x._2)))
      .collect()
      .map(elem => elem._1 -> elem._2)
      .toMap

//    print(kafkaTopicsSend + " " + JsonParser.mapToJsonInt(map))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonDouble(map), kafkaTopicSend)
  }

  override def mapping(manufacturingData: ManufacturingData): (String, Double) ={

    if (manufacturingData.analysisData.overallStatus.equals("NOK")) {
      (manufacturingData.materialNumber, 1)
    }else{
      (manufacturingData.materialNumber, 0)

    }
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
