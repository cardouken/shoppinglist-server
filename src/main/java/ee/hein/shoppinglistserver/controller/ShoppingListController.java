package ee.hein.shoppinglistserver.controller;

import ee.hein.shoppinglistserver.controller.api.request.CreateShoppingListRequest;
import ee.hein.shoppinglistserver.controller.api.request.DeleteShoppingListRequest;
import ee.hein.shoppinglistserver.controller.api.request.OrderShoppingListsRequest;
import ee.hein.shoppinglistserver.controller.api.request.UpdateShoppingListRequest;
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

    @PostMapping(value = "/list/create")
    public void create(@Valid @RequestBody CreateShoppingListRequest request) {
        shoppingListService.create(request);
    }

    @GetMapping(value = "/lists/")
    public ShoppingListsResponse getAllLists() {
        return shoppingListService.findAll();
    }

    @GetMapping(value = "/list/view/{id}")
    public ShoppingListResource view(@PathVariable ObjectId id) {
        return shoppingListService.findOne(id);
    }

    @PutMapping(value = "/list/update")
    public void update(@Valid @RequestBody UpdateShoppingListRequest request) {
        shoppingListService.update(request);
    }

    @DeleteMapping(value = "/list/delete")
    public void delete(@Valid @RequestBody DeleteShoppingListRequest request) {
        shoppingListService.delete(request);
    }

    @PutMapping(value = "/list/order")
    public void order(@Valid @RequestBody OrderShoppingListsRequest request) {
        shoppingListService.order(request);
    }
}
