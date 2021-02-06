package ee.hein.shoppinglistserver.controller.api.request;

import ee.hein.shoppinglistserver.pojo.Item;
import ee.hein.shoppinglistserver.pojo.RequestConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CreateShoppingListRequest {

    @NotNull
    @Size(min = 1, max = RequestConstants.SHORT_TEXT_LENGTH)
    private String name;

    @Size(max = 100)
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
