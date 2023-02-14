package it.lysz210.profile.lib.dynamodb.repository;

import io.micrometer.observation.annotation.Observed;
import it.lysz210.profile.lib.common.WithLogger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Persistable<T> extends WithTable<T>, WithLogger {

    @Observed(
            name = "repository.save",
            contextualName = "repository-save"
    )
    default Mono<T> save(T entity) {
        return Mono.fromFuture(this.getTable().updateItem(entity));
    }

    @Observed(
            name = "repository.saveAll",
            contextualName = "repository-save-all"
    )
    default Flux<T> saveAll(Iterable<T> entities) {
        return Flux.fromIterable(entities)
                .flatMap(this::save);
    }

}
