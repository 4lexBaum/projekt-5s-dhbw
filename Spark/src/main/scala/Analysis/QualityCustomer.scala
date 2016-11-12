package Analysis

import JsonParser.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController

import scala.collection.mutable.Map

import collection.mutable


/**
  * Created by fabian on 06.11.16.
  */
object QualityCustomer extends AnalysisParent{

  override val kafkaTopicsSend: String = this.getClass.getSimpleName
  private val map: mutable.Map[String, Int] = mutable.Map()

  override def runAnalysis(list: List[ManufacturingData]): Unit = {

    list.foreach(manuData => updateMap(manuData))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonInt(map), kafkaTopicsSend)
  }

  def updateMap(manuData: ManufacturingData): Unit ={
    if(manuData.analysisData.overallStatus == "NOK") {
      val key = manuData.customerNumber
      var v = map.getOrElseUpdate(key, 1)
      if(v > 1) {
        v += 1
        map + (key -> v)
      }
    }
  }

}
