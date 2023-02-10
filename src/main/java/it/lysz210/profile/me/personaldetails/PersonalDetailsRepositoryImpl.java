package it.lysz210.profile.me.personaldetails;

import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Repository
public class PersonalDetailsRepositoryImpl implements PersonalDetailsRepository {
    @Observed(
            name = "repository.PersonalDetails.findPersonalDetails",
            contextualName = "find-personal-details",
            lowCardinalityKeyValues = {"find", "personal details"}
    )
    @Override
    public Mono<PersonalDetails> find(Locale locale) {
        final var details = new PersonalDetails();
        details.setName("Lingyong");
        details.setSurname("Sun");
        return Mono.just(details);
    }
}
