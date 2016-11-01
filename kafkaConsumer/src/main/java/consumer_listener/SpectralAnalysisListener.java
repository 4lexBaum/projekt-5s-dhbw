package consumer_listener;

import model.dataModels.SpectralAnalysisData;

/**
 * Interface SpectralAnalysisConsumer.
 * @author Daniel
 *
 */
public interface SpectralAnalysisListener {
	public void onSpectralAnalysisData(SpectralAnalysisData data);
}