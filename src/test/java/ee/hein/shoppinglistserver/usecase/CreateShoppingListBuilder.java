package ee.hein.shoppinglistserver.usecase;

import ee.hein.shoppinglistserver.controller.api.request.CreateShoppingListRequest;
import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.service.ShoppingListService;
import net.bytebuddy.utility.RandomString;

public class CreateShoppingListBuilder {

    private final ShoppingListService shoppingListService;
    private String name = RandomString.make(8);

    public CreateShoppingListBuilder(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public CreateShoppingListBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ShoppingListResource build() {
        return shoppingListService.create(
                new CreateShoppingListRequest()
                        .setName(name)
        );
    }
}
