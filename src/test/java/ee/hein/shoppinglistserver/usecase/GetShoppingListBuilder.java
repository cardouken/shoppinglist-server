package ee.hein.shoppinglistserver.usecase;

import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.service.ShoppingListService;
import org.bson.types.ObjectId;

public class GetShoppingListBuilder {

    private final ShoppingListService shoppingListService;
    private ObjectId shoppingListId;

    public GetShoppingListBuilder(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public GetShoppingListBuilder setShoppingListId(ObjectId shoppingListId) {
        this.shoppingListId = shoppingListId;
        return this;
    }

    public ShoppingListResource build() {
        return shoppingListService.get(shoppingListId);
    }
}
