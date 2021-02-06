package ee.hein.shoppinglistserver.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

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
        return connectionString;
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
