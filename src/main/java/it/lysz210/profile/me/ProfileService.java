package it.lysz210.profile.me;

import io.micrometer.observation.annotation.Observed;
import it.lysz210.profile.me.personaldetails.PersonalDetailsService;
import it.lysz210.profile.me.socials.accounts.SocialAccountService;
import it.lysz210.profile.me.workexperiences.WorkExperiencesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProfileService {
    private final SocialAccountService socialAccountService;
    private final PersonalDetailsService personalDetailsService;
    private final WorkExperiencesService workExperiencesService;

    /**
     * Retrieve complete profile
     * @param locale for i18n values
     * @return complete profile
     */
    @Observed(
            name = "service.profile.get",
            contextualName = "get-profile"
    )
    public Mono<Profile> getProfile(Locale locale) {
        final var builder = Profile.builder();
        return Flux.merge(
                socialAccountService.list(locale)
                        .collectList()
                        .map(builder::socialAccounts),
                personalDetailsService.findDetails(locale)
                        .map(builder::details),
                workExperiencesService.list(locale)
                        .collectList()
                        .map(builder::workExperiences)

        )
                .last()
                .map(Profile.ProfileBuilder::build);
    }
}
