package ee.hein.shoppinglistserver.controller;

import ee.hein.shoppinglistserver.controller.api.request.DeleteShoppingListRequest;
import ee.hein.shoppinglistserver.controller.api.request.OrderShoppingListsRequest;
import ee.hein.shoppinglistserver.controller.api.request.UpdateShoppingListRequest;
import ee.hein.shoppinglistserver.controller.api.request.CreateShoppingListRequest;
import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.controller.response.ShoppingListsResponse;
import ee.hein.shoppinglistserver.service.ShoppingListService;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PostMapping(value = "/shopping-list/create")
    public void create(@Valid @RequestBody CreateShoppingListRequest request) {
        shoppingListService.create(request);
    }

    @GetMapping(value = "/shopping-lists/list")
    public ShoppingListsResponse getLists() {
        return shoppingListService.listAll();
    }

    @GetMapping(value = "/shopping-list/get/{id}")
    public ShoppingListResource getList(@PathVariable ObjectId id) {
        return shoppingListService.findOne(id);
    }

    @PutMapping(value = "/shopping-list/update")
    public void update(@Valid @RequestBody UpdateShoppingListRequest request) {
        shoppingListService.update(request);
    }

    @DeleteMapping(value = "/shopping-list/delete")
    public void delete(@Valid @RequestBody DeleteShoppingListRequest request) {
        shoppingListService.delete(request);
    }

    @PutMapping(value = "/shopping-list/order")
    public void order(@Valid @RequestBody OrderShoppingListsRequest request) {
        shoppingListService.order(request);
    }
}
