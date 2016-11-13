package Analysis

import JsonHandling.ManufacturingData

import scala.collection.mutable.ListBuffer

/**
  * Created by fabian on 06.11.16.
  */

trait AnalysisParent {

  val kafkaTopicsSend:String

  def runAnalysis(list: List[ManufacturingData]): Unit = {
    ()
  }

}
