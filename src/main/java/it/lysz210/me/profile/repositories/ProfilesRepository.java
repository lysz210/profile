package it.lysz210.me.profile.repositories;

import it.lysz210.me.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilesRepository extends JpaRepository<Profile, Long> {
}
