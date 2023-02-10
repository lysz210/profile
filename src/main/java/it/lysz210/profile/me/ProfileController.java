package it.lysz210.profile.me;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final ProfileService profileService;

    /**
     * retrieve profile,
     * this is a single profile project
     *
     * @return profile
     */
    @GetMapping("/me")
    public Mono<Profile> getProfile(@RequestParam(defaultValue = "EN") Locale locale) {
        return profileService.getProfile(locale);
    }
}
