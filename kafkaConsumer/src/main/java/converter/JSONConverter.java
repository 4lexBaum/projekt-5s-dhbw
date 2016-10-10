package converter;

import consumer.ProductionData;
import statemachine.ProductionStateMachine;

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
	ProductionStateMachine psm = new ProductionStateMachine();
	
	/**
	 * Converts JSON to Java object.
	 */
	public void convert(String msg) {
		ProductionData data = gson.fromJson(msg, ProductionData.class);
        productionDataList.add(data);
        System.out.println("Received data: " + data);
        psm.trigger(data);
	}

}
