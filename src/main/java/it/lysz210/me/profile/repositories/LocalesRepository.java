package it.lysz210.me.profile.repositories;

import it.lysz210.me.profile.model.Locale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalesRepository extends JpaRepository<Locale, Long> {
}
