package Controller

import JsonHandling.ManufacturingData
import MongoConnectivity.{MongoConsumer, MongoProducer}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * Created by fabian on 18.11.16.
  */
class MongoController {

  private val mongoConsumer: MongoConsumer = new MongoConsumer()
  private val mongoProducer: MongoProducer = new MongoProducer()
  private val mongoDatabaseName:String = "oip_taktstrasse"
  private val mongoCollectionName: String = "manufacturingData"

  def getManufacturingDataFromMongo(sc: SparkContext): RDD[ManufacturingData] ={
    mongoConsumer.getMongoData(mongoConsumer.getMongoCollection(mongoDatabaseName, mongoCollectionName),sc)
  }

  def runAnalysisWithMongoData(analysisController:AnalysisController, sc: SparkContext): Unit ={
    val coll = mongoConsumer.getMongoCollection(mongoDatabaseName, mongoCollectionName)
    val rdd = mongoConsumer.getMongoData(coll,sc)
    analysisController.runAllAnalysis(rdd)
  }

  def writeAnalysisToMongo(json: String, kafkaTopic: String): Unit ={
    val mongoClient = mongoProducer.getMongoProducer
    mongoProducer.writeJsonToMongo(mongoClient, json, kafkaTopic)
    mongoProducer.closeMongoProducer(mongoClient)
  }


}
