package Analysis

import JsonParser.{JsonParser, MachineData, ManufacturingData}
import KafkaConnectivity.KafkaController

import scala.collection.mutable
import scala.collection.mutable.{ListBuffer, Map}

/**
  * Created by fabian on 12.11.16.
  */
object MaterialMillingSpeed extends AnalysisParent{

  override val kafkaTopicsSend: String = this.getClass.getSimpleName
  private val map: Map[String, Double] = Map()

  override def runAnalysis(list: List[ManufacturingData]): Unit = {

    list.foreach(manuData => updateMap(manuData))
    KafkaController.sendStringViaKafka(JsonParser.mapToJsonDouble(map), kafkaTopicsSend)
  }

  def updateMap(manuData: ManufacturingData): Unit ={
    val key = manuData.materialNumber
    val machineData = manuData.machineData

    val speedList = for(elem <- machineData) yield checkElement(elem)
    val avg = speedList.sum/speedList.size.toDouble

    //not sure if this is right
    map + (key -> (map.getOrElseUpdate(key, avg) + avg)/2)
  }

  def checkElement(element: MachineData): Double ={
    if(element.itemName.equals("MILLING_SPEED")){
      return element.value.toDouble
    }
    0.0
  }

}
