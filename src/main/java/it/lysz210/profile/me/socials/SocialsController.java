package it.lysz210.profile.me.socials;

import it.lysz210.profile.me.socials.accounts.SocialAccount;
import it.lysz210.profile.me.socials.accounts.SocialAccountsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class SocialsController {

    private final SocialAccountsRepository socialAccountsRepository;

    public SocialsController(SocialAccountsRepository socialAccountsRepository) {
        this.socialAccountsRepository = socialAccountsRepository;
    }

    /**
     * Retrieve all social accounts
     * @return all social accounts
     */
    @GetMapping("me/social-accounts")
    public Flux<SocialAccount> getSocialAccounts() {
        return socialAccountsRepository.findAll();
    }
}
