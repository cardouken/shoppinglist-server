package ee.hein.shoppinglistserver.persistence.entity;

import ee.hein.shoppinglistserver.pojo.Item;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class ShoppingList {

    @BsonId
    private ObjectId id;

    @NotNull
    private String name;

    @NotNull
    private int order;

    private List<Item> items;

    private long version;

    private LocalDateTime created;

    public ShoppingList() {
        this.version = 1L;
    }

    public ObjectId getId() {
        return id;
    }

    public ShoppingList setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ShoppingList setName(String name) {
        this.name = name;
        return this;
    }

    public int getOrder() {
        return order;
    }

    public ShoppingList setOrder(int order) {
        this.order = order;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public ShoppingList setItems(List<Item> items) {
        this.items = items;
        return this;
    }

    public long getVersion() {
        return version;
    }

    public ShoppingList setVersion(long version) {
        this.version = version;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public ShoppingList setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    @BsonIgnore
    public long incrementVersion() {
        final long previousVersion = this.version;
        this.version++;
        return previousVersion;
    }
}
