package MongoConnectivity

import Analysis.AnalysisController
import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.commons.MongoDBObject

import scala.collection.mutable.ListBuffer
import JsonHandling.{JsonParser, ManufacturingData}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fabian on 12.11.16.
  */
class MongoConsumer {

//  val analysisController = new AnalysisController()

//  def main(args: Array[String]): Unit = {
//    val sc: SparkContext = new SparkContext(new SparkConf().setAppName("Test").setMaster("local[*]"))
//    val i = sc.parallelize(getMongoData)
//    analysisController.runAllAnalysis(i)
//  }

  val mongoClient = MongoClient("mongodb", 27017)
  val db = mongoClient("oip_taktstrasse")
  val coll = db("manufacturingData")
  val query  = MongoDBObject.empty
  val fields = MongoDBObject("_id" -> 0)
  val manufacturingDataObjects: ListBuffer[ManufacturingData] = ListBuffer[ManufacturingData]()

  def getMongoData: RDD[ManufacturingData] ={
    val allDocs = coll.find(query,fields)
    for(doc <- allDocs) append(doc.toString)
    val sc2: SparkContext = new SparkContext(new SparkConf().setAppName("DataInputStreams"))
    sc2.parallelize(manufacturingDataObjects.toList)
  }

  def append(mongoElem: String): Unit ={
    manufacturingDataObjects +=  JsonParser.jsonToManufacturingData(mongoElem)
  }

}
