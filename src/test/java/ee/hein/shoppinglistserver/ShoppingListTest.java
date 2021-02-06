package ee.hein.shoppinglistserver;

import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.pojo.Item;
import ee.hein.shoppinglistserver.util.ExpectedException;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

public class ShoppingListTest extends BaseTest {

    @Test
    public void test_api_fields() {
        // given
        final Item item = createItem("banana peels", false).build();
        final ShoppingListResource shoppingList = createShoppingList().addItems(item).setName("big list").build();

        // when -> then - assert all api fields with unchecked item
        viewShoppingList(shoppingList).buildApi()
                .assertThat("id", shoppingList.getId().toHexString())
                .assertThat("name", shoppingList.getName())
                .assertThat("order", "1")
                .assertExists("created")
                .assertNotExists("version")
                .separator()
                .assertThatArraySize("items", 1)
                .assertThat("items[0].id", item.getId().toHexString())
                .assertThat("items[0].name", item.getName())
                .assertNotExists("items[0].checked");

        // then - mark item as checked and assert checked field
        updateShoppingList(shoppingList).withItems(item.markCheckedNow()).build();
        viewShoppingList(shoppingList).buildApi()
                .assertExists("items[0].checked");
    }

    @Test
    public void create_empty_list() {
        // given
        final String name = "most important stuff ever";

        // when
        final ShoppingListResource shoppingList = createShoppingList().setName(name).build();

        // then
        getAllShoppingLists().buildApi()
                .assertThatArraySize("lists", 1)
                .assertThatArraySize("lists[0].items", 0)
                .assertThat("lists[0].id", shoppingList.getId().toHexString())
                .assertThat("lists[0].name", shoppingList.getName());
    }

    @Test
    public void create_list_with_items() {
        // given
        final Item cream = createItem("cream", false).build();
        final Item hammer = createItem("hammer", false).build();
        final ShoppingListResource shoppingList = createShoppingList().addItems(cream, hammer).build();

        // when
        viewShoppingList(shoppingList).buildApi()
                .assertThatArraySize("items", 2)
                .assertThat("items[0].name", cream.getName())
                .assertThat("items[1].name", hammer.getName());
    }

    @Test
    public void get_single_list() {
        // given
        createShoppingList().build();
        final ShoppingListResource secondShoppingList = createShoppingList().build();

        // when -> then
        viewShoppingList(secondShoppingList).buildApi()
                .assertThat("id", secondShoppingList.getId().toHexString());
    }

    @Test
    public void get_all_lists() {
        // given
        final ShoppingListResource firstShoppingList = createShoppingList().build();
        final ShoppingListResource secondShoppingList = createShoppingList().build();

        // when -> then
        getAllShoppingLists().buildApi()
                .assertThatArraySize("lists", 2)
                .assertThat("lists[0].id", firstShoppingList.getId().toHexString())
                .assertThat("lists[1].id", secondShoppingList.getId().toHexString());
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
        viewShoppingList(shoppingList).buildApi()
                .assertThatArraySize("items", 3)
                .assertThat("items[0].id", cream.getId().toHexString())
                .assertThat("items[1].id", hammer.getId().toHexString())
                .assertThat("items[2].id", manholeCover.getId().toHexString());
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
        viewShoppingList(shoppingList).buildApi()
                .assertThatArraySize("items", 2)
                .assertThat("items[0].id", cream.getId().toHexString())
                .assertThat("items[1].id", hammer.getId().toHexString());
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
        viewShoppingList(shoppingList).buildApi()
                .assertThatArraySize("items", 0);
    }

    @Test
    public void update_list_name() {
        // given
        final ShoppingListResource shoppingList = createShoppingList().setName("where'd u go").build();

        // when
        updateShoppingList(shoppingList).setName("i miss u so").build();

        // then
        viewShoppingList(shoppingList).buildApi()
                .assertThat("name", "i miss u so");
    }

    @Test
    public void mark_items_checked() {
        // given
        final Item cream = createItem("cream", false).build();
        final Item hammer = createItem("hammer", false).build();
        final Item manholeCover = createItem("manhole cover", false).build();
        final ShoppingListResource shoppingList = createShoppingList().addItems(cream, hammer, manholeCover).build();

        // when
        updateShoppingList(shoppingList).withItems(cream.markCheckedNow(), hammer.markCheckedNow(), manholeCover.markCheckedNow()).build();

        // then
        viewShoppingList(shoppingList).buildApi()
                .assertThatArraySize("items", 3)
                .assertExists("items[0].checked")
                .assertExists("items[1].checked")
                .assertExists("items[2].checked");
    }

