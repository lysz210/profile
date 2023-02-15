package it.lysz210.profile.lib.dynamodb.repository;

import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public abstract class AbsDynamoRepository<T>
    implements WithTable<T>, CreateTable<T>, Persistable<T>
{
    @Getter
    private final DynamoDbAsyncTable<T> table;
    @Getter
    private final Class<T> entityType;

    public AbsDynamoRepository(DynamoDbEnhancedAsyncClient dynamodb, Class<T> entityType) {
        this.entityType = entityType;
        this.table = dynamodb.table(entityType.getCanonicalName(), TableSchema.fromBean(entityType));
    }
}
