package ee.hein.shoppinglistserver.usecase;

import ee.hein.shoppinglistserver.controller.api.request.AddItemsRequest;
import ee.hein.shoppinglistserver.persistence.entity.Item;
import ee.hein.shoppinglistserver.service.ShoppingListService;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddItemsBuilder {

    private final ShoppingListService shoppingListService;
    private final List<Item> items = new ArrayList<>();
    private ObjectId shoppingListId;

    public AddItemsBuilder(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public AddItemsBuilder setShoppingListId(ObjectId shoppingListId) {
        this.shoppingListId = shoppingListId;
        return this;
    }

    public AddItemsBuilder addItems(Item... items) {
        this.items.addAll(Arrays.asList(items));
        return this;
    }

    public void build() {
        shoppingListService.addItems(
                new AddItemsRequest()
                        .setShoppingListId(shoppingListId)
                        .setItems(items)
        );
    }
}
