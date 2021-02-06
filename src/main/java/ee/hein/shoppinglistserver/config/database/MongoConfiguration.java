package ee.hein.shoppinglistserver.config.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import ee.hein.shoppinglistserver.config.properties.DatabaseProperties;
import ee.hein.shoppinglistserver.persistence.MongoService;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
public class MongoConfiguration {

    @Bean
    @Primary
    public MongoProperties mongoProperties(DatabaseProperties properties) {
        String uri = String.format(properties.getConnectionString(), properties.getUsername(), properties.getPassword());
        MongoProperties mongoProperties = new MongoProperties();
        mongoProperties.setUri(uri);
        mongoProperties.setDatabase(properties.getName());
        return mongoProperties;
    }

    @Bean
    @Profile("!test")
    public MongoService mongoService(MongoClient mongoClient, MongoProperties mongoProperties) {
        return new MongoService(mongoClient, mongoProperties.getDatabase());
    }

    @Bean
    public MongoClient mongoClient(MongoProperties mongoProperties) {
        CodecRegistry pojoCodecs = fromProviders(PojoCodecProvider.builder().conventions(List.of(Conventions.ANNOTATION_CONVENTION)).automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(getDefaultCodecRegistry(), pojoCodecs);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoProperties.getUri()))
                .codecRegistry(codecRegistry)
                .build();

        return MongoClients.create(settings);
    }

}