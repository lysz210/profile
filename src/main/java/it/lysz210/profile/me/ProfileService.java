package it.lysz210.profile.me;

import io.micrometer.observation.annotation.Observed;
import it.lysz210.profile.me.personaldetails.PersonalDetailsService;
import it.lysz210.profile.me.socials.accounts.SocialAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final SocialAccountService socialAccountService;
    private final PersonalDetailsService personalDetailsService;

    /**
     * Retrieve complete profile
     * @param locale for i18n values
     * @return complete profile
     */
    @Observed(
            name = "service.profile.get",
            contextualName = "get-profile",
            lowCardinalityKeyValues = {"get", "profile"}
    )
    public Mono<Profile> getProfile(Locale locale) {
        final var builder = Profile.builder();
        return Flux.merge(
                socialAccountService.list()
                        .collectList()
                        .map(builder::socialAccounts),
                personalDetailsService.findDetails(locale)
                        .map(builder::details)
        )
                .last()
                .map(Profile.ProfileBuilder::build);
    }
}
