package ee.hein.shoppinglistserver;

import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.controller.response.ShoppingListsResponse;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ShoppingListTest extends BaseTest {

    @Test
    public void create_shopping_list() {
        // given
        final String name = "babbys first list";

        // when
        createShoppingList().setName(name).build();

        // then
        final ShoppingListsResponse response = listShoppingLists().build();
        Assert.assertEquals(response.getLists().get(0).getName(), name);
    }

    @Test
    public void add_items_to_list() {
        // given
        final ShoppingListResource shoppingList = createShoppingList().build();

        // when
        addItems(shoppingList).addItems("dildo", "rope", "chloroform").build();

        // then
        final ShoppingListResource shoppingListUpdated = getShoppingList(shoppingList).build();
        Assert.assertEquals(shoppingListUpdated.getItems(), List.of("dildo", "rope", "chloroform"));
    }

}
