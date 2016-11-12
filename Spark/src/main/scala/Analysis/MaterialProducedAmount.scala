package Analysis

import JsonParser.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController

import scala.collection.mutable

/**
  * Created by fabian on 12.11.16.
  */
object MaterialProducedAmount extends AnalysisParent{

  override val kafkaTopicsSend: String = "MaterialProducedAmount"//this.getClass.getSimpleName.replace("$", "")
  private val map: mutable.Map[String, Int] = mutable.Map[String,Int]().withDefaultValue(0)

  def runAnalysisWithReturn(list: List[ManufacturingData]): mutable.Map[String, Int] = {

    list.foreach(manuData => updateMap(manuData))
    //print(kafkaTopicsSend + " " + JsonParser.mapToJsonInt(map))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonInt(map), kafkaTopicsSend)
    val tempMap = map
    map.empty
    tempMap
  }

  def updateMap(manuData: ManufacturingData): Unit = {
    val key = manuData.materialNumber
    map.update(key, map(key) + 1)
  }

}
