package it.lysz210.profile.me.personaldetails;

import it.lysz210.profile.lib.dynamodb.repository.AbsDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;

@Slf4j
@Repository
public class DynamoPersonalDetailsRepository
    extends AbsDynamoRepository<PersonalDetails>
        implements PersonalDetailsRepository
{

    public DynamoPersonalDetailsRepository(DynamoDbEnhancedAsyncClient dynamodb) {
        super(dynamodb, PersonalDetails.class);
    }

    @Override
    public Logger getLog() {
        return log;
    }
}
