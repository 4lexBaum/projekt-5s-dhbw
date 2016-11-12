package Analysis

import JsonParser.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController

import scala.collection.mutable
import scala.collection.mutable.Map

/**
  * Created by fabian on 12.11.16.
  */
object MaterialDuration extends AnalysisParent{

  override val kafkaTopicsSend: String = "MaterialDuration" //this.getClass.getSimpleName
  private val map: mutable.Map[String, Double] = mutable.Map[String, Double]().withDefaultValue(0.0)

  override def runAnalysis(list: List[ManufacturingData]): Unit = {

    list.foreach(manuData => updateMap(manuData))
    //print(kafkaTopicsSend + " " + JsonParser.mapToJsonDouble(map))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonDouble(map), kafkaTopicsSend)
  }

  def updateMap(manuData: ManufacturingData): Unit ={
    val key = manuData.materialNumber
    val productionTime = manuData.machineData.last.timestamp.toDouble - manuData.machineData.head.timestamp.toDouble

    if(map(key) == 0.0){
      map.update(key, productionTime)
    }else{
      map.update(key, (map(key) + productionTime)/2)
    }
  }

}
