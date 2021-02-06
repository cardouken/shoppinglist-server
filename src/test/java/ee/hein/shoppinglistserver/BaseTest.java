package ee.hein.shoppinglistserver;

import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.service.MongoTestService;
import ee.hein.shoppinglistserver.service.ShoppingListService;
import ee.hein.shoppinglistserver.usecase.AddItemsBuilder;
import ee.hein.shoppinglistserver.usecase.CreateShoppingListBuilder;
import ee.hein.shoppinglistserver.usecase.GetShoppingListBuilder;
import ee.hein.shoppinglistserver.usecase.ListShoppingListsBuilder;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {ShoppinglistServerApplication.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(BaseTest.TEST_PROFILE)
public abstract class BaseTest {

    static final String TEST_PROFILE = "test";

    @Autowired
    private ShoppingListService shoppingListService;

    @Autowired
    private MongoTestService mongoTestService;

    @Before
    public void beforeMethod() {
        mongoTestService.emptyKeyspace();
    }

    public CreateShoppingListBuilder createShoppingList() {
        return new CreateShoppingListBuilder(shoppingListService);
    }

    public ListShoppingListsBuilder listShoppingLists() {
        return new ListShoppingListsBuilder(shoppingListService);
    }

    public AddItemsBuilder addItems(ShoppingListResource shoppingList) {
        return new AddItemsBuilder(shoppingListService)
                .setShoppingListId(shoppingList.getId());
    }

    public GetShoppingListBuilder getShoppingList(ShoppingListResource shoppingList) {
        return new GetShoppingListBuilder(shoppingListService)
                .setShoppingListId(shoppingList.getId());
    }

}
