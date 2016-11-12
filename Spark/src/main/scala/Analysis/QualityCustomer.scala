package Analysis

import JsonParser.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController

import collection.mutable


/**
  * Created by fabian on 06.11.16.
  */
object QualityCustomer extends AnalysisParent{

  override val kafkaTopicsSend: String = "QualityCustomer"//this.getClass.getSimpleName.replace("$", "")
  val kafkaTopicsSendPercentage: String = "QualityCustomerPercentage"//this.getClass.getSimpleName.replace("$", "")
  private val map: mutable.Map[String, Int] = mutable.Map[String,Int]()
  private val mapPercentage: mutable.Map[String, String] = mutable.Map[String,String]()


  override def runAnalysis(list: List[ManufacturingData]): Unit = {

    list.foreach(manuData => updateMap(manuData))

    val total = CustomerOrderAmount.runAnalysisWithReturn(list)
    total.foreach(element => calculatePercentage(element))

    //print(kafkaTopicsSend + " " + JsonParser.mapToJsonInt(map))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonInt(map), kafkaTopicsSend)
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonString(mapPercentage), kafkaTopicsSendPercentage)
    map.empty
    mapPercentage.empty
  }

  def updateMap(manuData: ManufacturingData): Unit ={
    val key = manuData.customerNumber
    val value = map.get(key)

    if(value.isEmpty){
      map += (key -> 0)
    }

    if (manuData.analysisData.overallStatus.equals("NOK")) {
      map.update(key, map(key) + 1)
    }
  }

  def calculatePercentage(keyValue: (String,Int)): Unit ={
    val key = keyValue._1
    val value = map.get(key)
    if(value.isEmpty){
      mapPercentage + (key -> 100 + "%")
      return
    }
    mapPercentage + (key -> value.get / keyValue._2 + "%")
  }
}
