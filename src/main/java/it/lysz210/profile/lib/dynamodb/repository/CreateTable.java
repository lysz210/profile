package it.lysz210.profile.lib.dynamodb.repository;

import io.micrometer.observation.annotation.Observed;
import it.lysz210.profile.lib.common.WithLogger;
import reactor.core.publisher.Mono;

public interface CreateTable<T> extends WithTable<T>, WithLogger {

    @Observed(
            name = "repository.create-table",
            contextualName = "repository-create-table"
    )
    default Mono<Void> createTable() {
        final var table = this.getTable();
        final var log = this.getLog();
        return Mono.fromFuture(table.createTable())
                .doOnNext(unused -> log.info("Table {} created", table.tableName()))
                .doOnError(ex -> log.info("Table {} already exists", table.tableName(), ex))
                .onErrorComplete();
    }
}
