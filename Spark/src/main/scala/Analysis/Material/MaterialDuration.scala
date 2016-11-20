package Analysis.Material

import Analysis.AnalysisParent
import Controller.{KafkaController, MongoController}
import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import org.apache.spark.rdd.RDD

/**
  * Created by fabian on 12.11.16.
  */

class MaterialDuration extends AnalysisParent{

  override val kafkaTopicSend: String = "MaterialDuration" //this.getClass.getSimpleName

  override def runAnalysis(rdd: RDD[ManufacturingData], kafkaController: KafkaController, mongoController: MongoController): Unit = {

    val map = rdd.map(manuData => mapping(manuData))
      .groupByKey()
      .sortByKey()
      .map(x => (x._1, average(x._2)))
      .collect()
      .map(elem => elem._1 -> elem._2)
      .toMap

    val json = JsonParser.mapToJsonDouble(map)

    mongoController.writeAnalysisToMongo(json, kafkaTopicSend)
    kafkaController.sendStringViaKafka(json, kafkaTopicSend)
  }

  override def mapping(manufacturingData: ManufacturingData): (String, Double) ={

    val productionTime = (manufacturingData.machineData.last.timestamp.toDouble -
      manufacturingData.machineData.head.timestamp.toDouble)/10000

    (manufacturingData.materialNumber, productionTime)
  }

  override def checkElement(element: MachineData): Double = {
    -1.0
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
