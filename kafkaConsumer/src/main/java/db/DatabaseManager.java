package db;

import org.bson.Document;

import com.google.gson.Gson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import app.Constants;

import model.dataModels.ManufacturingData;

/**
 * Class DatabaseManager.
 * This class is responsible for
 * interacting with the mongodb.
 * @author Philip
 *
 */
public class DatabaseManager {
	private MongoClient mongoClient;
	private MongoDatabase db;
	private Gson gson;
	
	//singleton instance
	private static DatabaseManager dbManager;
			
	/**
	 * Constructor.
	 */
	private DatabaseManager() {
		mongoClient = new MongoClient();
		db = mongoClient.getDatabase(Constants.DATABASE_NAME);
		gson = new Gson();
	}
	
	/**
	 * Returns the database manager object.
	 * @return
	 */
	public static DatabaseManager getManager() {
		if(dbManager == null) {
			dbManager = new DatabaseManager();
		}
		return dbManager;
	}
	
	/**
	 * Stores the json formatted string message
	 * in the database collection specified.
	 * @param data
	 */
	public void insertManifacturingDocument(ManufacturingData data) {
		String json = gson.toJson(data);
        Document doc = Document.parse(json);
		db.getCollection(Constants.DATABASE_COLLECTION_NAME).insertOne(doc);
	}
}