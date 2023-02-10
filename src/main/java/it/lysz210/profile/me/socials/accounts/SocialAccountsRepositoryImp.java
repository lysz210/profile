package it.lysz210.profile.me.socials.accounts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Path;

@Repository
public class SocialAccountsRepositoryImp implements SocialAccountsRepository {

    private final ObjectMapper objectMapper;
    private final Path socialAccountsFile;

    public SocialAccountsRepositoryImp(
            ObjectMapper objectMapper,
            @Value("classpath:me/social-accounts.json")
            Path socialAccountsFile
    ) {
        this.objectMapper = objectMapper;
        this.socialAccountsFile = socialAccountsFile;
    }


    @Observed(
            name = "repository.social-accounts.findAll",
            contextualName = "find-all-social-accounts",
            lowCardinalityKeyValues = {"find", "all"}
    )
    @Override
    public Flux<SocialAccount> findAll() {
        try {
            return Flux.fromIterable(objectMapper.readValue(
                    this.socialAccountsFile.toFile(),
                    new TypeReference<>() {}
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
