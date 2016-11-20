package Analysis.Milling

import Analysis.AnalysisParent
import Controller.{KafkaController, MongoController}
import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import org.apache.spark.rdd.RDD

/**
  * Created by fabian on 18.11.16.
  */
class MaterialMaximumMillingHeat extends AnalysisParent {

  override val kafkaTopicSend: String = "MaterialMaximumMillingHeat"// this.getClass.getSimpleName

  override def runAnalysis(rdd: RDD[ManufacturingData], kafkaController: KafkaController, mongoController: MongoController): Unit = {

    val map = rdd.map(manuData => mapping(manuData))
      .groupByKey()
      .sortByKey()
      .map(x => (x._1, max(x._2)))
      .collect()
      .map(elem => elem._1 -> elem._2)
      .toMap

    val json = JsonParser.mapToJsonDouble(map)

    mongoController.writeAnalysisToMongo(json, kafkaTopicSend)
    kafkaController.sendStringViaKafka(json, kafkaTopicSend)
  }

  override def mapping(manufacturingData: ManufacturingData): (String, Double) ={

    val machineData = manufacturingData.machineData
    val speedList = for(elem <- machineData) yield checkElement(elem)
    val filteredList = speedList.filter(_ != -1)
    val max = filteredList.max

    (manufacturingData.materialNumber, max)
  }

  override def checkElement(element: MachineData): Double ={
    if(element.itemName.equals("MILLING_HEAT")){
      return element.value.toDouble
    }
    -1
  }

}
