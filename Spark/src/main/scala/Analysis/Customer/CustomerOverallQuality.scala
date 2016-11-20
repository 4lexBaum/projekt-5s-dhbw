package Analysis.Customer

import Analysis.AnalysisParent
import Controller.{KafkaController, MongoController}
import JsonHandling.{JsonParser, MachineData, ManufacturingData}
import org.apache.spark.rdd.RDD

/**
  * Created by Philip on 20.11.16.
  */
class CustomerOverallQuality extends AnalysisParent{

  override val kafkaTopicSend: String = "CustomerOverallQuality"//this.getClass.getSimpleName.replace("$", "")

  override def runAnalysis(rdd: RDD[ManufacturingData], kafkaController: KafkaController, mongoController: MongoController): Unit = {

    val total = rdd.map(manuData => ("key",1.0))
      .reduceByKey(_ + _)

    val bad = rdd.map(manuData => mapping(manuData))
      .reduceByKey(_ + _)

    val map = total
      .join(bad)
      .map(x => (x._1, %(x._2._1,x._2._2)))
      .collect()
      .map(elem => elem._1 -> elem._2)
      .toMap

    val json = JsonParser.mapToJsonDouble(map)

    mongoController.writeAnalysisToMongo(json, kafkaTopicSend)
    kafkaController.sendStringViaKafka(json, kafkaTopicSend)
  }

  private def %(x: Double, y: Double): Double ={
    BigDecimal((y/x)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  override def mapping(manufacturingData: ManufacturingData): (String, Double) ={

    if (manufacturingData.analysisData.overallStatus.equals("OK")) {
      ("key", 1)
    }else{
      ("key", 0)

    }
  }

  override def checkElement(element: MachineData): Double = {
    -1.0
  }

}
