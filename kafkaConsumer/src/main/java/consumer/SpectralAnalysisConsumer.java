package consumer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import java.util.concurrent.CopyOnWriteArrayList;

import app.Constants;

import consumer_listener.SpectralAnalysisListener;

import converter.SpectralAnalysisDataConverter;

import model.dataModels.SpectralAnalysisData;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

/**
 * Class SpectralAnalysisConsumer.
 * This class is responsible for detecting and
 * reading new spectral analysis files.
 * @author Daniel
 *
 */
public class SpectralAnalysisConsumer implements Consumer, Runnable {
	private Path dir = Paths.get(Constants.PATH_SPECTRAL_ANALYSIS);
	private WatchService watcher;
	
	//converter to convert the raw data into pojos
	private SpectralAnalysisDataConverter converter;
	
	//singleton instance
	private static SpectralAnalysisConsumer consumer;
	
	//list of listeners to be notified
	private static CopyOnWriteArrayList<SpectralAnalysisListener> listeners;
	
	/**
	 * Constructor.
	 */
	private SpectralAnalysisConsumer() {
		listeners = new CopyOnWriteArrayList<>();
		converter = new SpectralAnalysisDataConverter();
		
		try {
			//register the directory
			watcher = FileSystems.getDefault().newWatchService();
			dir.register(watcher, ENTRY_CREATE);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initializes the spectral analysis consumer.
	 */
	public static void initialize() {
		if(consumer == null) {
			consumer = new SpectralAnalysisConsumer();
		}
		new Thread(consumer).start();
	}
	
	/**
	 * Registers a listener object
	 * for the spectral analysis data event.
	 * @param listener listener to be registered.
	 */
	public static void setOnSpectralAnalysisListener(SpectralAnalysisListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Removes a listener object
	 * from the listeners collection.
	 * @param listener listener to be removed.
	 */
	public static void removeSpectralAnalysisListener(SpectralAnalysisListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Starts the thread.
	 */
	@Override
	public void run() {
		monitorDir();
	}
	
	/**
	 * Monitores the directory and fires
	 * an event as soon as a new file is created.
	 */
	private void monitorDir() {
		while(true) {
			WatchKey key;
			try {
				//wait for key to be signaled
				key = watcher.take();
			} catch(InterruptedException e) {
				e.printStackTrace();
				return;
			}
			
			for(WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();
				
				if(kind == OVERFLOW) continue;
				
				//the filename is the context of the event
				@SuppressWarnings("unchecked")
				WatchEvent<Path> ev = (WatchEvent<Path>) event; 
				Path fileName = ev.context();
				
				//if a file was created read it
				if(kind == ENTRY_CREATE) {
					readNewFile(fileName.toString());
				}
			}
			
			//if valid == false then the directory might not be accesible any more
			boolean valid = key.reset();
			if(!valid) {
				break;
			}
		}
	}
	
	/**
	 * Reads the new file.
	 * @param fileName the name of the file to be read.
	 */
	private void readNewFile(String fileName) {
		String file = Constants.PATH_SPECTRAL_ANALYSIS + fileName;
		BufferedReader reader = null;
		String content = "";
		
		try {
			//create a buffered reader and read the content of the file
			reader = new BufferedReader(new FileReader(file));
			
			String line;
			while((line = reader.readLine()) != null) {
				content += line;
			}
			
			//propagate event to all registered listeners
			propagateEvent(converter.convert(content));
			//System.out.println(converter.convert(content));
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(reader != null) reader.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Notifies all listeners that have registered.
	 * @param data
	 */
	private void propagateEvent(SpectralAnalysisData data) {
		for(SpectralAnalysisListener listener : listeners) {
			listener.onSpectralAnalysisData(data);
		}
	}
}