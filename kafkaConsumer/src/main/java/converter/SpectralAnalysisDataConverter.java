package converter;

import com.google.gson.Gson;

import model.dataModels.SpectralAnalysisData;

/**
 * Class SpectralAnalaysisDataConverter
 * @author Daniel
 *
 */
public class SpectralAnalysisDataConverter implements Converter {
	Gson gson = new Gson();
	
	/**
	 * Converts spectral analysis data
	 * into json data.
	 * @param msg
	 * @return
	 */
	@Override
	public SpectralAnalysisData convert(String msg) {
		return gson.fromJson(msg, SpectralAnalysisData.class);
	}
}
