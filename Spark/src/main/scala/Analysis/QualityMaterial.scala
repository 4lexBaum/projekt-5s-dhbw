package Analysis

import JsonHandling.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController
import org.apache.spark.rdd.RDD

import scala.collection.mutable
import scala.collection.mutable.Map

/**
  * Created by fabian on 12.11.16.
  */
object QualityMaterial extends AnalysisParent{

  override val kafkaTopicSend: String = "QualityMaterial" //this.getClass.getSimpleName.replace("$", "")
  val kafkaTopicsSendPercentage: String = "QualityMaterialPercentage"//this.getClass.getSimpleName.replace("$", "")
  private val map: mutable.Map[String, Int] = mutable.Map[String,Int]()
  private val mapPercentage: mutable.Map[String, String] = mutable.Map[String,String]()


  override def runAnalysis(rdd: RDD[ManufacturingData]): Unit = {

    rdd.foreach(manuData => updateMap(manuData))

    val total :mutable.Map[String,Int] = MaterialProducedAmount.runAnalysisWithReturn(rdd)
    total.foreach(element => calculatePercentage(element))

//    print(kafkaTopicsSend + " " + JsonParser.mapToJsonInt(map))
//    print(kafkaTopicsSendPercentage + " " + JsonParser.mapToJsonString(mapPercentage))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonInt(map), kafkaTopicSend)
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonString(mapPercentage), kafkaTopicsSendPercentage)
    map.empty
    mapPercentage.empty
  }

  def updateMap(manuData: ManufacturingData): Unit ={
    val key = manuData.materialNumber
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
      mapPercentage += (key -> {100 + "%"})
      return
    }
    mapPercentage += (key -> ((((value.get:Float)/keyValue._2)*100).toString + "%"))
  }

}
