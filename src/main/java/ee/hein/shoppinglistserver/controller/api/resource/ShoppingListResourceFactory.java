package ee.hein.shoppinglistserver.controller.api.resource;

import ee.hein.shoppinglistserver.persistence.entity.ShoppingList;
import org.springframework.stereotype.Component;

@Component
public class ShoppingListResourceFactory {

    public ShoppingListResource create(ShoppingList shoppingList) {
        if (shoppingList == null) {
            return null;
        }

        return new ShoppingListResource()
                .setId(shoppingList.getId())
                .setName(shoppingList.getName())
                .setItems(shoppingList.getItems())
                .setOrder(shoppingList.getOrder());
    }
}
