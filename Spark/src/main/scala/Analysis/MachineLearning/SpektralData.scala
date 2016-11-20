package Analysis.MachineLearning

import Analysis.AnalysisParent
import Controller.{KafkaController, MongoController}
import JsonHandling.{MachineData, ManufacturingData}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.linalg._
import org.apache.spark.mllib.stat.Statistics

/**
  * Created by fabian on 20.11.16.
  */

class SpektralData extends AnalysisParent{

  override val kafkaTopicSend: String = "SpektralData"


//  clustering the spektralData
//
//  override def runAnalysis(rdd: RDD[ManufacturingData], kafkaController: KafkaController, mongoController: MongoController): Unit = {
//
//    val parsedData = rdd.map(manuData => Vectors.dense(
//      manuData.analysisData.a1, manuData.analysisData.a2, manuData.analysisData.b1, manuData.analysisData.b2, manuData.analysisData.em1, manuData.analysisData.em2
//    )).cache()
//
//    // Cluster the data into three classes
//    val numClusters = 3
//    val numIterations = 20
//    val clusters = KMeans.train(parsedData, numClusters, numIterations)
//
//    clusters.clusterCenters.foreach(println)
//
//    // Evaluate clustering
//    val errors = clusters.computeCost(parsedData)
//    println("Within Set Sum of Squared Errors = " + errors)
//  }

    override def runAnalysis(rdd: RDD[ManufacturingData], kafkaController: KafkaController, mongoController: MongoController): Unit = {

      val parsedData = rdd.map(manuData => Vectors.dense(
        manuData.analysisData.a1, manuData.analysisData.a2, manuData.analysisData.b1, manuData.analysisData.b2, manuData.analysisData.em1, manuData.analysisData.em2
      )).cache()

      val correlMatrix: Matrix = Statistics.corr(parsedData)

      correlMatrix
        .transpose
        .toArray
        .grouped(correlMatrix.numCols)
        .toList
        .map(line => line.mkString(" "))
        .foreach(println)

//      println(correlMatrix.toString)

    }

  override def mapping(manufacturingData: ManufacturingData): Any = ???

  override def checkElement(element: MachineData): Double = ???
}
