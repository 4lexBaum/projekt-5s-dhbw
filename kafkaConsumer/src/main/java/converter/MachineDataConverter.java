package converter;

import model.dataModels.MachineData;

import com.google.gson.Gson;

/**
 * Class MachineDataConverter.
 * @author Daniel
 *
 */
public class MachineDataConverter implements Converter {
	Gson gson = new Gson();
	
	/**
	 * Converts json to java object.
	 * @return
	 */
	public MachineData convert(String msg) {
		return gson.fromJson(msg, MachineData.class);
	}
}
