package db;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Class DatabaseManager.
 * Interact with the mongoDB and store the JSON-Documents.
 * @author Philip
 *
 */
public class DatabaseManager {
	private MongoClient mongoClient;
	private MongoDatabase db;
	
	private static DatabaseManager dbManager;
			
	/**
	 * Constructor DatabaseManger.
	 * Singleton-Pattern! => private constructor.	
	 */
	private DatabaseManager(){
		mongoClient = new MongoClient();
		db = mongoClient.getDatabase("oip_taktstrasse");
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
	public void insertDocument(String collection, String message){
        Document doc = Document.parse(message);
		db.getCollection(collection).insertOne(doc);
	}
}
