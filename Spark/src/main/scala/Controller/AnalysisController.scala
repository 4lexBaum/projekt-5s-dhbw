package Controller

import Analysis.Customer._
import Analysis.Drilling._
import Analysis.General.{CustomerSingleProductAmount, OverallOrderAmount, OverallQuality}
import Analysis.MachineLearning.SpektralData
import Analysis.Material.{MaterialDuration, MaterialProducedAmount, MaterialQuality, MaterialQualityPercentage}
import Analysis.Milling._
import JsonHandling.ManufacturingData
import org.apache.spark.rdd.RDD

/**
  * Created by fabian on 06.11.16.
  */
class AnalysisController{

  val kafkaController: KafkaController = new KafkaController()
  val mongoController: MongoController = new MongoController()

  def runAllAnalysis(rdd: RDD[ManufacturingData]): Unit ={

    new CustomerQuality().runAnalysis(rdd, kafkaController, mongoController)
    new MaterialQuality().runAnalysis(rdd, kafkaController, mongoController)
    new MaterialDuration().runAnalysis(rdd, kafkaController, mongoController)
    new MaterialMillingSpeed().runAnalysis(rdd, kafkaController, mongoController)
    new MaterialMillingHeat().runAnalysis(rdd, kafkaController, mongoController)
    new MaterialDrillingSpeed().runAnalysis(rdd, kafkaController, mongoController)
    new MaterialDrillingHeat().runAnalysis(rdd, kafkaController, mongoController)
    new CustomerOrderAmount().runAnalysis(rdd, kafkaController, mongoController)
    new MaterialProducedAmount().runAnalysis(rdd, kafkaController, mongoController)
    new MaterialMaximumDrillingSpeed().runAnalysis(rdd, kafkaController, mongoController) // Not send to Kafka & UI
    new MaterialMinimumDrillingSpeed().runAnalysis(rdd, kafkaController, mongoController) // Not send to Kafka & UI
    new MaterialMaximumDrillingHeat().runAnalysis(rdd, kafkaController, mongoController) // Not send to Kafka & UI
    new MaterialMinimumDrillingHeat().runAnalysis(rdd, kafkaController, mongoController) // Not send to Kafka & UI
    new MaterialMaximumMillingSpeed().runAnalysis(rdd, kafkaController, mongoController) // Not send to Kafka & UI
    new MaterialMinimumMillingSpeed().runAnalysis(rdd, kafkaController, mongoController) // Not send to Kafka & UI
    new MaterialMaximumMillingHeat().runAnalysis(rdd, kafkaController, mongoController) // Not send to Kafka & UI
    new MaterialMinimumMillingHeat().runAnalysis(rdd, kafkaController, mongoController) // Not send to Kafka & UI
    new MaterialQualityPercentage().runAnalysis(rdd, kafkaController,mongoController)
    new CustomerQualityPercentage().runAnalysis(rdd, kafkaController,mongoController)
    new CustomerSingleProductAmount().runAnalysis(rdd, kafkaController,mongoController) // Not send to Kafka & UI
    new OverallOrderAmount().runAnalysis(rdd, kafkaController, mongoController)
    new OverallQuality().runAnalysis(rdd, kafkaController, mongoController)
//    new SpektralData().runAnalysis(rdd, kafkaController, mongoController) // playing around with correlation and clustering




  }

}
