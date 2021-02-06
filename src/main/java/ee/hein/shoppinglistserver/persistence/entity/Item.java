package ee.hein.shoppinglistserver.persistence.entity;

import java.time.LocalDateTime;

public class Item {

    private String name;
    private LocalDateTime acquired;

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDateTime isAcquired() {
        return acquired;
    }

    public Item setAcquired(LocalDateTime acquired) {
        this.acquired = acquired;
        return this;
    }
}
