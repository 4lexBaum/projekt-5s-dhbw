package MongoConnectivity

import com.mongodb.DBObject
import com.mongodb.casbah.{MongoClient, MongoClientURI}
import com.mongodb.util.JSON

/**
  * Created by fabian on 14.11.16.
  */
class MongoProducer {


  def writeToMongo(json: String, collectionName: String): Unit = {
    val dbObject: DBObject = JSON.parse(json).asInstanceOf[DBObject]
    val mongo = MongoClient(MongoClientURI("mongodb://mongodb:27017"))
    val buffer = new java.util.ArrayList[DBObject]()
    //buffer.add(dbObject)
    mongo.getDB("iop_taktstrasse").createCollection(collectionName, dbObject) //.insert(buffer)
    //buffer.clear()
  }

}
