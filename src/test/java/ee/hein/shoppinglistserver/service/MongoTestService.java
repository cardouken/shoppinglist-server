package ee.hein.shoppinglistserver.service;

import com.mongodb.client.MongoClient;
import ee.hein.shoppinglistserver.persistence.MongoService;
import org.bson.BsonDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class MongoTestService extends MongoService {

    @Autowired
    public MongoTestService(MongoClient mongoClient, MongoProperties mongoProperties) {
        super(mongoClient, mongoProperties.getDatabase());
        if (!mongoProperties.getUri().contains("localhost") && !mongoProperties.getUri().contains("127.0.0.1")) {
            throw new RuntimeException("Non localhost database connection detected in tests! Actual connection string: " + mongoProperties.getUri());
        }
        database.drop();
    }

    public void emptyKeyspace() {
        for (String collectionName : database.listCollectionNames()) {
            database.getCollection(collectionName).deleteMany(new BsonDocument());
        }
    }
}
