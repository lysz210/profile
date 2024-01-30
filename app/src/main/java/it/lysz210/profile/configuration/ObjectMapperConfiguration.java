package it.lysz210.profile.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ObjectMapperConfiguration {

    @Primary
    @Bean("jsonMapper")
    public ObjectMapper jsonMapper() {
        return new ObjectMapper();
    }

    @Bean("yamlMapper")
    public ObjectMapper yamlMapper() {
        return new ObjectMapper(new YAMLFactory());
    }
}
