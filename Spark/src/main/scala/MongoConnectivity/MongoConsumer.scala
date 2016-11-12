package MongoConnectivity

/**
  * Created by fabian on 12.11.16.
  */
object MongoConsumer {

  val sc = new SparkContext("local", "Hello from scala")

  val config = new Configuration()
  config.set("mongo.input.uri", "mongodb://127.0.0.1:27017/dbName.collectionName")
  val mongoRDD = sc.newAPIHadoopRDD(config, classOf[com.mongodb.hadoop.MongoInputFormat], classOf[Object], classOf[BSONObject])

}


package Analysis

import JsonParser.ManufacturingData
import KafkaConnectivity.KafkaController

import scala.collection.mutable.ListBuffer

/**
  * Created by fabian on 06.11.16.
  */
object AnalysisController{

  //val dataList = ListBuffer[ManufacturingData]()

  def runAllAnalysis(list: List[ManufacturingData]): Unit ={
    //dataList += obj
    QualityCustomer.runAnalysis(list)

  }

}
