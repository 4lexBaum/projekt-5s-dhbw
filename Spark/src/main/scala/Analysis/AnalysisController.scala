package Analysis

import JsonHandling.ManufacturingData
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.StreamingContext

/**
  * Created by fabian on 06.11.16.
  */
class AnalysisController{

  def runAllAnalysis(rdd: RDD[ManufacturingData]): Unit ={//list: List[ManufacturingData]
//    val rdd = inputRDD.cache()

    CustomerOrderAmount.runAnalysisWithReturn(rdd)
    QualityCustomer.runAnalysis(rdd)
    QualityMaterial.runAnalysis(rdd)
    MaterialDuration.runAnalysis(rdd)
    MaterialMillingSpeed.runAnalysis(rdd)
//    MaterialMillingHeat.runAnalysis(rdd)
    MaterialDrillingSpeed.runAnalysis(rdd)
//    MaterialDrillingHeat.runAnalysis(rdd)
  }

}
