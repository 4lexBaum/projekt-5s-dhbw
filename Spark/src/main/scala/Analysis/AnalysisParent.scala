package Analysis

import JsonHandling.{MachineData, ManufacturingData}
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

  def average[T]( ts: Iterable[T] )( implicit num: Numeric[T] ) = {
    num.toDouble( ts.sum ) / ts.size
  }

  def sum[T]( ts: Iterable[T] )( implicit num: Numeric[T] ) = {
    num.toDouble( ts.sum )
  }

  def mapping(manufacturingData: ManufacturingData): (String, Double)={
    ("",-1)
  }

  def checkElement(element: MachineData): Double ={
    -1.0
  }

}
