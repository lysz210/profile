package it.lysz210.profile.repositories;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Path;

public interface I18nRepository {
    ObjectNode retrieveTranslation(Path source);
}
