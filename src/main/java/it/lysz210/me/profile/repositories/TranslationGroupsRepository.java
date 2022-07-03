package it.lysz210.me.profile.repositories;

import it.lysz210.me.profile.model.TranslationGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TranslationGroupsRepository extends JpaRepository<TranslationGroup, Long> {
    Optional<TranslationGroup> findByGroup(String group);
}
