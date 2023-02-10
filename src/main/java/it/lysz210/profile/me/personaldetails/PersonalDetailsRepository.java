package it.lysz210.profile.me.personaldetails;

import reactor.core.publisher.Mono;

import java.util.Locale;

public interface PersonalDetailsRepository {
    Mono<PersonalDetails> find(Locale locale);
}
