package ee.hein.shoppinglistserver.persistence.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.UpdateResult;
import ee.hein.shoppinglistserver.persistence.MongoService;
import ee.hein.shoppinglistserver.persistence.entity.ShoppingList;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

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
                .sort(ascending("order"))
                .cursor();
        return asList(cursor);
    }

    public Optional<ShoppingList> findOne(ObjectId shoppingListId) {
        return Optional.ofNullable(collection.find(
                eq("_id", shoppingListId)
        ).first());
    }

    public boolean update(ShoppingList shoppingList, long expectedVersion) {
        final UpdateResult updateResult = collection.replaceOne(
                and(
                        eq("_id", shoppingList.getId()),
                        eq("version", expectedVersion)
                ),
                shoppingList,
                new ReplaceOptions().upsert(false)
        );

        return updateResult.getModifiedCount() == 1;
    }

    private static <T> List<T> asList(MongoCursor<T> cursor) {
        List<T> result = new ArrayList<>();
        cursor.forEachRemaining(result::add);
        return result;
    }

    public void delete(List<ObjectId> shoppingListIds) {
        collection.deleteMany(
                in("_id", shoppingListIds)
        );
    }

    public ShoppingList getHighestOrderedItem() {
        return collection.find()
                .sort(descending("order"))
                .limit(1)
                .first();
    }
}
