package it.lysz210.profile.runners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulumi.Pulumi;
import com.pulumi.aws.dynamodb.Table;
import com.pulumi.aws.dynamodb.TableArgs;
import com.pulumi.aws.dynamodb.TableItem;
import com.pulumi.aws.dynamodb.TableItemArgs;
import com.pulumi.aws.dynamodb.inputs.TableAttributeArgs;
import com.pulumi.aws.dynamodb.inputs.TableGlobalSecondaryIndexArgs;
import com.pulumi.core.Output;
import it.lysz210.profile.services.DynamoDbTranslationService;
import it.lysz210.profile.services.I18nService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class PulumiRunner implements ApplicationRunner {
    public static final String PARTITION_KEY = "locale";
    public static final String SORT_KEY = "fullpath";
    private final DynamoDbTranslationService dynamoDbTranslationService;
    private final ObjectMapper dynamodbMapper;

    Table createTable() {
        return new Table("i18n", TableArgs.builder()
                .name("i18n.cv.lysz210")
                .attributes(
                    TableAttributeArgs.builder()
                            .name(PARTITION_KEY)
                            .type("S")
                            .build(),
                    TableAttributeArgs.builder()
                            .name(SORT_KEY)
                            .type("S")
                            .build()
                )
                .hashKey(PARTITION_KEY)
                .rangeKey(SORT_KEY)
                .billingMode("PROVISIONED")
                .readCapacity(20)
                .writeCapacity(20)
                .globalSecondaryIndexes(
                        TableGlobalSecondaryIndexArgs.builder()
                                .name("keys-index")
                                .hashKey(PARTITION_KEY)
                                .rangeKey(SORT_KEY)
                                .projectionType("KEYS_ONLY")
                                .readCapacity(10)
                                .writeCapacity(10)
                                .build(),
                        TableGlobalSecondaryIndexArgs.builder()
                                .name("metas-index")
                                .hashKey(PARTITION_KEY)
                                .rangeKey(SORT_KEY)
                                .projectionType("INCLUDE")
                                .nonKeyAttributes("mtime", "birthtime")
                                .readCapacity(1)
                                .writeCapacity(1)
                                .build()
                )
                .build());
    }

    @SneakyThrows
    String dynamify(I18nService.I18nGroup i18nGroup) {
        return dynamodbMapper.writeValueAsString(i18nGroup);
    }

    Stream<TableItem> createItems(Table table) {
        final var groups = dynamoDbTranslationService.allTranslations();
        return groups.stream()
                .map(content -> new TableItem(
                        content.key().locale() + content.key().fullpath(),
                        TableItemArgs.builder()
                                .tableName(table.name())
                                .hashKey(table.hashKey())
                                .rangeKey(table.rangeKey().applyValue(value -> value.orElse(null)))
                                .item(this.dynamify(content))
                                .build()
                ));
    }

    @Override
    public void run(ApplicationArguments args){
        Pulumi.run(ctx -> {
                final var table = createTable();
                final var items = createItems(table).map(TableItem::getId).toList();
                ctx.export("tableName", table.name())
                        .export("tableArn", table.arn())
                        .export("items", Output.all(items).applyValue(list -> String.join(" - ", list)));
            }
        );
    }
}
