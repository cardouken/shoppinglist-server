package ee.hein.shoppinglistserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@SpringBootApplication
@ConfigurationPropertiesScan
public class ShoppinglistServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppinglistServerApplication.class, args);
    }

}
