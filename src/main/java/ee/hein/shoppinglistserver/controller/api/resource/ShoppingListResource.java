package ee.hein.shoppinglistserver.controller.api.resource;

import ee.hein.shoppinglistserver.pojo.Item;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

public class ShoppingListResource {

    private ObjectId id;
    private String name;
    private List<Item> items;
    private int order;
    private LocalDateTime created;

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

    public List<Item> getItems() {
        return items;
    }

    public ShoppingListResource setItems(List<Item> items) {
        this.items = items;
        return this;
    }

    public int getOrder() {
        return order;
    }

    public ShoppingListResource setOrder(int order) {
        this.order = order;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public ShoppingListResource setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }
}
