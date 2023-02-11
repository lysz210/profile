package it.lysz210.profile.me.personaldetails;

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
public class DynamoPersonalDetailsRepository implements PersonalDetailsRepository {
    private final DynamoDbAsyncTable<PersonalDetails> table;

    public DynamoPersonalDetailsRepository(DynamoDbEnhancedAsyncClient dynamodb) {
        final var personalDetails = PersonalDetails.class;
        this.table = dynamodb.table(personalDetails.getCanonicalName(), TableSchema.fromBean(personalDetails));
    }

    @Override
    public Flux<PersonalDetails> findAll() {
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

    public Mono<Void> save(PersonalDetails details) {
        return Mono.fromFuture(this.table.putItem(details));
    }
}
