package Analysis

import JsonHandling.{JsonParser, ManufacturingData}
import KafkaConnectivity.KafkaController
import org.apache.spark.rdd.RDD

import scala.collection._

/**
  * Created by fabian on 12.11.16.
  */
object MaterialProducedAmount extends AnalysisParent{

  override val kafkaTopicSend: String = "MaterialProducedAmount"//this.getClass.getSimpleName.replace("$", "")

  override def runAnalysis(rdd: RDD[ManufacturingData]): Unit = {

    val map = rdd.map(manuData => mapping(manuData))
      .countByKey()
//    print(kafkaTopicsSend + " " + JsonParser.mapToJsonInt(map))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonLong(map), kafkaTopicSend)
  }

//  def updateMap(manuData: ManufacturingData): Unit = {
//    val key = manuData.materialNumber
//    val value = map.get(key)
//
//    if(value.isEmpty){
//      map += (key -> 1)
//    }else {
//      map.update(key, map(key) + 1)
//    }
//  }


  override def mapping(manufacturingData: ManufacturingData): (String, Double) ={
    (manufacturingData.customerNumber, 1.0)
  }

}
