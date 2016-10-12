package db;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import model.dataModels.ManufacturingData;

/**
 * Class DatabaseManager.
 * Interact with the mongoDB and store the JSON-Documents.
 * @author Philip
 *
 */
public class DatabaseManager {
	private MongoClient mongoClient;
	private MongoDatabase db;
	private Gson gson;
	
	private static DatabaseManager dbManager;
			
	/**
	 * Constructor DatabaseManger.
	 * Singleton-Pattern! => private constructor.	
	 */
	private DatabaseManager(){
		mongoClient = new MongoClient();
		db = mongoClient.getDatabase("oip_taktstrasse");
		gson = new Gson();
	}
	
	/**
	 * getManager method.
	 * Is used to obtain an instance of the dbManager.
	 * @return
	 */
	public static DatabaseManager getManager() {
		
		if(dbManager == null) {
			dbManager = new DatabaseManager();
		}
		return dbManager;
	}
	
	/**
	 * Insert method
	 * Stores JSON formatted String message to the specified database collection.
	 * @param collection, message
	 */
	public void insertManifacturingDocument(ManufacturingData data){
		String json = gson.toJson(data);
        Document doc = Document.parse(json);
		db.getCollection("manufacturingData").insertOne(doc);
	}
}
