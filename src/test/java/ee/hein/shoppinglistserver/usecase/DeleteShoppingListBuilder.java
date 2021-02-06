package ee.hein.shoppinglistserver.usecase;

import ee.hein.shoppinglistserver.controller.api.request.DeleteShoppingListRequest;
import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.service.ShoppingListService;
import ee.hein.shoppinglistserver.util.TestActionBuilder;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class DeleteShoppingListBuilder implements TestActionBuilder<Void> {

    private final ShoppingListService shoppingListService;
    private final List<ObjectId> shoppingListIds = new ArrayList<>();

    public DeleteShoppingListBuilder(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public DeleteShoppingListBuilder shoppingLists(ShoppingListResource... shoppingLists) {
        for (ShoppingListResource shoppingList : shoppingLists) {
            this.shoppingListIds.add(shoppingList.getId());
        }
        return this;
    }

    @Override
    public Void build() {
        shoppingListService.delete(
                new DeleteShoppingListRequest()
                        .setShoppingListIds(shoppingListIds)
        );
        return empty();
    }
}
