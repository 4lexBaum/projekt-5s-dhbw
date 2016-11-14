package MongoConnectivity


import scala.collection.mutable.ListBuffer
import JsonHandling.{JsonParser, ManufacturingData}
import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.commons.MongoDBObject
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext

/**
  * Created by fabian on 12.11.16.
  */
class MongoConsumer {

  val mongoClient = MongoClient("mongodb", 27017)
  val db = mongoClient("oip_taktstrasse")
  val coll = db("manufacturingData")
  val query  = MongoDBObject.empty
  val fields = MongoDBObject("_id" -> 0)
  val manufacturingDataObjects: ListBuffer[ManufacturingData] = ListBuffer[ManufacturingData]()

  def getMongoData(sc: SparkContext): RDD[ManufacturingData] ={
    val allDocs = coll.find(query,fields)
    for(doc <- allDocs) append(doc.toString)
    sc.parallelize(manufacturingDataObjects.toList)
  }

  def append(mongoElem: String): Unit ={
    manufacturingDataObjects +=  JsonParser.jsonToManufacturingData(mongoElem)
  }

}
