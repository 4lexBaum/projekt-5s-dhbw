package Analysis

import JsonHandling.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController
import org.apache.spark.rdd.RDD

import collection._


/**
  * Created by fabian on 06.11.16.
  */
object QualityCustomer extends AnalysisParent{

  override val kafkaTopicSend: String = "QualityCustomer"//this.getClass.getSimpleName.replace("$", "")
  val kafkaTopicsSendPercentage: String = "QualityCustomerPercentage"//this.getClass.getSimpleName.replace("$", "")
  private val map: mutable.Map[String, Int] = mutable.Map[String,Int]()
  private val mapPercentage: mutable.Map[String, String] = mutable.Map[String,String]()


  override def runAnalysis(rdd: RDD[ManufacturingData]): Unit = {

    rdd.foreach(manuData => updateMap(manuData))

    val total = CustomerOrderAmount.runAnalysisWithReturn(rdd)
    total.foreach(element => calculatePercentage(element))

//    print(kafkaTopicSend + " " + JsonParser.mapToJsonInt(map))
//    print(kafkaTopicsSendPercentage + " " + JsonParser.mapToJsonString(mapPercentage))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonInt(map), kafkaTopicSend)
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

  def calculatePercentage(keyValue: (String,Long)): Unit ={
    val key = keyValue._1
    val value = map.get(key)
    if(value.isEmpty){
      mapPercentage += (key -> {0 + "%"})
      return
    }

    mapPercentage += (key -> ((((value.get:Float)/keyValue._2)*100).toString + "%"))
    println(mapPercentage)
  }
}
