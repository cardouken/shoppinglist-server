package ee.hein.shoppinglistserver.usecase;

import ee.hein.shoppinglistserver.controller.response.ShoppingListsResponse;
import ee.hein.shoppinglistserver.service.ShoppingListService;

public class GetAllShoppingListsBuilder {

    private final ShoppingListService shoppingListService;

    public GetAllShoppingListsBuilder(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public ShoppingListsResponse build() {
        return shoppingListService.listAll();
    }
}
