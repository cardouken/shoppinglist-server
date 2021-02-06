package ee.hein.shoppinglistserver;

import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.persistence.repository.ShoppingListRepository;
import ee.hein.shoppinglistserver.service.MongoTestService;
import ee.hein.shoppinglistserver.service.ShoppingListService;
import ee.hein.shoppinglistserver.usecase.CreateItemBuilder;
import ee.hein.shoppinglistserver.usecase.CreateShoppingListBuilder;
import ee.hein.shoppinglistserver.usecase.DeleteShoppingListBuilder;
import ee.hein.shoppinglistserver.usecase.GetShoppingListBuilder;
import ee.hein.shoppinglistserver.usecase.GetAllShoppingListsBuilder;
import ee.hein.shoppinglistserver.usecase.ReorderShoppingListsBuilder;
import ee.hein.shoppinglistserver.usecase.UpdateShoppingListBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = ShoppinglistServerApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(BaseTest.TEST_PROFILE)
public abstract class BaseTest {

    static final String TEST_PROFILE = "test";

    @Autowired
    private MongoTestService mongoTestService;

    @Autowired
    private ShoppingListService shoppingListService;

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @BeforeEach
    public void beforeMethod() {
        mongoTestService.emptyKeyspace();
    }

    public CreateShoppingListBuilder createShoppingList() {
        return new CreateShoppingListBuilder(shoppingListService, shoppingListRepository);
    }

    public GetShoppingListBuilder getShoppingList(ShoppingListResource shoppingList) {
        return new GetShoppingListBuilder(shoppingListService)
                .setShoppingListId(shoppingList.getId());
    }

    public GetAllShoppingListsBuilder getAllShoppingLists() {
        return new GetAllShoppingListsBuilder(shoppingListService);
    }

    public UpdateShoppingListBuilder updateShoppingList(ShoppingListResource shoppingList) {
        return new UpdateShoppingListBuilder(shoppingListService)
                .setShoppingList(shoppingList);
    }

    public CreateItemBuilder createItem(String name, boolean checked) {
        return new CreateItemBuilder(name, checked);
    }

    public DeleteShoppingListBuilder deleteShoppingLists() {
        return new DeleteShoppingListBuilder(shoppingListService);
    }

    public ReorderShoppingListsBuilder orderShoppingLists() {
        return new ReorderShoppingListsBuilder(shoppingListService);
    }

}
