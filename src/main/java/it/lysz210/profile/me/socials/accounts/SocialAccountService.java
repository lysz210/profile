package it.lysz210.profile.me.socials.accounts;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Service
public class SocialAccountService {

    private final SocialAccountsRepository socialAccountsRepository;

    @Observed(
            name = "service.social-accounts.list",
            contextualName = "list-all-social-accounts"
    )
    public Flux<SocialAccount> list(Locale locale) {
        log.info("List all social accounts");
        return socialAccountsRepository.findAll();
    }
}
