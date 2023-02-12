package it.lysz210.profile.me.personaldetails;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RequiredArgsConstructor
@RestController
@RequestMapping("/me/personal-details")
public class PersonalDetailsController {
    private final PersonalDetailsService personalDetailsService;

    @GetMapping("")
    public Mono<PersonalDetails> findPersonalDetails(@RequestParam(defaultValue = "EN") Locale locale) {
        return personalDetailsService.findDetails(locale);
    }
}
