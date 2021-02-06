package ee.hein.shoppinglistserver.controller.api.request;

import org.bson.types.ObjectId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderShoppingListsRequest {

    @NotEmpty
    private List<@NotNull ObjectId> shoppingListIds;

    public List<ObjectId> getShoppingListIds() {
        return shoppingListIds;
    }

    public OrderShoppingListsRequest setShoppingListIds(List<ObjectId> shoppingListIds) {
        this.shoppingListIds = shoppingListIds;
        return this;
    }
}
