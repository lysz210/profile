package it.lysz210.profile.lib.dynamodb.repository;

import it.lysz210.profile.lib.common.WithLogger;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;

public interface CreateTable<T> extends WithTable<T>, WithLogger {
    default void createTable() {
        final var table = this.getTable();
        final var log = this.getLog();
        try {
            Mono.fromFuture(table.createTable()).block();
            log.info("Table {} created", table.tableName());
        } catch (ResourceInUseException ex) {
            log.info("Table {} already exists", table.tableName());
        }
    }
}
