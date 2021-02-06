package ee.hein.shoppinglistserver.usecase;

import ee.hein.shoppinglistserver.controller.response.ShoppingListsResponse;
import ee.hein.shoppinglistserver.service.ShoppingListService;

public class ListShoppingListsBuilder {

    private final ShoppingListService shoppingListService;

    public ListShoppingListsBuilder(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public ShoppingListsResponse build() {
        return shoppingListService.getAll();
    }
}
