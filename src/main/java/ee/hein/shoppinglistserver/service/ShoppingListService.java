package ee.hein.shoppinglistserver.service;

import ee.hein.shoppinglistserver.controller.api.request.AddItemsRequest;
import ee.hein.shoppinglistserver.controller.api.request.CreateShoppingListRequest;
import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResourceFactory;
import ee.hein.shoppinglistserver.controller.response.ShoppingListsResponse;
import ee.hein.shoppinglistserver.persistence.entity.ShoppingList;
import ee.hein.shoppinglistserver.persistence.entity.factory.ShoppingListEntityFactory;
import ee.hein.shoppinglistserver.persistence.repository.ShoppingListRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListEntityFactory shoppingListEntityFactory;
    private final ShoppingListResourceFactory shoppingListResourceFactory;

    public ShoppingListService(ShoppingListRepository shoppingListRepository,
                               ShoppingListEntityFactory shoppingListEntityFactory,
                               ShoppingListResourceFactory shoppingListResourceFactory) {
        this.shoppingListRepository = shoppingListRepository;
        this.shoppingListEntityFactory = shoppingListEntityFactory;
        this.shoppingListResourceFactory = shoppingListResourceFactory;
    }

    public ShoppingListResource create(CreateShoppingListRequest request) {
        final ShoppingList shoppingList = shoppingListEntityFactory.create(request.getName(), null);
        shoppingListRepository.save(shoppingList);
        return shoppingListResourceFactory.create(shoppingList);
    }

    public ShoppingListsResponse getAll() {
        final List<ShoppingList> shoppingLists = shoppingListRepository.list();
        final List<ShoppingListResource> shoppingListResources = shoppingLists.stream()
                .map(shoppingListResourceFactory::create)
                .collect(Collectors.toList());

        return new ShoppingListsResponse()
                .setLists(shoppingListResources);
    }

    public void addItems(AddItemsRequest request) {
        final ShoppingList shoppingList = shoppingListRepository.findOne(request.getShoppingListId());

        shoppingList.setItems(request.getItems());
        shoppingListRepository.save(shoppingList);
    }

    public ShoppingListResource get(ObjectId shoppingListId) {
        final ShoppingList shoppingList = shoppingListRepository.findOne(shoppingListId);
        return shoppingListResourceFactory.create(shoppingList);
    }
}
