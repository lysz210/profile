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

    public AbsDynamoRepository(DynamoDbEnhancedAsyncClient dynamodb, Class<T> type) {
        this.table = dynamodb.table(type.getCanonicalName(), TableSchema.fromBean(type));
    }
}
