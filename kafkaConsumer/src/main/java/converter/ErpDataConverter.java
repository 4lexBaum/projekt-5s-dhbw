package converter;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import model.dataModels.ErpData;

/**
 * Class ErpDataConverter.
 * @author Daniel
 *
 */
public class ErpDataConverter implements Converter {
	private Unmarshaller unmarshaller;
	
	/**
	 * Constructor.
	 */
	public ErpDataConverter() {
		try {
			JAXBContext context = JAXBContext.newInstance(ErpData.class);
			this.unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Converts erp data.
	 * @param rawData
	 * @return
	 */
	@Override
	public ErpData convert(String rawData) {
		ErpData data = null;
		
		try {
			StringReader reader = new StringReader(rawData);
			data = (ErpData) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return data;
	}
}