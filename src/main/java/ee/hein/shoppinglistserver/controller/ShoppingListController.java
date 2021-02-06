package ee.hein.shoppinglistserver.controller;

import ee.hein.shoppinglistserver.controller.api.request.AddItemsRequest;
import ee.hein.shoppinglistserver.controller.api.request.CreateShoppingListRequest;
import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.controller.response.ShoppingListsResponse;
import ee.hein.shoppinglistserver.service.ShoppingListService;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PostMapping(value = "/shoppinglist/create")
    public ShoppingListResource create(@Valid @RequestBody CreateShoppingListRequest request) {
        return shoppingListService.create(request);
    }

    @GetMapping(value = "/shoppinglist/list")
    public ShoppingListsResponse getAll() {
        return shoppingListService.getAll();
    }

    @GetMapping(value = "/shoppinglist/get/{id}")
    public ShoppingListResource getList(@PathVariable ObjectId id) {
        return shoppingListService.get(id);
    }

    @PostMapping("/shoppinglist/add-items")
    public void addItems(@Valid @RequestBody AddItemsRequest request) {
        shoppingListService.addItems(request);
    }
}
