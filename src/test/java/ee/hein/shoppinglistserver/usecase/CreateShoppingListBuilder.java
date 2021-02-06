package ee.hein.shoppinglistserver.usecase;

import ee.hein.shoppinglistserver.controller.api.request.CreateShoppingListRequest;
import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.persistence.entity.ShoppingList;
import ee.hein.shoppinglistserver.persistence.repository.ShoppingListRepository;
import ee.hein.shoppinglistserver.pojo.Item;
import ee.hein.shoppinglistserver.service.ShoppingListService;
import ee.hein.shoppinglistserver.util.TestActionBuilder;
import net.bytebuddy.utility.RandomString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CreateShoppingListBuilder implements TestActionBuilder<ShoppingListResource> {

    private final ShoppingListService shoppingListService;
    private final ShoppingListRepository shoppingListRepository;
    private final List<Item> items = new ArrayList<>();
    private String name = RandomString.make(8);

    public CreateShoppingListBuilder(ShoppingListService shoppingListService, ShoppingListRepository shoppingListRepository) {
        this.shoppingListService = shoppingListService;
        this.shoppingListRepository = shoppingListRepository;
    }

    public CreateShoppingListBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CreateShoppingListBuilder addItems(Item... items) {
        this.items.addAll(Arrays.asList(items));
        return this;
    }

    @Override
    public ShoppingListResource build() {
        shoppingListService.create(
                new CreateShoppingListRequest()
                        .setName(Objects.requireNonNull(name))
                        .setItems(items)
        );

        final ShoppingList shoppingList = shoppingListRepository.getHighestOrderedItem();
        return new ShoppingListResource()
                .setId(shoppingList.getId())
                .setName(shoppingList.getName())
                .setItems(shoppingList.getItems())
                .setOrder(shoppingList.getOrder())
                .setCreated(shoppingList.getCreated());
    }
}
