package it.lysz210.profile.me.socials.accounts;

import reactor.core.publisher.Flux;

public interface SocialAccountsRepository {
    /**
     * retrieve all social accounts
     * @return
     */
    Flux<SocialAccount> findAll();
}