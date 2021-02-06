package ee.hein.shoppinglistserver.util;

public interface TestActionBuilder<T> {

    T build();

    default ApiResponse buildApi() {
        T build = build();
        return new ApiResponse(JsonUtility.toJson(build));
    }

    default Void empty() {
        return null;
    }
}
