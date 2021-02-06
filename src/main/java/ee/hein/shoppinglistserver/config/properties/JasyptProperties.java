package ee.hein.shoppinglistserver.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "jasypt.encryptor")
public class JasyptProperties {

    private final String password;

    @ConstructorBinding
    public JasyptProperties(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}

