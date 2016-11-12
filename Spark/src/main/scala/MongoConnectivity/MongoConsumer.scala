package MongoConnectivity

import com.mongodb.hadoop.MongoInputFormat
import org.apache.hadoop.conf.Configuration
import org.apache.spark.SparkContext
import org.bson.BSONObject

/**
  * Created by fabian on 12.11.16.
  */
object MongoConsumer {

  val sc = new SparkContext("local", "Hello from scala")

  val config = new Configuration()
  config.set("mongo.input.uri", "mongodb://mongodb:27017/oip_taktstrasse.manufacturingData")
  val mongoRDD = sc.newAPIHadoopRDD(config, classOf[MongoInputFormat], classOf[Object], classOf[BSONObject])

}