    @Test
    public void mark_items_unchecked() {
        // given
        final Item cream = createItem("cream", true).build();
        final Item hammer = createItem("hammer", true).build();
        final Item manholeCover = createItem("manhole cover", true).build();
        final ShoppingListResource shoppingList = createShoppingList().addItems(cream, hammer, manholeCover).build();

        // when
        updateShoppingList(shoppingList).withItems(cream.markUnchecked(), hammer.markUnchecked(), manholeCover.markUnchecked()).build();

        // then
        viewShoppingList(shoppingList).buildApi()
                .assertThatArraySize("items", 3)
                .assertNotExists("items[0].checked")
                .assertNotExists("items[1].checked")
                .assertNotExists("items[2].checked");
    }

    @Test
    public void get_nonexistent_list() {
        // given
        final ShoppingListResource nonExistentList = new ShoppingListResource();

        // when -> then
        ExpectedException.expect(
                () -> viewShoppingList(nonExistentList).build(),
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
        getAllShoppingLists().buildApi()
                .assertThatArraySize("lists", 1)
                .assertThat("lists[0].id", shoppingList.getId().toHexString());
    }

    @Test
    public void delete_all_lists() {
        // given
        final ShoppingListResource firstList = createShoppingList().build();
        final ShoppingListResource secondList = createShoppingList().build();

        // when
        deleteShoppingLists().shoppingLists(firstList, secondList).build();

        // then
        getAllShoppingLists().buildApi()
                .assertThatArraySize("lists", 0);
    }

    @Test
    public void test_ordering_on_list_creation() {
        // given
        final ShoppingListResource first = createShoppingList().build();
        final ShoppingListResource second = createShoppingList().build();
        final ShoppingListResource third = createShoppingList().build();
        final ShoppingListResource fourth = createShoppingList().build();

        // when -> then
        getAllShoppingLists().buildApi()
                .assertThat("lists[0].id", first.getId().toHexString())
                .assertThat("lists[0].order", "1")
                .assertThat("lists[1].id", second.getId().toHexString())
                .assertThat("lists[1].order", "2")
                .assertThat("lists[2].id", third.getId().toHexString())
                .assertThat("lists[2].order", "3")
                .assertThat("lists[3].id", fourth.getId().toHexString())
                .assertThat("lists[3].order", "4");
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
        getAllShoppingLists().buildApi()
                .assertThat("lists[0].id", first.getId().toHexString())
                .assertThat("lists[0].order", "1")
                .assertThat("lists[1].id", third.getId().toHexString())
                .assertThat("lists[1].order", "2")
                .assertThat("lists[2].id", second.getId().toHexString())
                .assertThat("lists[2].order", "3")
                .assertThat("lists[3].id", fourth.getId().toHexString())
                .assertThat("lists[3].order", "4");
    }

    @Test
    public void mark_item_checked_twice() {
        // given
        final LocalDateTime fakeCheckedTime = LocalDateTime.of(2010, 10, 10, 13, 37);
        final Item item = createItem("t-rex skeleton", true).setCheckedTime(fakeCheckedTime).build();
        final ShoppingListResource shoppingList = createShoppingList().addItems(item).build();

        // when
        updateShoppingList(shoppingList).withItems(item.markCheckedNow()).build();

        // then
        viewShoppingList(shoppingList).buildApi()
                .assertThatStartsWith("items[0].checked", "2010-10-10T13:37");
    }

    @Test
    public void mark_item_checked_then_unchecked() {
        // given
        final Item item = createItem("t-rex skeleton", false).build();
        final ShoppingListResource shoppingList = createShoppingList().addItems(item).build();
        updateShoppingList(shoppingList).withItems(item.markCheckedNow()).build();

        // when
        updateShoppingList(shoppingList).withItems(item.setChecked(null)).build();

        // then
        viewShoppingList(shoppingList).buildApi()
                .assertNotExists("items[0].checked");
    }

}