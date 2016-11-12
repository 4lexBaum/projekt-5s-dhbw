package Analysis

import JsonParser.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController

import scala.collection.mutable.Map

/**
  * Created by fabian on 12.11.16.
  */
object QualityMaterial extends AnalysisParent{

  override val kafkaTopicsSend: String = this.getClass.getSimpleName
  private val map: Map[String, Int] = Map()

  override def runAnalysis(list: List[ManufacturingData]): Unit = {

    list.foreach(manuData => updateMap(manuData))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonInt(map), kafkaTopicsSend)
  }

  def updateMap(manuData: ManufacturingData): Unit ={

    val key = manuData.materialNumber
    var v = map.getOrElseUpdate(key, 0)

    if(manuData.analysisData.overallStatus.equals("NOK")) {
        map + (key -> {v += 1})
    }
  }

}
