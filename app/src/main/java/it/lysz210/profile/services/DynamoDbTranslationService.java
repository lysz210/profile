package it.lysz210.profile.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.lysz210.profile.repositories.I18nRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DynamoDbTranslationService implements I18nService {

    private final ObjectMapper dynamodbMapper;
    private final I18nRepository i18nRepository;
    @SneakyThrows
    @Override
    public Collection<I18nGroup> allTranslations() {

        final var information = i18nRepository.retrieveTranslation(Paths.get("knowledge/it-skills/1.java.yaml"));
        final var group = new I18nGroup(
                new I18nKey("it", "/hello"),
                information
        );
        System.out.println(dynamodbMapper.writerWithDefaultPrettyPrinter().writeValueAsString(group));
        return List.of();
    }
}
