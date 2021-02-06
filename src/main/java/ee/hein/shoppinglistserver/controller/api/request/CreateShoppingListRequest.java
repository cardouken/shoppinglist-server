package ee.hein.shoppinglistserver.controller.api.request;

import ee.hein.shoppinglistserver.pojo.Item;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateShoppingListRequest {

    @NotNull
    private String name;

    private List<@NotNull Item> items;

    public String getName() {
        return name;
    }

    public CreateShoppingListRequest setName(String name) {
        this.name = name;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public CreateShoppingListRequest setItems(List<Item> items) {
        this.items = items;
        return this;
    }
}
