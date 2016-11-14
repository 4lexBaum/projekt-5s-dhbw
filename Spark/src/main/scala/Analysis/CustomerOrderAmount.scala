package Analysis

import JsonHandling.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.StreamingContext

import scala.collection._

import scala.collection.mutable

/**
  * Created by fabian on 12.11.16.
  */
object CustomerOrderAmount extends AnalysisParent{

  override val kafkaTopicSend: String = "CustomerOrderAmount"//this.getClass.getSimpleName.replace("$", "")
  //private val map: mutable.Map[String, Int] = mutable.Map[String,Int]()

  override def runAnalysisWithReturn(rdd: RDD[ManufacturingData]): Map[String, Long] = {

    val map = rdd.map(manuData => mapping(manuData))
      .countByKey()
    //print(kafkaTopicSend + " " + JsonParser.mapToJsonLong(map))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonLong(map), kafkaTopicSend)
    val tempMap = map
    map.empty
    tempMap
  }

//  def updateMap(manuData: Man ufacturingData): Unit = {
//    val key = manuData.customerNumber
//    val value = map.get(key)
//
//    if(value.isEmpty){
//      map += (key -> 1)
//    }else {
//      map.update(key, map(key) + 1)
//    }
//  }

  def mapping(manufacturingData: ManufacturingData): (String, Int) ={
    val key = manufacturingData.customerNumber
    (key, 1)
  }

}
