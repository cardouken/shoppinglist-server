package ee.hein.shoppinglistserver;

import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.controller.response.ShoppingListsResponse;
import ee.hein.shoppinglistserver.pojo.Item;
import ee.hein.shoppinglistserver.util.ExpectedException;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

public class ShoppingListTest extends BaseTest {

    @Test
    public void create_empty_list() {
        // given
        final String name = "most important stuff ever";

        // when
        final ShoppingListResource shoppingList = createShoppingList().setName(name).build();

        // then
        final ShoppingListsResponse shoppingListsResponse = getAllShoppingLists().build();

        Assert.assertEquals(shoppingList.getId(), shoppingListsResponse.getShoppingLists().get(0).getId());
        Assert.assertEquals(shoppingList.getName(), shoppingListsResponse.getShoppingLists().get(0).getName());
        Assert.assertEquals(1, shoppingListsResponse.getShoppingLists().size());
        Assert.assertEquals(0, shoppingListsResponse.getShoppingLists().get(0).getItems().size());
    }

    @Test
    public void create_list_with_items() {
        // given
        final Item cream = createItem("cream", false).build();
        final Item hammer = createItem("hammer", false).build();
        final ShoppingListResource shoppingList = createShoppingList().addItems(cream, hammer).build();

        // when
        final ShoppingListResource response = getShoppingList(shoppingList).build();

        // then
        Assert.assertEquals(2, response.getItems().size());
        Assert.assertEquals(cream.getName(), response.getItems().get(0).getName());
        Assert.assertEquals(hammer.getName(), response.getItems().get(1).getName());
    }

    @Test
    public void get_single_list() {
        // given
        createShoppingList().build();
        final ShoppingListResource secondShoppingList = createShoppingList().build();

        // when
        final ShoppingListResource response = getShoppingList(secondShoppingList).build();

        // then
        Assert.assertEquals(secondShoppingList.getId(), response.getId());
    }

    @Test
    public void get_all_lists() {
        // given
        final ShoppingListResource firstShoppingList = createShoppingList().setName("third").build();
        final ShoppingListResource secondShoppingList = createShoppingList().setName("fourth").build();

        // when
        final ShoppingListsResponse response = getAllShoppingLists().build();

        // then
        Assert.assertEquals(2, response.getShoppingLists().size());
        Assert.assertEquals(firstShoppingList.getId(), response.getShoppingLists().get(0).getId());
        Assert.assertEquals(secondShoppingList.getId(), response.getShoppingLists().get(1).getId());
    }

    @Test
    public void add_items_to_list() {
        // given
        final ShoppingListResource shoppingList = createShoppingList().build();
        final Item cream = createItem("cream", false).build();
        final Item hammer = createItem("hammer", false).build();
        final Item manholeCover = createItem("manhole cover", false).build();

        // when
        updateShoppingList(shoppingList).withItems(cream, hammer, manholeCover).build();

        // then
        final ShoppingListResource response = getShoppingList(shoppingList).build();
        Assert.assertEquals(3, response.getItems().size());
        Assert.assertEquals(cream.getName(), response.getItems().get(0).getName());
        Assert.assertEquals(hammer.getName(), response.getItems().get(1).getName());
        Assert.assertEquals(manholeCover.getName(), response.getItems().get(2).getName());
    }

    @Test
    public void remove_items_from_list() {
        // given
        final Item cream = createItem("cream", false).build();
        final Item hammer = createItem("hammer", false).build();
        final Item manholeCover = createItem("manhole cover", false).build();
        final ShoppingListResource shoppingList = createShoppingList().addItems(cream, hammer, manholeCover).build();

        // when
        updateShoppingList(shoppingList).withItems(cream, hammer).build();

        // then
        final ShoppingListResource response = getShoppingList(shoppingList).build();
        Assert.assertEquals(2, response.getItems().size());
        Assert.assertEquals(cream.getName(), response.getItems().get(0).getName());
        Assert.assertEquals(hammer.getName(), response.getItems().get(1).getName());
    }

