package ee.hein.shoppinglistserver.usecase;

import ee.hein.shoppinglistserver.pojo.Item;

import java.time.LocalDateTime;

public class CreateItemBuilder {

    private String name;
    private LocalDateTime checked;

    public CreateItemBuilder(String name, boolean checked) {
        this.name = name;
        if (checked) {
            this.checked = LocalDateTime.now();
        }
    }

    public CreateItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CreateItemBuilder setChecked(boolean checked) {
        if (checked) {
            this.checked = LocalDateTime.now();
        }
        return this;
    }

    public Item build() {
        return new Item(name, checked);
    }
}
