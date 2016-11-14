package Analysis

import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import KafkaConnectivity.KafkaController
import org.apache.spark.rdd.RDD

import scala.collection.mutable

/**
  * Created by fabian on 12.11.16.
  */
object MaterialDuration extends AnalysisParent{

  override val kafkaTopicSend: String = "MaterialDuration" //this.getClass.getSimpleName
  private val map: mutable.Map[String, Double] = mutable.Map[String, Double]()

  override def runAnalysis(rdd: RDD[ManufacturingData]): Unit = {

    val map = rdd.map(manuData => mapping(manuData))
      .groupByKey()
      .sortByKey()
      .map(x => (x._1, average(x._2)))
      .collect().map(elem => elem._1 -> elem._2)
      .toMap

//    print(kafkaTopicsSend + " " + JsonParser.mapToJsonDouble(map))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonDouble(map), kafkaTopicSend)
    map.empty
  }

  override def mapping(manufacturingData: ManufacturingData): (String, Double) ={

    val productionTime = manufacturingData.machineData.last.timestamp.toDouble -
      manufacturingData.machineData.head.timestamp.toDouble

    (manufacturingData.materialNumber, productionTime)
  }

//  def updateMap(manuData: ManufacturingData): Unit ={
//    val key = manuData.materialNumber
//    val productionTime = manuData.machineData.last.timestamp.toDouble - manuData.machineData.head.timestamp.toDouble
//    val value = map.get(key)
//
//    if(value.isEmpty){
//      map += (key -> productionTime)
//    }else{
//      map.update(key, {(value.get + productionTime).toFloat/2})
//    }
//  }

}
