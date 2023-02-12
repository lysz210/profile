package it.lysz210.profile.me.workexperiences;

import it.lysz210.profile.lib.dynamodb.repository.AbsDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;

@Slf4j
@Repository
public class DynamoWorkExperiencesRepository
    extends AbsDynamoRepository<WorkExperience>
        implements WorkExperiencesRepository
{
    public DynamoWorkExperiencesRepository(DynamoDbEnhancedAsyncClient dynamodb) {
        super(dynamodb, WorkExperience.class);
    }

    @Override
    public Logger getLog() {
        return log;
    }
}
