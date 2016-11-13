package Analysis

import JsonHandling.ManufacturingData

/**
  * Created by fabian on 06.11.16.
  */
class AnalysisController{

  def runAllAnalysis(list: List[ManufacturingData]): Unit ={
    QualityCustomer.runAnalysis(list)
    QualityMaterial.runAnalysis(list)
    MaterialDuration.runAnalysis(list)
    MaterialMillingSpeed.runAnalysis(list)
    MaterialMillingHeat.runAnalysis(list)
    MaterialDrillingSpeed.runAnalysis(list)
    MaterialDrillingHeat.runAnalysis(list)
  }

}
