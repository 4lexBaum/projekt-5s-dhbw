package Analysis

import JsonParser.ManufacturingData
import KafkaConnectivity.KafkaController

import scala.collection.mutable.ListBuffer

/**
  * Created by fabian on 06.11.16.
  */
object AnalysisController{

  def runAllAnalysis(list: List[ManufacturingData]): Unit ={
    QualityCustomer.runAnalysis(list)
    MaterialDuration.runAnalysis(list)
    MaterialMillingSpeed.runAnalysis(list)
    QualityMaterial.runAnalysis(list)

  }

}
