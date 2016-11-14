package Analysis

import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import KafkaConnectivity.KafkaController
import org.apache.spark.rdd.RDD

import collection.mutable

/**
  * Created by fabian on 12.11.16.
  */
object MaterialMillingSpeed extends AnalysisParent{

  override val kafkaTopicSend: String = this.getClass.getSimpleName
  private val map: mutable.Map[String, Double] = mutable.Map[String,Double]().withDefaultValue(0)

  override def runAnalysis(rdd: RDD[ManufacturingData]): Unit = {

    rdd.foreach(manuData => updateMap(manuData))
    print(kafkaTopicSend + " " + JsonParser.mapToJsonDouble(map))
    //KafkaController.sendStringViaKafka(JsonParser.mapToJsonDouble(map), kafkaTopicsSend)
  }

  def updateMap(manuData: ManufacturingData): Unit ={
    val key = manuData.materialNumber
    val machineData = manuData.machineData

    val speedList = for(elem <- machineData) yield checkElement(elem)
    val filteredList = speedList.filter(v => v > 0)
    val avg = filteredList.sum/filteredList.size.toDouble

    if(map(key) == 0.0){
      map.update(key, avg)
    }else {
      map.update(key, (map(key) + avg) / 2)
    }
  }

  def checkElement(element: MachineData): Double ={
    if(element.itemName.equals("MILLING_SPEED")){
      return element.value.toDouble
    }
    0.0
  }

}
