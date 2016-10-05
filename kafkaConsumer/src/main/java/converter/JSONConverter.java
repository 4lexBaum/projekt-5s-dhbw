package converter;

import consumer.ProductionData;

import java.util.Vector;

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
	
	Vector<ProductionData> productionDataList = new Vector<>();
	
	/**
	 * Converts JSON to Java object.
	 */
	@Override
	public void convert(String msg) {
		ProductionData data = gson.fromJson(msg, ProductionData.class);
        productionDataList.add(data);
	}

}
