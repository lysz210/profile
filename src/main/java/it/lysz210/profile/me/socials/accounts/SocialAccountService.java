package it.lysz210.profile.me.socials.accounts;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class SocialAccountService {

    Logger log = LoggerFactory.getLogger(SocialAccountService.class);
    private final SocialAccountsRepository socialAccountsRepository;

    @Observed(
            name = "service.social-accounts.list",
            contextualName = "list-all-social-accounts",
            lowCardinalityKeyValues = {"list", "social-accounts"}
    )
    public Flux<SocialAccount> list() {
        log.info("List all social accounts");
        return socialAccountsRepository.findAll();
    }
}
