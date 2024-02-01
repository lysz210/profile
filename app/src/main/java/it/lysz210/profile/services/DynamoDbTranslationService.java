package it.lysz210.profile.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.lysz210.profile.repositories.I18nRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class DynamoDbTranslationService implements I18nService<String, JsonNode> {

    private final ObjectMapper dynamodbMapper;
    private final I18nRepository i18nRepository;
    @SneakyThrows
    @Override
    public Map<String, JsonNode> allTranslations() {

        final var information = i18nRepository.retrieveTranslation(Paths.get("index.yaml"));
        System.out.println(dynamodbMapper.writeValueAsString(information));
        return Map.of();
    }
}
