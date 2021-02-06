package ee.hein.shoppinglistserver.controller.api.request;

import org.bson.types.ObjectId;

import java.util.List;

public class AddItemsRequest {

    private ObjectId shoppingListId;
    private List<String> items;

    public ObjectId getShoppingListId() {
        return shoppingListId;
    }

    public AddItemsRequest setShoppingListId(ObjectId shoppingListId) {
        this.shoppingListId = shoppingListId;
        return this;
    }

    public List<String> getItems() {
        return items;
    }

    public AddItemsRequest setItems(List<String> items) {
        this.items = items;
        return this;
    }
}
