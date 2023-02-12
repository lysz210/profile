package it.lysz210.profile.lib.dynamodb.repository.query;

import io.micrometer.observation.annotation.Observed;
import it.lysz210.profile.lib.dynamodb.repository.WithTable;
import reactor.core.publisher.Flux;

public interface FindAll<T> extends WithTable<T> {
    @Observed(
            name = "dynamodb.repository.findAll",
            contextualName = "dynamodb-repository-operation"
    )
    default Flux<T> findAll() {
        return Flux.from(this.getTable().scan().items());
    }

}
