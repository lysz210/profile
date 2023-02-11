package it.lysz210.profile.me.socials.accounts;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;

@Slf4j
@Repository
public class DynamoSocialAccountsRepository implements SocialAccountsRepository {

    private final DynamoDbAsyncTable<SocialAccount> table;

    public DynamoSocialAccountsRepository(DynamoDbEnhancedAsyncClient dynamodb) {
        final var socialAccount = SocialAccount.class;
        this.table = dynamodb.table(socialAccount.getCanonicalName(), TableSchema.fromBean(socialAccount));
    }

    @Observed(
            name = "repository.SocialSccounts.findAll",
            contextualName = "repository-social-accounts"
    )
    @Override
    public Flux<SocialAccount> findAll() {
        return Flux.from(this.table.scan().items());
    }

    public void createTable() {
        try {
            Mono.fromFuture(this.table.createTable()).block();
            log.info("Table {} created", this.table.tableName());
        } catch (ResourceInUseException ex) {
            log.info("Table {} already exists", this.table.tableName());
        }
    }

    public Mono<SocialAccount> save(SocialAccount account) {
        return Mono.fromFuture(this.table.updateItem(account));
    }
    public Flux<SocialAccount> saveAll(Iterable<SocialAccount> accounts) {
        return Flux.fromIterable(accounts)
                .flatMap(this::save);
    }
}
