package Analysis

/**
  * Created by fabian on 12.11.16.
  */

import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import KafkaConnectivity.KafkaController
import org.apache.spark.rdd.RDD

import collection.mutable

object MaterialDrillingSpeed extends AnalysisParent {

  override val kafkaTopicSend: String = "MaterialDrillingSpeed"// this.getClass.getSimpleName

  override def runAnalysis(rdd: RDD[ManufacturingData]): Unit = {

    val map = rdd.map(manuData => mapping(manuData))
      .groupByKey()
      .sortByKey()
      .map(x => (x._1, average(x._2)))
      .collect()
      .map(elem => elem._1 -> elem._2)
      .toMap

//    print(kafkaTopicsSend + " " + JsonParser.mapToJsonDouble(map))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonDouble(map), kafkaTopicSend)
  }

  override def mapping(manufacturingData: ManufacturingData): (String, Double) ={

    val machineData = manufacturingData.machineData
    val speedList = for(elem <- machineData) yield checkElement(elem)
    val filteredList = speedList.filter(_ != -1)
    val avg = filteredList.sum/filteredList.size.toDouble

    (manufacturingData.materialNumber, avg)
  }

  override def checkElement(element: MachineData): Double ={
    if(element.itemName.equals("DRILLING_SPEED")){
      return element.value.toDouble
    }
    -1
  }

//  def updateMap(manuData: ManufacturingData): Unit = {
//    val key = manuData.materialNumber
//    val machineData = manuData.machineData
//
//    val speedList = for (elem <- machineData) yield checkElement(elem)
//    val filteredList = speedList.filter(v => v >= 0)
//    val avg = filteredList.sum / filteredList.size.toDouble
//    val value = map.get(key)
//
//    if (value.isEmpty) {
//      map += (key -> avg)
//    } else {
//      map.update(key, {(value.get + avg).toFloat/2})
//    }
//  }
//
//  def checkElement(element: MachineData): Double = {
//    if (element.itemName.equals("DRILLING_SPEED")) {
//      return element.value.toDouble
//    }
//    -1
//  }

}
