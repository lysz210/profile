package it.lysz210.profile.lib.dynamodb.repository;

import it.lysz210.profile.lib.common.WithLogger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Persistable<T> extends WithTable<T>, WithLogger {
    default Mono<T> save(T entity) {
        return Mono.fromFuture(this.getTable().updateItem(entity));
    }

    default Flux<T> saveAll(Iterable<T> entities) {
        return Flux.fromIterable(entities)
                .flatMap(this::save);
    }

}
