package it.lysz210.profile.me.socials;

import it.lysz210.profile.me.socials.accounts.SocialAccount;
import it.lysz210.profile.me.socials.accounts.SocialAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class SocialsController {

    private final SocialAccountService socialAccountsService;

    public SocialsController(SocialAccountService socialAccountsService) {
        this.socialAccountsService = socialAccountsService;
    }

    /**
     * Retrieve all social accounts
     * @return all social accounts
     */
    @GetMapping("me/social-accounts")
    public Flux<SocialAccount> getSocialAccounts() {
        return socialAccountsService.list();
    }
}
