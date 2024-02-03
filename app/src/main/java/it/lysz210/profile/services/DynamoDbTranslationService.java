package it.lysz210.profile.services;

import it.lysz210.profile.repositories.I18nRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class DynamoDbTranslationService implements I18nService {

    private final I18nRepository i18nRepository;
    @Value("${i18n.root}")
    private final Path i18nRoot;
    @SneakyThrows
    @Override
    public Collection<I18nGroup> allTranslations() {
        try (final var localeDirs = Files.list(i18nRoot)) {
            return localeDirs.flatMap(dir -> {
                final var locale = i18nRoot.relativize(dir).toString();
                final var builder = Stream.<I18nGroup>builder();
                try {
                    Files.walkFileTree(dir, new SimpleFileVisitor<>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            final var returnVal = super.visitFile(file, attrs);
                            builder.accept(new I18nGroup(
                                    new I18nKey(locale, "/" + dir.relativize(file)),
                                    I18nMeta.of(attrs),
                                    i18nRepository.retrieveTranslation(file)
                            ));
                            return returnVal;
                        }
                    });
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
                return builder.build();
            }).toList();
        }
    }
}
