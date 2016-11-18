package Analysis.Customer

import Analysis.AnalysisParent
import Controller.{KafkaController, MongoController}
import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import org.apache.spark.rdd.RDD

/**
  * Created by fabian on 12.11.16.
  */

class CustomerOrderAmount extends AnalysisParent{

  override val kafkaTopicSend: String = "CustomerOrderAmount"//this.getClass.getSimpleName.replace("$", "")

  override def runAnalysis(rdd: RDD[ManufacturingData], kafkaController: KafkaController, mongoController: MongoController): Unit = {

    val map = rdd.map(manuData => mapping(manuData))
      .countByKey()

    val json = JsonParser.mapToJsonLong(map)

    mongoController.writeAnalysisToMongo(json, kafkaTopicSend)
    kafkaController.sendStringViaKafka(json, kafkaTopicSend)
  }

  override def mapping(manufacturingData: ManufacturingData): (String, Double) ={
    (manufacturingData.customerNumber, 1.0)
  }

  override def checkElement(element: MachineData): Double = {
    -1.0
  }


  //  def updateMap(manuData: ManufacturingData): Unit = {
//    val key = manuData.customerNumber
//    val value = map.get(key)
//
//    if(value.isEmpty){
//      map += (key -> 1)
//    }else {
//      map.update(key, map(key) + 1)
//    }
//  }
}
