package converter;

import model.dataModels.MachineData;

import com.google.gson.Gson;

/**
 * Class JSONConverter.
 * A JSONConverter object converts JSON data
 * into Java objects.
 * @author Daniel
 *
 */
public class MachineDataConverter implements Converter {
	Gson gson = new Gson();
	
	/**
	 * Converts JSON to Java object.
	 * @return
	 */
	public MachineData convert(String msg) {
		return gson.fromJson(msg, MachineData.class);
	}
}
