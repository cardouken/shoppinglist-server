package ee.hein.shoppinglistserver.controller.api.resource;

import org.bson.types.ObjectId;

import java.util.List;

public class ShoppingListResource {

    private ObjectId id;
    private String name;
    private List<String> items;

    public ObjectId getId() {
        return id;
    }

    public ShoppingListResource setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ShoppingListResource setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getItems() {
        return items;
    }

    public ShoppingListResource setItems(List<String> items) {
        this.items = items;
        return this;
    }
}
