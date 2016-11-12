package Analysis

import JsonParser.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController

import collection.mutable


/**
  * Created by fabian on 06.11.16.
  */
object QualityCustomer extends AnalysisParent{

  override val kafkaTopicsSend: String = "QualityCustomer"//this.getClass.getSimpleName.replace("$", "")
  private val map: mutable.Map[String, Int] = mutable.Map[String,Int]().withDefaultValue(0)

  override def runAnalysis(list: List[ManufacturingData]): Unit = {

    list.foreach(manuData => updateMap(manuData))
    //print(kafkaTopicsSend + " " + JsonParser.mapToJsonInt(map))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonInt(map), kafkaTopicsSend)
  }

  def updateMap(manuData: ManufacturingData): Unit ={
    val key = manuData.customerNumber
    val value = map.get(key)

    if (manuData.analysisData.overallStatus.equals("NOK")) {
      map.update(key, map(key)+1)
    }
  }
}
