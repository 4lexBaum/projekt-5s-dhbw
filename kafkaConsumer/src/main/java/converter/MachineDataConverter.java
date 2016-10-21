package converter;

import model.dataModels.MachineData;

import com.google.gson.Gson;

/**
 * Class MachineDataConverter.
 * @author Daniel
 *
 */
public class MachineDataConverter implements Converter {
	Gson gson;
	
	/**
	 * Constructor.
	 */
	public MachineDataConverter() {
		gson = new Gson();
	}
	
	/**
	 * Converts machine data.
	 * @param rawData
	 * @return
	 */
	@Override
	public MachineData convert(String rawData) {
		return gson.fromJson(rawData, MachineData.class);
	}
}