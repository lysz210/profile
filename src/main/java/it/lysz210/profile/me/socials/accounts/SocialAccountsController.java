package it.lysz210.profile.me.socials.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Locale;

@RequiredArgsConstructor
@RestController
@RequestMapping("me/social-accounts")
public class SocialAccountsController {

    private final SocialAccountService socialAccountsService;


    /**
     * Retrieve all social accounts
     * @return all social accounts
     */
    @GetMapping("")
    public Flux<SocialAccount> getSocialAccounts(@RequestParam(defaultValue = "EN") Locale locale) {
        return socialAccountsService.list(locale);
    }
}
