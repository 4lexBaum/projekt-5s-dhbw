package database;

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
	
	public void insertDocument(String collection, String message){
		
        Document doc = null;
		db.getCollection(collection).insertOne(doc);
		
		
	}

}
