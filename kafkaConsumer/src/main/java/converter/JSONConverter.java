package converter;

import model.dataModels.ManufacturingData;
import model.stateMachine.ProductionStateMachine;

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
	
	Vector<ManufacturingData> productionDataList = new Vector<>();
	ProductionStateMachine psm = new ProductionStateMachine();
	
	/**
	 * Converts JSON to Java object.
	 */
	public void convert(String msg) {
		ManufacturingData data = gson.fromJson(msg, ManufacturingData.class);
        productionDataList.add(data);
        System.out.println("Received data: " + data);
        psm.trigger(data);
	}

}
