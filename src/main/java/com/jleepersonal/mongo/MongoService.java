package com.jleepersonal.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoService {
	private static MongoClient mongoClient = new MongoClient();
	
	private MongoService() {}
	
	public static MongoClient getMongoClient() {
		return mongoClient;
	}
		
	public static MongoDatabase getMongoDB(String dbName) {
		return mongoClient.getDatabase(dbName);
	}
}


//TODO:
//Make this to be enum Singleton!
//db.users.insert({id:"123123", username: "test-user", password: "test"})