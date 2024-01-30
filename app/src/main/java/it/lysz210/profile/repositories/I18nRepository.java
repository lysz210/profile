package it.lysz210.profile.repositories;

import com.fasterxml.jackson.databind.JsonNode;

import java.nio.file.Path;

public interface I18nRepository {
    JsonNode retrieveTranslation(Path source);
}
