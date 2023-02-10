package it.lysz210.profile.me.personaldetails;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RequiredArgsConstructor
@RestController
public class PersonalDetailsController {
    private final PersonalDetailsRepository personalDetailsRepository;

    @GetMapping("/me/personal-details")
    public Mono<PersonalDetails> findPersonalDetails(@RequestParam(defaultValue = "EN") Locale locale) {
        return personalDetailsRepository.find(locale);
    }
}
