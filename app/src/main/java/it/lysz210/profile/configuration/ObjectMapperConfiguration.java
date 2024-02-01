package it.lysz210.profile.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import it.lysz210.profile.serializers.ObjectNodeSerializer;
import it.lysz210.profile.serializers.ValueNodeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {

    @Bean("dynamodbMapper")
    public ObjectMapper dynamodbMapper() {
        final var mapper =  new ObjectMapper();
        final var module = new SimpleModule();
        module.addSerializer(ObjectNode.class, new ObjectNodeSerializer());
        module.addSerializer(ValueNode.class, new ValueNodeSerializer());
        mapper.registerModule(module);
        return mapper;
    }

    @Bean("yamlMapper")
    public ObjectMapper yamlMapper() {
        return new ObjectMapper(new YAMLFactory());
    }
}
