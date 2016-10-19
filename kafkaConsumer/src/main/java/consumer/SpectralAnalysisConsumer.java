package consumer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.Constants;

import converter.SpectralAnalysisDataConverter;
import db.DatabaseManager;
import model.dataModels.ManufacturingData;
import model.dataModels.SpectralAnalysisData;

/**
 * Class SpectralAnalysisConsumer.
 * The consumer reads the files containing the
 * spectral analysis results from the file system.
 * @author Daniel
 *
 */
public class SpectralAnalysisConsumer implements Consumer, Runnable {
	
	//singleton instance
	private static SpectralAnalysisConsumer consumer;
	private ManufacturingData manufacturingData;
	
	SpectralAnalysisDataConverter converter = new SpectralAnalysisDataConverter();
	
	@Override
	public void run() {
		try {
			Thread.sleep(20000);
			manufacturingData.setAnalysisData(
				(SpectralAnalysisData) converter.convert(readSpectralAnalysisFile())
			);
			DatabaseManager.getManager().insertManifacturingDocument(manufacturingData);	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor SpectralAnalysisConsumer.
	 * Singleton-Pattern! => private constructor.
	 */
	private SpectralAnalysisConsumer() {
		/*MachineDataConsumer.setOnMachineDataListener(data -> {
			if(data.getItemName().equals("L2") && data.getValue().equals("false")) {
				if(finishedFirstProduction) {
					
					
					//convert spectral analysis data and pass it to the data handler
					DataHandler.getDataHandler().addConsumerData(
						
					);
					
					System.out.println("stored data in mongodb");
				} else {
					finishedFirstProduction = true;
				}
			}
		});*/
	}
	
	public void setManufacturingData(ManufacturingData manufacturingData) {
		this.manufacturingData = manufacturingData;
	}
	
	/**
	 * getConsumer method.
	 * Is used to obtain an instance of the SpectralAnalysisConsumer.
	 * @param path
	 * @return
	 */
	public static SpectralAnalysisConsumer getConsumer() {
		if(consumer == null) {
			consumer = new SpectralAnalysisConsumer();
		}
		return consumer;
	}
	
	/**
	 * Save latest spectralanalysis file
	 * in ManufacturingData object.
	 */
	public String readSpectralAnalysisFile() {
		InputStream in;
		File spectralAnalysis = getLatestFile();
		StringBuilder out = null;
		
		try {
			in = new FileInputStream(spectralAnalysis);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        out = new StringBuilder();
	        String line;
	        
	        while ((line = reader.readLine()) != null) {
	            out.append(line);
	        }
	        
	        reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return out.toString();
	}
	
	/**
	 * Get the latest file for a specified directory
	 * Returns the latest spectralanalysis file.
	 */	
	public File getLatestFile() {
	    File dir = new File(Constants.PATH_SPECTRAL_ANALYSIS);
	    File[] files = dir.listFiles(file -> {
	    	return file.isFile();
	    });
	    
	    long lastMod = Long.MIN_VALUE;
	    File choice = null;
	    
	    for(File file : files) {
	        if(file.lastModified() > lastMod) {
	            choice = file;
	            lastMod = file.lastModified();
	        }
	    }
	    return choice;
	}
}