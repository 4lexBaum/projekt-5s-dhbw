package Analysis.Drilling

import Analysis.AnalysisParent
import Controller.{KafkaController, MongoController}
import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import org.apache.spark.rdd.RDD

/**
  * Created by fabian on 18.11.16.
  */
class MaterialMinimumDrillingSpeed extends AnalysisParent {

  override val kafkaTopicSend: String = "MaterialMinimumDrillingSpeed"// this.getClass.getSimpleName

  override def runAnalysis(rdd: RDD[ManufacturingData], kafkaController: KafkaController, mongoController: MongoController): Unit = {

    val map = rdd.map(manuData => mapping(manuData))
      .groupByKey()
      .sortByKey()
      .map(x => (x._1, min(x._2)))
      .collect()
      .map(elem => elem._1 -> elem._2)
      .toMap

    val json = JsonParser.mapToJsonDouble(map)

    mongoController.writeAnalysisToMongo(json, kafkaTopicSend)
//    kafkaController.sendStringViaKafka(json, kafkaTopicSend)
  }

  override def mapping(manufacturingData: ManufacturingData): (String, Double) ={

    val machineData = manufacturingData.machineData
    val speedList = for(elem <- machineData) yield checkElement(elem)
    var filteredList = speedList.filter(_ != -1)
    filteredList = filteredList.filter(_ != 0)
    val min = filteredList.min
   
    (manufacturingData.materialNumber, min)
  }

  override def checkElement(element: MachineData): Double ={
    if(element.itemName.equals("DRILLING_SPEED")){
      return element.value.toDouble
    }
    -1
  }

}
