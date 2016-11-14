package Analysis

import JsonHandling.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController
import org.apache.spark.rdd.RDD

import scala.collection.mutable

/**
  * Created by fabian on 12.11.16.
  */
object CustomerOrderAmount extends AnalysisParent{

  override val kafkaTopicSend: String = "CustomerOrderAmount"//this.getClass.getSimpleName.replace("$", "")
  private val map: mutable.Map[String, Int] = mutable.Map[String,Int]()

  def runAnalysisWithReturn(rdd: RDD[ManufacturingData]): mutable.Map[String, Int] = {

    rdd.foreach(manuData => updateMap(manuData))
//    print(kafkaTopicsSend + " " + JsonParser.mapToJsonInt(map))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonInt(map), kafkaTopicSend)
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
      map.update(key, map(key) + 1)
    }
  }
}
