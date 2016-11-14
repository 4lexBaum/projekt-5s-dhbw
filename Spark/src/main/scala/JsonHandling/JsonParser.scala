package JsonHandling

import com.fasterxml.jackson.databind.JsonMappingException
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.writePretty

import collection._

/**
  * Created by fabian on 05.11.16.
  */
object JsonParser {

  /**
    * Convert Json to ManufaturingData object for easier processing
    * @param inputData Json string
    * @return ManufacturingData Object
    */

  def jsonToManufacturingData(inputData: String): ManufacturingData = {
    implicit val formats = DefaultFormats
    parse(inputData).extract[ManufacturingData]
    //TODO: JsonMappingException tritt auf, wenn Daten umvollständig
  }

  /**
    * Convert ManufacturingData Object back to Json for easier use in JS
    * @param inputData  ManufacturingData Object
    * @return pretty Json String
    */

  def manufacturingDataToJson(inputData: ManufacturingData): String = {
    implicit val formats = DefaultFormats
     writePretty(inputData)
  }

  def mapToJsonInt(map: Map[String, Int]): String = {
    //val tempMap = mutable.ListMap(map.toSeq.sortBy(_._2):_*)
    implicit val formats = DefaultFormats
    writePretty(map)
  }

  def mapToJsonDouble(map: Map[String, Double]): String = {
    implicit val formats = DefaultFormats
    writePretty(map)
  }

  def mapToJsonString(map: Map[String, String]): String = {
    implicit val formats = DefaultFormats
    writePretty(map)
  }

  def mapToJsonLong(map: Map[java.lang.String, Long]): String = {
    implicit val formats = DefaultFormats
    writePretty(map)
  }

}

case class MachineData(value: String, status: String, itemName: String, timestamp: String)

case class AnalysisData(em1: Double, em2: Double, a1: Double, a2: Double, b1: Double, b2: Double, overallStatus: String, ts_start: Int, ts_stop: Int)

case class ManufacturingData(customerNumber: String, materialNumber: String, orderNumber: String, timeStamp: String, machineData: List[MachineData], analysisData: AnalysisData)
