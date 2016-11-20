package Analysis

import Controller.{KafkaController, MongoController}
import JsonHandling.{MachineData, ManufacturingData}
import org.apache.spark.rdd.RDD

/**
  * Created by fabian on 06.11.16.
  */

trait AnalysisParent extends Serializable{

  val kafkaTopicSend:String


  def runAnalysis(rdd: RDD[ManufacturingData], kafkaController: KafkaController, mongoController: MongoController): Unit

  def mapping(manufacturingData: ManufacturingData): Any

  def checkElement(element: MachineData): Double


  def average[T]( ts: Iterable[T] )(implicit num: Numeric[T]) = {
    num.toDouble(ts.sum) / ts.size
  }

  def sum[T]( ts: Iterable[T] )(implicit num: Numeric[T]) = {
    num.toDouble(ts.sum)
  }

  def max[T](ts: Iterable[T] )(implicit num: Numeric[T]) = {
    num.toDouble(ts.max)
  }

  def min[T](ts: Iterable[T] )(implicit num: Numeric[T]) = {
    num.toDouble(ts.min)
  }

  def %(x: Double, y: Double): Double ={
    BigDecimal((y/x)*100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

}
