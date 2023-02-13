package it.lysz210.profile.me.workexperiences;

import io.micrometer.observation.annotation.Observed;
import it.lysz210.profile.lib.dynamodb.repository.WithTable;
import reactor.core.publisher.Flux;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.Locale;

public interface WorkExperiencesRepository
    extends WithTable<WorkExperience>
{
    @Observed(
            name = "repository.find-all-for-local",
            contextualName = "repository-find-all"
    )
    default Flux<WorkExperience> findAll(Locale locale) {
        final var conditions = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(locale.toString())
                .build());
        return Flux.from(this.getTable().query(conditions).items());
    }
}
