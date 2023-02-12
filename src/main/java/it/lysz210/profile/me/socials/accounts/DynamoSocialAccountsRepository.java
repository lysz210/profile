package it.lysz210.profile.me.socials.accounts;

import io.micrometer.observation.annotation.Observed;
import it.lysz210.profile.lib.dynamodb.repository.AbsDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;

@Slf4j
@Repository
public class DynamoSocialAccountsRepository
    extends AbsDynamoRepository<SocialAccount>
        implements SocialAccountsRepository
{

    public DynamoSocialAccountsRepository(DynamoDbEnhancedAsyncClient dynamodb) {
        super(dynamodb,SocialAccount.class);
    }


    @Override
    public Logger getLog() {
        return log;
    }
}
