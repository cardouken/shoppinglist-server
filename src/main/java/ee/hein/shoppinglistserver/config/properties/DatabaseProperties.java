package ee.hein.shoppinglistserver.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Optional;

@ConfigurationProperties(prefix = "shoppinglist.database")
public class DatabaseProperties {

    private final String connectionString;
    private final String username;
    private final String password;
    private final String name;

    @ConstructorBinding
    public DatabaseProperties(String connectionString, String username, String password, String name) {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String getConnectionString() {
        return Optional.ofNullable(System.getenv("MONGODB_URI"))
                .orElse(connectionString);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
