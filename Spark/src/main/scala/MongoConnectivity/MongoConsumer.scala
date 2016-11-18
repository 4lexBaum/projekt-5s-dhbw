package MongoConnectivity

import scala.collection.mutable.ListBuffer
import JsonHandling.{JsonParser, ManufacturingData}
import com.mongodb.casbah.{MongoClient, MongoCollection}
import com.mongodb.casbah.commons.MongoDBObject
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext

/**
  * Created by fabian on 12.11.16.
  */
class MongoConsumer {

  private val query  = MongoDBObject.empty
  private val fields = MongoDBObject("_id" -> 0)
  private val manufacturingDataObjects: ListBuffer[ManufacturingData] = ListBuffer[ManufacturingData]()

  def getMongoCollection(databaseName: String, collectionName: String): MongoCollection ={
      val mongoClient = MongoClient("mongodb", 27017)
      val db = mongoClient(databaseName)
      db(collectionName)
  }

  def getMongoData(mongoCollection: MongoCollection, sc: SparkContext): RDD[ManufacturingData] ={

    val allDocs = mongoCollection.find(query,fields)
    for(doc <- allDocs) append(doc.toString)
    sc.parallelize(manufacturingDataObjects.toList)
  }

  def append(mongoElem: String): Unit ={
    manufacturingDataObjects +=  JsonParser.jsonToManufacturingData(mongoElem)
  }

  def closeMongoMongoConnection(mongoClient: MongoClient): Unit ={
    mongoClient.close()
  }

}
