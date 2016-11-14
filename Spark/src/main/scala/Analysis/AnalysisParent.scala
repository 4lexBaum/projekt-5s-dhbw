package Analysis

import JsonHandling.ManufacturingData
import org.apache.spark.rdd.RDD

import scala.collection.mutable.ListBuffer

/**
  * Created by fabian on 06.11.16.
  */

trait AnalysisParent {

  val kafkaTopicSend:String

  def runAnalysis(rdd: RDD[ManufacturingData]): Unit = {//list: List[ManufacturingData]
    ()
  }

}
