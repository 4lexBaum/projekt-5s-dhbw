package Analysis

import JsonParser.ManufacturingData
import KafkaConnectivity.KafkaController
import org.json4s.DefaultFormats

import scala.collection.mutable.Map
import org.json4s.jackson.Serialization.writePretty


/**
  * Created by fabian on 06.11.16.
  */
object QualityCustomer extends AnalysisParent{

  override val kafkaTopicsSend: String = "QualityCustomer"
  private val map: Map[String, Int] = Map()

  override def runAnalysis(list: List[ManufacturingData]): Unit = {

    list.foreach(manuData => updateList(manuData))
    implicit val formats = DefaultFormats
    val json = writePretty(map)
    KafkaController.sendStringViaKafka(json, kafkaTopicsSend)
  }

  def updateList(manuData: ManufacturingData): Unit ={
    //if(manuData.analysisData.overallStatus == "NOK") {
      val key = manuData.customerNumber
      var v = map.getOrElseUpdate(key, 1)
      if(v > 1) {
        v += 1
        map + (key -> v)
      }
    //}
  }

}
