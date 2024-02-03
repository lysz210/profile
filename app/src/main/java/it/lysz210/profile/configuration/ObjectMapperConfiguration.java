package it.lysz210.profile.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import it.lysz210.profile.serializers.ArrayNodeSerializer;
import it.lysz210.profile.serializers.I18nGroupSerializer;
import it.lysz210.profile.serializers.ObjectNodeSerializer;
import it.lysz210.profile.serializers.ValueNodeSerializer;
import it.lysz210.profile.services.I18nService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {

    @Bean("dynamodbMapper")
    public ObjectMapper dynamodbMapper() {
        final var mapper =  new ObjectMapper();
        final var module = new SimpleModule();
        module.addSerializer(new I18nGroupSerializer(I18nService.I18nGroup.class));
        module.addSerializer(new ObjectNodeSerializer(ObjectNode.class));
        module.addSerializer(new ArrayNodeSerializer(ArrayNode.class));
        module.addSerializer(new ValueNodeSerializer<>(TextNode.class));
        module.addSerializer(new ValueNodeSerializer<>(NumericNode.class));
        module.addSerializer(new ValueNodeSerializer<>(BooleanNode.class));
        module.addSerializer(new ValueNodeSerializer<>(BinaryNode.class));
        mapper.registerModule(module);
        return mapper;
    }

    @Bean("yamlMapper")
    public ObjectMapper yamlMapper() {
        return new ObjectMapper(new YAMLFactory());
    }
}
