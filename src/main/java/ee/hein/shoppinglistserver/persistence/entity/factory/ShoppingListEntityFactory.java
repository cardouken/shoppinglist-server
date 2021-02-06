package ee.hein.shoppinglistserver.persistence.entity.factory;

import ee.hein.shoppinglistserver.persistence.entity.Item;
import ee.hein.shoppinglistserver.persistence.entity.ShoppingList;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ShoppingListEntityFactory {

    public ShoppingList create(String name, List<Item> items) {
        if (name == null) {
            return null;
        }
        return new ShoppingList()
                .setId(new ObjectId())
                .setName(name)
                .setItems(Optional.ofNullable(items).orElse(null));
    }

}
