package ee.hein.shoppinglistserver.controller.api.request;

import ee.hein.shoppinglistserver.persistence.entity.Item;
import org.bson.types.ObjectId;

import java.util.List;

public class AddItemsRequest {

    private ObjectId shoppingListId;
    private List<Item> items;

    public ObjectId getShoppingListId() {
        return shoppingListId;
    }

    public AddItemsRequest setShoppingListId(ObjectId shoppingListId) {
        this.shoppingListId = shoppingListId;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public AddItemsRequest setItems(List<Item> items) {
        this.items = items;
        return this;
    }
}
