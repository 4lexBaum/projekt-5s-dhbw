package converter;

import com.google.gson.Gson;

import model.dataModels.SpectralAnalysisData;

/**
 * Class SpectralAnalaysisDataConverter
 * @author Daniel
 *
 */
public class SpectralAnalysisDataConverter implements Converter {
	Gson gson;
	
	/**
	 * Constructor.
	 */
	public SpectralAnalysisDataConverter() {
		gson = new Gson();
	}
	
	/**
	 * Converts spectral analysis data.
	 * @param rawData
	 * @return
	 */
	@Override
	public SpectralAnalysisData convert(String rawData) {
		return gson.fromJson(rawData, SpectralAnalysisData.class);
	}
}