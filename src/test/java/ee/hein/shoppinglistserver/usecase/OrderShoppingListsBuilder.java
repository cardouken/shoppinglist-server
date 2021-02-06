package ee.hein.shoppinglistserver.usecase;

import ee.hein.shoppinglistserver.controller.api.request.OrderShoppingListsRequest;
import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.service.ShoppingListService;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class OrderShoppingListsBuilder {

    private final ShoppingListService shoppingListService;
    private List<ObjectId> shoppingListIds = new ArrayList<>();

    public OrderShoppingListsBuilder(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public OrderShoppingListsBuilder setShoppingLists(ShoppingListResource... shoppingLists) {
        for (ShoppingListResource shoppingList : shoppingLists) {
            this.shoppingListIds.add(shoppingList.getId());
        }
        return this;
    }

    public void build() {
        shoppingListService.order(
                new OrderShoppingListsRequest()
                        .setShoppingListIds(shoppingListIds)
        );
    }

}
