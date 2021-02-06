package ee.hein.shoppinglistserver.controller.api.request;

public class CreateShoppingListRequest {

    private String name;

    public String getName() {
        return name;
    }

    public CreateShoppingListRequest setName(String name) {
        this.name = name;
        return this;
    }
}
