package ee.hein.shoppinglistserver.pojo;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class Item {

    private ObjectId id;
    private String name;
    private LocalDateTime checked;

    public Item() {
    }

    public Item(String name, LocalDateTime checked) {
        this.id = new ObjectId();
        this.name = name;
        this.checked = checked;
    }

    public ObjectId getId() {
        return id;
    }

    public Item setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDateTime getChecked() {
        return checked;
    }

    public Item setChecked(LocalDateTime checked) {
        this.checked = checked;
        return this;
    }

    public Item markCheckedNow() {
        this.checked = LocalDateTime.now();
        return this;
    }

    public Item markUnchecked() {
        this.checked = null;
        return this;
    }
}
