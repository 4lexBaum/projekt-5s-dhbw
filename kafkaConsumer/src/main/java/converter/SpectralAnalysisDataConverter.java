package converter;

import com.google.gson.Gson;

import model.dataModels.SpectralAnalysisData;

public class SpectralAnalysisDataConverter implements Converter {
	Gson gson = new Gson();
	
	/**
	 * Converts JSON into SpectralAnalysisData.
	 * @param msg
	 * @return
	 */
	@Override
	public SpectralAnalysisData convert(String msg) {
		return gson.fromJson(msg, SpectralAnalysisData.class);
	}

}
