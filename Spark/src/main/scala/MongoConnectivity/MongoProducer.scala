package MongoConnectivity

import com.mongodb.DBObject
import com.mongodb.casbah.{MongoClient, MongoClientURI}
import com.mongodb.util.JSON

/**
  * Created by fabian on 14.11.16.
  */
class MongoProducer {

  private val buffer = new java.util.ArrayList[DBObject]()

  def getMongoProducer: MongoClient ={
    MongoClient(MongoClientURI("mongodb://mongodb:27017"))
  }

  def writeJsonToMongo(mongoClient: MongoClient, json: String, collectionName: String): Unit = {

    val dbObject: DBObject = JSON.parse(json).asInstanceOf[DBObject]

    buffer.add(dbObject)
    mongoClient.getDB("oip_taktstrasse").getCollection(collectionName).insert(buffer)
    buffer.clear()
  }

  def closeMongoProducer(mongoClient: MongoClient): Unit ={
    mongoClient.close()
  }

}
