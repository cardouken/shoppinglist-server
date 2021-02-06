package ee.hein.shoppinglistserver.persistence.entity;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ShoppingList {

    @BsonId
    private ObjectId id;

    @NotNull
    private String name;

    private List<Item> items;

    public ObjectId getId() {
        return id;
    }

    public ShoppingList setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ShoppingList setName(String name) {
        this.name = name;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public ShoppingList setItems(List<Item> items) {
        this.items = items;
        return this;
    }
}
