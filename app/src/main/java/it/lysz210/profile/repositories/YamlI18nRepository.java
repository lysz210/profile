package it.lysz210.profile.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;

@RequiredArgsConstructor
@Repository
public class YamlI18nRepository implements I18nRepository {

    @Qualifier("yamlMapper")
    private final ObjectMapper yamlMapper;
    @SneakyThrows
    @Override
    public ObjectNode retrieveTranslation(Path source) {
        final var path = Path.of("/i18n/it/me/").resolve(source);
        try (final var file = new ClassPathResource(path.toString()).getInputStream()) {
            return (ObjectNode) yamlMapper.readTree(file);
        }
    }
}
