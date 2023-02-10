package it.lysz210.profile.me.personaldetails;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class PersonalDetailsService {
    private final PersonalDetailsRepository personalDetailsRepository;

    @Observed(
            name = "service.PersonalDetails.findDetails",
            contextualName = "find-personal-details",
            lowCardinalityKeyValues = {"find", "details"}
    )
    public Mono<PersonalDetails> findDetails(Locale locale) {
        return this.personalDetailsRepository.find(locale);
    }
}
