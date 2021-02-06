package ee.hein.shoppinglistserver.service;

import ee.hein.shoppinglistserver.controller.api.request.CreateShoppingListRequest;
import ee.hein.shoppinglistserver.controller.api.request.DeleteShoppingListRequest;
import ee.hein.shoppinglistserver.controller.api.request.OrderShoppingListsRequest;
import ee.hein.shoppinglistserver.controller.api.request.UpdateShoppingListRequest;
import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResource;
import ee.hein.shoppinglistserver.controller.api.resource.ShoppingListResourceFactory;
import ee.hein.shoppinglistserver.controller.response.ShoppingListsResponse;
import ee.hein.shoppinglistserver.exception.ApplicationLogicException;
import ee.hein.shoppinglistserver.persistence.entity.ShoppingList;
import ee.hein.shoppinglistserver.persistence.repository.ShoppingListRepository;
import ee.hein.shoppinglistserver.pojo.Item;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListResourceFactory shoppingListResourceFactory;

    public ShoppingListService(ShoppingListRepository shoppingListRepository,
                               ShoppingListResourceFactory shoppingListResourceFactory) {
        this.shoppingListRepository = shoppingListRepository;
        this.shoppingListResourceFactory = shoppingListResourceFactory;
    }

    public void create(CreateShoppingListRequest request) {
        int order = Optional.ofNullable(shoppingListRepository.getHighestOrderedItem()).map(ShoppingList::getOrder).orElse(0);
        final List<Item> items = request.getItems();
        items.forEach(item -> item.setId(new ObjectId()));

        shoppingListRepository.save(
                new ShoppingList()
                        .setId(new ObjectId())
                        .setName(request.getName())
                        .setItems(request.getItems())
                        .setOrder(++order)
                        .setCreated(LocalDateTime.now())
        );
    }

    public ShoppingListsResponse listAll() {
        final List<ShoppingList> shoppingLists = shoppingListRepository.list();
        final List<ShoppingListResource> shoppingListResources = shoppingLists.stream()
                .map(shoppingListResourceFactory::create)
                .collect(Collectors.toList());

        return new ShoppingListsResponse()
                .setShoppingLists(shoppingListResources);
    }

    public ShoppingListResource findOne(ObjectId shoppingListId) {
        final ShoppingList shoppingList = shoppingListRepository.findOne(shoppingListId)
                .orElseThrow(EntityNotFoundException::new);

        return shoppingListResourceFactory.create(shoppingList);
    }

    public void update(UpdateShoppingListRequest request) {
        final ShoppingList shoppingList = shoppingListRepository.findOne(request.getShoppingListId())
                .orElseThrow(EntityNotFoundException::new);

        final List<Item> requestedItems = request.getItems();
        final List<Item> existingItems = shoppingList.getItems();
        for (Item existingItem : existingItems) {
            for (Item requestedItem : requestedItems) {
                if (requestedItem.getChecked() != null && existingItem.getChecked() != null) {
                    requestedItem.setChecked(existingItem.getChecked());
                }
            }
        }

        shoppingList.setItems(requestedItems);
        if (!shoppingListRepository.update(shoppingList, shoppingList.incrementVersion())) {
            throw new ApplicationLogicException(ApplicationLogicException.ErrorCode.RETRY);
        }
    }

    public void delete(DeleteShoppingListRequest request) {
        shoppingListRepository.delete(request.getShoppingListIds());
    }

    public void order(OrderShoppingListsRequest request) {
        final List<ObjectId> requestedShoppingListIds = request.getShoppingListIds();

        final List<ShoppingList> shoppingLists = shoppingListRepository.list();
        final List<ObjectId> shoppingListIds = shoppingLists.stream()
                .map(ShoppingList::getId)
                .collect(Collectors.toList());
        requestedShoppingListIds.removeIf(requestedShoppingId -> !shoppingListIds.contains(requestedShoppingId));

        int order = 1;
        final Map<ObjectId, Integer> shoppingListIdOrderMap = new HashMap<>();
        for (ObjectId folderId : requestedShoppingListIds) {
            shoppingListIdOrderMap.put(folderId, order++);
        }

        shoppingListIdOrderMap.forEach((requestedShoppingId, requestedOrder) -> {
            for (ShoppingList shoppingList : shoppingLists) {
                if (Objects.equals(shoppingList.getId(), requestedShoppingId) && !Objects.equals(shoppingList.getOrder(), requestedOrder)) {
                    shoppingList.setOrder(requestedOrder);
                    if (!shoppingListRepository.update(shoppingList, shoppingList.incrementVersion())) {
                        throw new ApplicationLogicException(ApplicationLogicException.ErrorCode.RETRY);
                    }
                }
            }
        });
    }
}
