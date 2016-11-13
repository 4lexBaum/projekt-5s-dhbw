package Analysis

import JsonHandling.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController

import scala.collection.mutable

/**
  * Created by fabian on 12.11.16.
  */
object CustomerOrderAmount extends AnalysisParent{

  override val kafkaTopicsSend: String = "CustomerOrderAmount"//this.getClass.getSimpleName.replace("$", "")
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
    val key = manuData.customerNumber
    val value = map.get(key)

    if(value.isEmpty){
      map += (key -> 1)
    }else {
      map.update(key, {map(key) + 1})
    }
  }
}
