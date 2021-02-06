package ee.hein.shoppinglistserver.controller.response;

import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;

import java.util.List;

public class ShoppingListsResponse {

    private List<ShoppingListResource> lists;

    public List<ShoppingListResource> getLists() {
        return lists;
    }

    public ShoppingListsResponse setLists(List<ShoppingListResource> lists) {
        this.lists = lists;
        return this;
    }
}
