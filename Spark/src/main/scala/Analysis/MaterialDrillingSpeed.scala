package Analysis

/**
  * Created by fabian on 12.11.16.
  */

import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import KafkaConnectivity.KafkaController
import org.apache.spark.rdd.RDD

import collection.mutable

object MaterialDrillingSpeed extends AnalysisParent {

  override val kafkaTopicSend: String = "MaterialDrillingSpeed"
  // this.getClass.getSimpleName
  private val map: mutable.Map[String, Double] = mutable.Map[String, Double]()

  override def runAnalysis(rdd: RDD[ManufacturingData]): Unit = {

    rdd.foreach(manuData => updateMap(manuData))
//    print(kafkaTopicsSend + " " + JsonParser.mapToJsonDouble(map))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonDouble(map), kafkaTopicSend)
    map.empty
  }

  def updateMap(manuData: ManufacturingData): Unit = {
    val key = manuData.materialNumber
    val machineData = manuData.machineData

    val speedList = for (elem <- machineData) yield checkElement(elem)
    val filteredList = speedList.filter(v => v >= 0)
    val avg = filteredList.sum / filteredList.size.toDouble
    val value = map.get(key)

    if (value.isEmpty) {
      map += (key -> avg)
    } else {
      map.update(key, {(value.get + avg).toFloat/2})
    }
  }

  def checkElement(element: MachineData): Double = {
    if (element.itemName.equals("DRILLING_SPEED")) {
      return element.value.toDouble
    }
    -1
  }

}
