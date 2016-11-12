package Analysis

import JsonParser.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController

import scala.collection.mutable
import scala.collection.mutable.Map

/**
  * Created by fabian on 12.11.16.
  */
object QualityMaterial extends AnalysisParent{

  override val kafkaTopicsSend: String = this.getClass.getSimpleName.replace("$", "")
  private val map: mutable.Map[String, Int] = mutable.Map[String,Int]().withDefaultValue(0)

  override def runAnalysis(list: List[ManufacturingData]): Unit = {

    list.foreach(manuData => updateMap(manuData))
    print(kafkaTopicsSend + " " + JsonParser.mapToJsonInt(map))
    //KafkaController.sendStringViaKafka(JsonParser.mapToJsonInt(map), kafkaTopicsSend)
  }

  def updateMap(manuData: ManufacturingData): Unit ={
    val key = manuData.materialNumber
    val value = map.get(key)

    if (manuData.analysisData.overallStatus.equals("OK")) {
      map.update(key, map(key)+1)
    }
  }

}
