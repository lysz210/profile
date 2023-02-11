package it.lysz210.profile.me.personaldetails;

import reactor.core.publisher.Flux;

public interface PersonalDetailsRepository {
    Flux<PersonalDetails> findAll();
}
