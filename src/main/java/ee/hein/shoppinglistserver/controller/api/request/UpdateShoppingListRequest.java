package ee.hein.shoppinglistserver.controller.api.request;

import ee.hein.shoppinglistserver.pojo.Item;
import ee.hein.shoppinglistserver.pojo.RequestConstants;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UpdateShoppingListRequest {

    @NotNull
    private ObjectId shoppingListId;

    @NotNull
    @Size(min = 1, max = RequestConstants.SHORT_TEXT_LENGTH)
    private String name;

    @NotEmpty
    @Size(max = 100)
    private List<@NotNull Item> items;

    public ObjectId getShoppingListId() {
        return shoppingListId;
    }

    public UpdateShoppingListRequest setShoppingListId(ObjectId shoppingListId) {
        this.shoppingListId = shoppingListId;
        return this;
    }

    public String getName() {
        return name;
    }

    public UpdateShoppingListRequest setName(String name) {
        this.name = name;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public UpdateShoppingListRequest setItems(List<Item> items) {
        this.items = items;
        return this;
    }
}
