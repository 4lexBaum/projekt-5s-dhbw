package Analysis

import JsonHandling.ManufacturingData
import org.apache.spark.rdd.RDD

/**
  * Created by fabian on 06.11.16.
  */
class AnalysisController{

  def runAllAnalysis(rdd: RDD[ManufacturingData]): Unit ={
    QualityCustomer.runAnalysis(rdd)
    QualityMaterial.runAnalysis(rdd)
    MaterialDuration.runAnalysis(rdd)
    MaterialMillingSpeed.runAnalysis(rdd)
    MaterialMillingHeat.runAnalysis(rdd)
    MaterialDrillingSpeed.runAnalysis(rdd)
    MaterialDrillingHeat.runAnalysis(rdd)
    CustomerOrderAmount.runAnalysis(rdd)
    MaterialProducedAmount.runAnalysis(rdd)
  }

}
