package it.lysz210.me.profile.services;

import it.lysz210.me.profile.model.TranslatedEntity;
import it.lysz210.me.profile.model.Translation;
import it.lysz210.me.profile.model.TranslationGroup;
import it.lysz210.me.profile.model.TranslationId;
import it.lysz210.me.profile.repositories.TranslationGroupsRepository;
import it.lysz210.me.profile.repositories.TranslationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Validated
@Transactional
@RequiredArgsConstructor
public class TranslationService {
    private final TranslationGroupsRepository translationGroupsRepo;
    private final TranslationsRepository translationsRepo;

    public <T extends TranslatedEntity> Optional<TranslationGroup> findGroup(@NotNull T entity) {
        return translationGroupsRepo.findByGroup(entity.getGroup());
    }

    public <T extends TranslatedEntity> TranslationGroup findOrRegisterGroup(@Validated @NotNull T entity) {
        return this.findGroup(entity)
                .orElseGet(() -> {
                    final var group = new TranslationGroup();
                    group.setGroup(entity.getGroup());
                    return translationGroupsRepo.save(group);
                });
    }

    @SneakyThrows
    public <T extends TranslatedEntity> Optional<Translation> findTranslation(@NotNull T entity, @NotNull String keyFieldName) {
        final var key = Optional.of(entity.getClass())
                .map(type -> BeanUtils.getPropertyDescriptor(type, keyFieldName))
                .map(PropertyDescriptor::getReadMethod)
                .map(getter -> {
                    try {
                        return (String) getter.invoke(entity);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow();
        final var groupId = this.findGroup(entity)
                .map(TranslationGroup::getId)
                .orElseThrow();

        final var translationId = TranslationId.builder()
                .itemId(entity.getId())
                .key(key)
                .group(groupId)
                .locale(1L)
                .build();
        return translationsRepo.findById(translationId);
    }

    public <T extends TranslatedEntity> Map<String, String> translate(@NotNull T entity) {
        return entity.getTranslatedFields().stream()
                .map(fieldName -> {
                    final var value = this.findTranslation(entity, fieldName)
                            .map(Translation::getValue)
                            .orElse(fieldName);
                    return Map.entry(fieldName, value);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
