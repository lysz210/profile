package it.lysz210.me.profile.repositories;

import it.lysz210.me.profile.model.Translation;
import it.lysz210.me.profile.model.TranslationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranslationsRepository extends JpaRepository<Translation, TranslationId> {
}
