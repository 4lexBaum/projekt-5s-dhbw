//package MongoConnectivity
//
//import org.apache.hadoop.conf.Configuration
//import org.apache.spark.SparkContext
//
//import scala.tools.nsc.doc.model.Object
//
///**
//  * Created by fabian on 12.11.16.
//  */
//object MongoConsumer {
//
//  val sc = new SparkContext("local", "Hello from scala")
//
//  val config = new Configuration()
//  config.set("mongo.input.uri", "mongodb://127.0.0.1:27017/dbName.collectionName")
//  val mongoRDD = sc.newAPIHadoopRDD(config, classOf[com.mongodb.hadoop.MongoInputFormat], classOf[Object], classOf[BSONObject])
//
//}
