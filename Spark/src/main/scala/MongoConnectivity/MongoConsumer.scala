//package MongoConnectivity
//
//import JsonParser.ManufacturingData
//import com.mongodb.casbah.MongoClient
//import com.mongodb.casbah.commons.MongoDBObject
//
//import scala.collection.mutable.ListBuffer
//
//import JsonParser.JsonParser
//
///**
//  * Created by fabian on 12.11.16.
//  */
//object MongoConsumer {
//
//  def main(args: Array[String]): Unit = {
//    getMongoData.foreach(elem => println(elem))
//  }
//
//  val mongoClient = MongoClient("localhost", 28000)
//  val db = mongoClient("oip_taktstrasse")
//  val coll = db("manufacturingData")
//  val query  = MongoDBObject.empty
//  val fields = MongoDBObject("_id" -> 0)
//  val manufacturingDataObjects: ListBuffer[ManufacturingData] = ListBuffer[ManufacturingData]()
//
//  def getMongoData: List[ManufacturingData] ={
//    val allDocs = coll.find(query,fields)
//    for(doc <- allDocs) yield append(doc.toString)
//    manufacturingDataObjects.toList
//  }
//
//  def append(mongoElem: String): Unit ={
//    manufacturingDataObjects +=  JsonParser.jsonToManufacturingData(mongoElem)
//  }
//
//}
