package Analysis

import JsonParser.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController

import scala.collection.mutable

/**
  * Created by fabian on 12.11.16.
  */
object MaterialDuration extends AnalysisParent{

  override val kafkaTopicsSend: String = "MaterialDuration" //this.getClass.getSimpleName
  private val map: mutable.Map[String, Double] = mutable.Map[String, Double]()

  override def runAnalysis(list: List[ManufacturingData]): Unit = {

    list.foreach(manuData => updateMap(manuData))
    //print(kafkaTopicsSend + " " + JsonParser.mapToJsonDouble(map))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonDouble(map), kafkaTopicsSend)
    map.empty
  }

  def updateMap(manuData: ManufacturingData): Unit ={
    val key = manuData.materialNumber
    val productionTime = manuData.machineData.last.timestamp.toDouble - manuData.machineData.head.timestamp.toDouble
    val value = map.get(key)

    if(value.isEmpty){
      map + (key -> productionTime)
    }else{
      map.update(key, (value.get + productionTime)/2)
    }
  }

}
