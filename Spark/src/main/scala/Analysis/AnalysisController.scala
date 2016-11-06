package Analysis

import JsonParser.ManufacturingData
import KafkaConnectivity.KafkaController

import scala.collection.mutable.ListBuffer

/**
  * Created by fabian on 06.11.16.
  */
object AnalysisController{

  val dataList = ListBuffer[ManufacturingData]()

  def runAllAnalysis(obj: ManufacturingData): Unit ={
    dataList += obj
    QualityCustomer.runAnalysis(dataList.toList)

  }

}
