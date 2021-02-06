package ee.hein.shoppinglistserver.usecase;

import ee.hein.shoppinglistserver.controller.api.request.OrderShoppingListsRequest;
import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.service.ShoppingListService;
import ee.hein.shoppinglistserver.util.TestActionBuilder;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ReorderShoppingListsBuilder implements TestActionBuilder<Void> {

    private final ShoppingListService shoppingListService;
    private final List<ObjectId> shoppingListIds = new ArrayList<>();

    public ReorderShoppingListsBuilder(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public ReorderShoppingListsBuilder setShoppingLists(ShoppingListResource... shoppingLists) {
        for (ShoppingListResource shoppingList : shoppingLists) {
            this.shoppingListIds.add(shoppingList.getId());
        }
        return this;
    }

    @Override
    public Void build() {
        shoppingListService.order(
                new OrderShoppingListsRequest()
                        .setShoppingListIds(shoppingListIds)
        );
        return empty();
    }

}
