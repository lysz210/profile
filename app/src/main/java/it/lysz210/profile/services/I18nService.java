package it.lysz210.profile.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.lang.NonNull;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

public interface I18nService {


    record I18nGroup (
            @NonNull I18nKey key,
            @NonNull I18nMeta meta,
            ObjectNode rootAttributes
    ) { }


    record I18nKey (
            @NonNull String locale,
            @NonNull String fullpath
    ) { }

    record I18nMeta (
            @NonNull Instant lastModifiedTime,
            Instant creationTime
    ) {
        public static I18nMeta of (@NonNull BasicFileAttributes attributes) {
            final var lastModified = Optional.of(attributes.lastModifiedTime())
                    .map(FileTime::toInstant)
                    .orElseThrow(() -> new IllegalArgumentException("lastModifiedTime required"));
            final var createdAt = Optional.of(attributes.creationTime())
                    .map(FileTime::toInstant)
                    .orElse(null);
            return new I18nMeta(
                    lastModified,
                    createdAt
            );
        }
    }

    Collection<I18nGroup> allTranslations();
}
