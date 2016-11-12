package Analysis

import JsonParser.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController

import scala.collection.mutable.Map

/**
  * Created by fabian on 12.11.16.
  */
object MaterialDuration extends AnalysisParent{

  override val kafkaTopicsSend: String = this.getClass.getSimpleName
  private val map: Map[String, Double] = Map()

  override def runAnalysis(list: List[ManufacturingData]): Unit = {

    list.foreach(manuData => updateMap(manuData))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonDouble(map), kafkaTopicsSend)
  }

  def updateMap(manuData: ManufacturingData): Unit ={
      val key = manuData.materialNumber
      val productionTime = manuData.machineData.last.timestamp - manuData.machineData.head.timestamp

      val v = map.getOrElseUpdate(key, productionTime)
      if(v > 1) {
        map + (key -> (v + productionTime)/2)
      }
  }
}
