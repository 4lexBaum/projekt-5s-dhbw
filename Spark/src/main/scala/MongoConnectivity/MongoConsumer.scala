package MongoConnectivity


import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
//import org.mongodb.scala._
import org.apache.hadoop.conf.Configuration;
import org.apache.spark.streaming.{ Seconds, StreamingContext }
import com.mongodb.casbah.Imports._


/**
  * Created by fabian on 12.11.16.

  */
object MongoConsumer {

 def main(args: Array[String]) {

     val mongoClient = MongoClient("localhost", 27017)
     val db = mongoClient("oip_taktstrasse")
     val coll = db("manufacturingData")
     val query  = MongoDBObject.empty
     val fields = MongoDBObject("_id" -> 0)
     val allDocs = coll.find(query,fields)
     for(doc <- allDocs) println( doc.toString )
                  
 }
}

