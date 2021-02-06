package ee.hein.shoppinglistserver.persistence.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import ee.hein.shoppinglistserver.persistence.MongoService;
import ee.hein.shoppinglistserver.persistence.entity.ShoppingList;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class ShoppingListRepository {

    private final MongoCollection<ShoppingList> collection;

    public ShoppingListRepository(MongoService mongoService) {
        final MongoDatabase database = mongoService.getDatabase();
        for (String name : database.listCollectionNames()) {
            if (name.equalsIgnoreCase("ShoppingList")) {
                continue;
            }
            database.createCollection("ShoppingList");
        }
        this.collection = mongoService.getDatabase().getCollection("ShoppingList", ShoppingList.class);
    }

    public void save(ShoppingList shoppingList) {
        collection.replaceOne(
                eq("_id", shoppingList.getId()),
                shoppingList,
                new ReplaceOptions().upsert(true));
    }

    public List<ShoppingList> list() {
        final MongoCursor<ShoppingList> cursor = collection.find()
                .cursor();
        return asList(cursor);

    }

    private static <T> List<T> asList(MongoCursor<T> cursor) {
        List<T> result = new ArrayList<>();
        cursor.forEachRemaining(result::add);
        return result;
    }

    public ShoppingList findOne(ObjectId shoppingListId) {
        return collection.find(
                eq("_id", shoppingListId)
        ).first();
    }
}
