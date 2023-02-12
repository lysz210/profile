package it.lysz210.profile.me.personaldetails;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Service
public class PersonalDetailsService {
    private final PersonalDetailsRepository personalDetailsRepository;

    @Observed(
            name = "service.PersonalDetails.findDetails",
            contextualName = "find-personal-details"
    )
    public Mono<PersonalDetails> findDetails(Locale locale) {
        log.info("find personal details");
        return this.personalDetailsRepository.findAll().next();
    }
}
