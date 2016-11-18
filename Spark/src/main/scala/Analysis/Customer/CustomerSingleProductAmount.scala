package Analysis.Customer

import Analysis.AnalysisParent
import Controller.{KafkaController, MongoController}
import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import org.apache.spark.rdd.RDD

/**
  * Created by fabian on 18.11.16.
  */
class CustomerSingleProductAmount extends AnalysisParent{

  override val kafkaTopicSend: String = "CustomerSingleProductAmount"//this.getClass.getSimpleName.replace("$", "")

  override def runAnalysis(rdd: RDD[ManufacturingData], kafkaController: KafkaController, mongoController: MongoController): Unit = {

    //    this took way too long to figure it out
    val map = rdd.map(manuData => mapping(manuData))
      .groupByKey()
      .mapValues(x => countEqualElements(x))
      .collect()
      .map(elem => elem._1 -> elem._2)
      .toMap

    val json = JsonParser.mapToJsonSet(map)

    //    mongoController.writeAnalysisToMongo(json, kafkaTopicSend)
    //    kafkaController.sendStringViaKafka(json, kafkaTopicSend)
  }

  override def mapping(manufacturingData: ManufacturingData): (String, (String, Double)) ={
    (manufacturingData.customerNumber,(manufacturingData.materialNumber,1))
  }

  def countEqualElements(iterable: Iterable[(String,Double)]): Set[(String,Int)] = {
    val a = iterable.groupBy(x => x._1).mapValues(_.size)
    a.toSet
  }

  override def checkElement(element: MachineData): Double ={
    -1.0
  }
}
