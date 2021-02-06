package ee.hein.shoppinglistserver.usecase;

import ee.hein.shoppinglistserver.controller.response.ShoppingListsResponse;
import ee.hein.shoppinglistserver.service.ShoppingListService;
import ee.hein.shoppinglistserver.util.TestActionBuilder;

public class GetAllShoppingListsBuilder implements TestActionBuilder<ShoppingListsResponse> {

    private final ShoppingListService shoppingListService;

    public GetAllShoppingListsBuilder(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @Override
    public ShoppingListsResponse build() {
        return shoppingListService.listAll();
    }
}
