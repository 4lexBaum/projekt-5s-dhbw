package converter;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import model.dataModels.Data;
import model.dataModels.ErpData;

/**
 * Class ErpDataConverter.
 * @author Daniel
 *
 */
public class ErpDataConverter implements Converter {
	
	/**
	 * Converts xml data.
	 */
	@Override
	public Data convert(String msg) {
		ErpData data = null;
		
		try {
			JAXBContext context = JAXBContext.newInstance(ErpData.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			StringReader reader = new StringReader(msg);
			data = (ErpData) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return data;
	}
}