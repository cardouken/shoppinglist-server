package ee.hein.shoppinglistserver.config;

import ee.hein.shoppinglistserver.config.properties.JasyptProperties;
import ee.hein.shoppinglistserver.config.util.JsonUtility;
import org.apache.commons.lang3.ObjectUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableWebMvc
@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(JsonUtility.getObjectMapper()));
    }

    @Bean
    public StringEncryptor jasyptStringEncryptor(JasyptProperties jasyptProperties) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPassword(
                ObjectUtils.firstNonNull(
                        jasyptProperties.getPassword(),
                        System.getenv("JASYPT_ENCRYPTOR_PASSWORD")
                )
        );
        encryptor.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        encryptor.setIvGenerator(new RandomIvGenerator());
        encryptor.setKeyObtentionIterations(100);
        encryptor.setPoolSize(1);
        return encryptor;
    }
}
