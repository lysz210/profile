package it.lysz210.profile.me.socials.accounts;

import it.lysz210.profile.lib.dynamodb.repository.AbsDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;

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
