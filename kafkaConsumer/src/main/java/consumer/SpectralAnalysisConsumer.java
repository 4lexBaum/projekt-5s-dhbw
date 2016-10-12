package consumer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.Main;
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
	
	private static SpectralAnalysisConsumer consumer;
	
	/**
	 * Constructor SpectralAnalysisConsumer.
	 * Singleton-Pattern! => private constructor.
	 * @param path
	 */
	private SpectralAnalysisConsumer(String path) {
		this.path = path;
	}
	
	/**
	 * getConsumer method.
	 * Is used to obtain an instance of the SpectralAnalysisConsumer.
	 * @param path
	 * @return
	 */
	public static SpectralAnalysisConsumer getConsumer(String path) {
		if(consumer == null) {
			consumer = new SpectralAnalysisConsumer(path);
		}
		return consumer;
	}
	
	/**
	 * Save latest spectralanalysis file
	 * in ManufacturingData object.
	 */
	public void saveSpectralanalysis() {
		InputStream in;
		File spectralAnalysis = getLatestFile();
		
		try {
			in = new FileInputStream(spectralAnalysis);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        StringBuilder out = new StringBuilder();
	        String line;
	        
	        while ((line = reader.readLine()) != null) {
	            out.append(line);
	        }
	        
	        SpectralAnalysisData data = null;
	        Main.previousData.setAnalysisData(data);
	        reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the latest file for a specified directory
	 * Returns the latest spectralanalysis file.
	 */	
	public File getLatestFile() {
	    File dir = new File(path);
	    File[] files = dir.listFiles(new FileFilter() {          
	        public boolean accept(File file) {
	            return file.isFile();
	        }
	    });
	    
	    long lastMod = Long.MIN_VALUE;
	    File choice = null;
	    
	    for (File file : files) {
	        if (file.lastModified() > lastMod) {
	            choice = file;
	            lastMod = file.lastModified();
	        }
	    }
	    return choice;
	}
}