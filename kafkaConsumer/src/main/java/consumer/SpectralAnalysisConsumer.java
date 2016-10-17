package consumer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.Constants;

import converter.SpectralAnalysisDataConverter;

import model.dataModels.SpectralAnalysisData;

/**
 * Class SpectralAnalysisConsumer.
 * The consumer reads the files containing the
 * spectral analysis results from the file system.
 * @author Daniel
 *
 */
public class SpectralAnalysisConsumer implements Consumer {
	private String path;
	
	//singleton instance
	private static SpectralAnalysisConsumer consumer;
	private boolean finishedFirstProduction = false;
	
	/**
	 * Constructor SpectralAnalysisConsumer.
	 * Singleton-Pattern! => private constructor.
	 * @param path
	 */
	private SpectralAnalysisConsumer(String path) {
		this.path = path;
		MachineDataConsumer.setOnMachineDataListener(data -> {
			
			
			if(data.getItemName().equals("L2") && data.getValue().equals("false")) {
				if(finishedFirstProduction) {
					SpectralAnalysisDataConverter converter = new SpectralAnalysisDataConverter();
					DataHandler.getDataHandler().addConsumerData(
						(SpectralAnalysisData) converter.convert(readSpectralAnalysisFile())
					);
					System.out.println("new document of previous product was stored into mongoDB");
				} else {
					finishedFirstProduction = true;
				}
			}
		});
	}
	
	/**
	 * getConsumer method.
	 * Is used to obtain an instance of the SpectralAnalysisConsumer.
	 * @param path
	 * @return
	 */
	public static SpectralAnalysisConsumer getConsumer() {
		if(consumer == null) {
			consumer = new SpectralAnalysisConsumer(Constants.PATH_SPECTRAL_ANALYSIS);
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
	    File dir = new File(path);
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