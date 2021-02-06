package ee.hein.shoppinglistserver.usecase;

import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.service.ShoppingListService;
import ee.hein.shoppinglistserver.util.TestActionBuilder;
import org.bson.types.ObjectId;

public class GetShoppingListBuilder implements TestActionBuilder<ShoppingListResource> {

    private final ShoppingListService shoppingListService;
    private ObjectId shoppingListId;

    public GetShoppingListBuilder(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public GetShoppingListBuilder setShoppingListId(ObjectId shoppingListId) {
        this.shoppingListId = shoppingListId;
        return this;
    }

    @Override
    public ShoppingListResource build() {
        return shoppingListService.findOne(shoppingListId);
    }
}
