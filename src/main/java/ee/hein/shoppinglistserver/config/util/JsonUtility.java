package ee.hein.shoppinglistserver.config.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class JsonUtility {

    private static final String DATETIME_JSON_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final ObjectMapper mapper;

    private static class BsonModule extends SimpleModule {
        public BsonModule() {
            super("ObjectId serialization module");
            addSerializer(new ObjectIdSerializer(ObjectId.class));
            addDeserializer(ObjectId.class, new ObjectIdDeserializer(ObjectId.class));
        }
    }

    private static class ObjectIdSerializer extends StdSerializer<ObjectId> {
        public ObjectIdSerializer(Class<ObjectId> t) {
            super(t);
        }

        @Override
        public void serialize(ObjectId value, JsonGenerator jsonGen, SerializerProvider provider) throws IOException {
            jsonGen.writeString(value.toHexString());
        }
    }

    private static class ObjectIdDeserializer extends StdDeserializer<ObjectId> {
        public ObjectIdDeserializer(Class<?> t) {
            super(t);
        }

        @Override
        public ObjectId deserialize(JsonParser p, DeserializationContext context) throws IOException {
            String objectId = p.getValueAsString();
            return new ObjectId(objectId);
        }
    }

    static {
        Jdk8Module jdk8Module = new Jdk8Module();
        BsonModule bsonModule = new BsonModule();

        ZoneId zoneId = ZoneId.of("UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_JSON_FORMAT_PATTERN);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(zoneId));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_JSON_FORMAT_PATTERN).withZone(zoneId);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));

        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(bsonModule);
        mapper.registerModule(jdk8Module);
        mapper.registerModule(javaTimeModule);
        mapper.setDateFormat(simpleDateFormat);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

}
