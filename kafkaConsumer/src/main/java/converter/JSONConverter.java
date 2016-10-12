package converter;

import model.dataModels.MachineData;
import model.dataModels.SpectralAnalysisData;

import com.google.gson.Gson;

/**
 * Class JSONConverter.
 * A JSONConverter object converts JSON data
 * into Java objects.
 * @author Daniel
 *
 */
public class JSONConverter implements Converter {
	Gson gson = new Gson();
	
	/**
	 * Converts JSON to Java object.
	 * @return
	 */
	public MachineData convert(String msg) {
		return gson.fromJson(msg, MachineData.class);
	}
	
	/**
	 * Converts JSON into SpectralAnalysisData.
	 * @param msg
	 * @return
	 */
	public SpectralAnalysisData convertSpecData(String msg) {
		return gson.fromJson(msg, SpectralAnalysisData.class);
	}
}
