package converter;

import model.dataModels.Data;

/**
 * Interface Converter.
 * @author Daniel
 *
 */
public interface Converter {
	public Data convert(String rawData);
}