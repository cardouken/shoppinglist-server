package ee.hein.shoppinglistserver.usecase;

import ee.hein.shoppinglistserver.controller.api.request.UpdateShoppingListRequest;
import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.pojo.Item;
import ee.hein.shoppinglistserver.service.ShoppingListService;
import ee.hein.shoppinglistserver.util.TestActionBuilder;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UpdateShoppingListBuilder implements TestActionBuilder<Void> {

    private final ShoppingListService shoppingListService;
    private final List<Item> items = new ArrayList<>();
    private ObjectId shoppingListId;
    private String name;

    public UpdateShoppingListBuilder(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public UpdateShoppingListBuilder setShoppingList(ShoppingListResource shoppingList) {
        this.shoppingListId = shoppingList.getId();
        this.name = shoppingList.getName();
        return this;
    }

    public UpdateShoppingListBuilder withItems(Item... items) {
        this.items.addAll(Arrays.asList(items));
        return this;
    }

    public UpdateShoppingListBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Void build() {
        shoppingListService.update(
                new UpdateShoppingListRequest()
                        .setShoppingListId(Objects.requireNonNull(shoppingListId))
                        .setName(Objects.requireNonNull(name))
                        .setItems(Objects.requireNonNull(items))
        );
        return empty();
    }
}
