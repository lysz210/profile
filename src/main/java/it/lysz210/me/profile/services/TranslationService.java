package it.lysz210.me.profile.services;

import it.lysz210.me.profile.model.Translation;
import it.lysz210.me.profile.model.TranslationGroup;
import it.lysz210.me.profile.repositories.TranslationGroupsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
@Transactional
@RequiredArgsConstructor
public class TranslationService {
    private final TranslationGroupsRepository translationGroupsRepo;

    public <T> Optional<TranslationGroup> findGroup(@NotNull Class<T> entityType) {
        return translationGroupsRepo.findByGroup(entityType.getName());
    }

    public <T> TranslationGroup registerGroup(@NotNull Class<T> entityType) {
        return this.findGroup(entityType)
                .orElseGet(() -> {
                    final var group = new TranslationGroup();
                    group.setGroup(entityType.getName());
                    return group;
                });
    }

    public <T> Translation findTranslation(@NonNull T entity, @NotNull String keyFieldName) throws NoSuchFieldException {
        final var type = entity.getClass();
        final var field = type.getField(keyFieldName);
        final var
    }
}
