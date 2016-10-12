package db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bson.Document;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

/**
 * Class DatabaseManager.
 * Interact with the mongoDB and store the JSON-Documents.
 * @author Philip
 *
 */

public class DatabaseManager {
	
	private String connectString = "127.0.0.1";
	private MongoClient mongoClient;
	private MongoDatabase db;
			
			
	public DatabaseManager(){
		
		mongoClient = new MongoClient();
		db = mongoClient.getDatabase("oip_taktstrasse");
		
	}
	
	/**
	 * Insert method
	 * Stores JSON formatted String message to the specified database collection.
	 * @param collection, message
	 */
	
	public void insertDocument(String collection, String message){
		
        Document doc = Document.parse(message);
		db.getCollection(collection).insertOne(doc);
		
		
	}
	
	/**
	 * Save latest spectralanalysis file
	 * Stores spectralanalysis file from filesystem to database.
	 * @param dir
	 */
	
	public void saveSpectralanalysis(String dir){
		
		InputStream in;
		File spectralAnalysis = getLatestFile(dir);
		
		try {
			
			in = new FileInputStream(spectralAnalysis);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        StringBuilder out = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            out.append(line);
	        }
	        System.out.println("saved spectralanalysis");
	        insertDocument("spectralanalysis", out.toString());
	        reader.close();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		
	}
	
	/**
	 * Get the latest file for a specified directory
	 * Returns the latest spectralanalysis file.
	 * @param dir
	 */
	
	public static File getLatestFile(String dir) {
	    File fl = new File(dir);
	    File[] files = fl.listFiles(new FileFilter() {          
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
