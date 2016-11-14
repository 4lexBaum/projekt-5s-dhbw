package Analysis

import JsonHandling.ManufacturingData
import KafkaConnectivity.KafkaController
import JsonHandling.{JsonParser, ManufacturingData}
import org.apache.spark.rdd.RDD

import scala.collection.mutable.Map

/**
  * Created by fabian on 12.11.16.
  */
object MaterialDuration extends AnalysisParent{

  override val kafkaTopicSend: String = this.getClass.getSimpleName
  private val map: Map[String, Double] = Map[String, Double]().withDefaultValue(0.0)

  override def runAnalysis(rdd: RDD[ManufacturingData]): Unit = {

    rdd.foreach(manuData => updateMap(manuData))
    print(kafkaTopicSend + " " + JsonParser.mapToJsonDouble(map))
    //KafkaController.sendStringViaKafka(JsonParser.mapToJsonDouble(map), kafkaTopicsSend)
  }

  def updateMap(manuData: ManufacturingData): Unit ={
    val key = manuData.materialNumber
    val productionTime = manuData.machineData.last.timestamp.toDouble - manuData.machineData.head.timestamp.toDouble

    if(map(key) == 0.0){
      map.update(key, productionTime)
    }else{
      map.update(key, (map(key) + productionTime)/2)
    }
  }

}