    @Test
    public void update_list_without_items() {
        // given
        final Item cream = createItem("cream", false).build();
        final Item hammer = createItem("hammer", false).build();
        final Item manholeCover = createItem("manhole cover", false).build();
        final ShoppingListResource shoppingList = createShoppingList().addItems(cream, hammer, manholeCover).build();

        // when
        updateShoppingList(shoppingList).withItems().build();

        // then
        final ShoppingListResource response = getShoppingList(shoppingList).build();
        Assert.assertEquals(0, response.getItems().size());
    }

    @Test
    public void get_nonexistent_list() {
        // given
        final ShoppingListResource nonExistentList = new ShoppingListResource();

        // when -> then
        ExpectedException.expect(
                () -> getShoppingList(nonExistentList).build(),
                EntityNotFoundException.class
        );
    }

    @Test
    public void delete_single_list() {
        // given
        final ShoppingListResource shoppingList = createShoppingList().build();
        final ShoppingListResource listToDelete = createShoppingList().build();

        // when
        deleteShoppingLists().shoppingLists(listToDelete).build();

        // then
        final ShoppingListsResponse response = getAllShoppingLists().build();
        Assert.assertEquals(1, response.getShoppingLists().size());
        Assert.assertEquals(shoppingList.getId(), response.getShoppingLists().get(0).getId());
    }

    @Test
    public void delete_all_lists() {
        // given
        final ShoppingListResource firstList = createShoppingList().build();
        final ShoppingListResource secondList = createShoppingList().build();

        // when
        deleteShoppingLists().shoppingLists(firstList, secondList).build();

        // then
        final ShoppingListsResponse response = getAllShoppingLists().build();
        Assert.assertEquals(0, response.getShoppingLists().size());
    }

    @Test
    public void test_ordering_on_list_creation() {
        // given
        final ShoppingListResource first = createShoppingList().build();
        final ShoppingListResource second = createShoppingList().build();
        final ShoppingListResource third = createShoppingList().build();
        final ShoppingListResource fourth = createShoppingList().build();

        // when
        final ShoppingListsResponse response = getAllShoppingLists().build();

        // then
        Assert.assertEquals(1, response.getShoppingLists().get(0).getOrder());
        Assert.assertEquals(first.getId(), response.getShoppingLists().get(0).getId());

        Assert.assertEquals(2, response.getShoppingLists().get(1).getOrder());
        Assert.assertEquals(second.getId(), response.getShoppingLists().get(1).getId());

        Assert.assertEquals(3, response.getShoppingLists().get(2).getOrder());
        Assert.assertEquals(third.getId(), response.getShoppingLists().get(2).getId());

        Assert.assertEquals(4, response.getShoppingLists().get(3).getOrder());
        Assert.assertEquals(fourth.getId(), response.getShoppingLists().get(3).getId());
    }

    @Test
    public void reorder_lists() {
        // given
        final ShoppingListResource first = createShoppingList().setName("first").build();
        final ShoppingListResource second = createShoppingList().setName("second").build();
        final ShoppingListResource third = createShoppingList().setName("third").build();
        final ShoppingListResource fourth = createShoppingList().setName("fourth").build();

        // when
        orderShoppingLists().setShoppingLists(first, third, second, fourth).build();

        // then
        final ShoppingListsResponse response = getAllShoppingLists().build();
        Assert.assertEquals(1, response.getShoppingLists().get(0).getOrder());
        Assert.assertEquals(first.getId(), response.getShoppingLists().get(0).getId());

        Assert.assertEquals(2, response.getShoppingLists().get(1).getOrder());
        Assert.assertEquals(third.getId(), response.getShoppingLists().get(1).getId());

        Assert.assertEquals(3, response.getShoppingLists().get(2).getOrder());
        Assert.assertEquals(second.getId(), response.getShoppingLists().get(2).getId());

        Assert.assertEquals(4, response.getShoppingLists().get(3).getOrder());
        Assert.assertEquals(fourth.getId(), response.getShoppingLists().get(3).getId());
    }

}
