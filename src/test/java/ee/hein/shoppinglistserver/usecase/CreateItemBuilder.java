package ee.hein.shoppinglistserver.usecase;

import ee.hein.shoppinglistserver.pojo.Item;
import ee.hein.shoppinglistserver.util.TestActionBuilder;
import net.bytebuddy.utility.RandomString;

import java.time.LocalDateTime;

public class CreateItemBuilder implements TestActionBuilder<Item> {

    private String name = RandomString.make(8);
    private LocalDateTime checked = null;

    public CreateItemBuilder() {
    }

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

    public CreateItemBuilder setCheckedTime(LocalDateTime checked) {
        this.checked = checked;
        return this;
    }

    @Override
    public Item build() {
        return new Item(name, checked);
    }
}
