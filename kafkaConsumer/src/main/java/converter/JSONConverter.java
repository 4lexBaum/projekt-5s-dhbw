package converter;

import model.dataModels.ManufacturingData;

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
	 */
	public ManufacturingData convert(String msg) {
		return gson.fromJson(msg, ManufacturingData.class);
	}
}
