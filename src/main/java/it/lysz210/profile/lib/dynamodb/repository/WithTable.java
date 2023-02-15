package it.lysz210.profile.lib.dynamodb.repository;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;

public interface WithTable<T> {

    Class<T> getEntityType();
    DynamoDbAsyncTable<T> getTable();
}
