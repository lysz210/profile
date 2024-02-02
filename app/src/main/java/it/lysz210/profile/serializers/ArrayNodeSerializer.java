package it.lysz210.profile.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public class ArrayNodeSerializer extends StdSerializer<ArrayNode> {

    public ArrayNodeSerializer(Class<ArrayNode> type) {
        super(type);
    }

    Optional<ValueNodeSerializer.DynamoDbSimpleType> retrieveCandicateSetTypeFor (@NonNull ArrayNode arrayNode) {
        if (arrayNode.isEmpty()) {
            return Optional.empty();
        }
        ValueNodeSerializer.DynamoDbSimpleType candidate = null;
        for (Iterator<JsonNode> it = arrayNode.elements(); it.hasNext(); ) {
            final var node = it.next();
            if (node.isNull() || !node.isValueNode()) {
                break;
            }
            final var dynamoType = ValueNodeSerializer.DynamoDbSimpleType.forValueNodeInstance((ValueNode) node);
            if (Objects.isNull(dynamoType) || (Objects.nonNull(candidate) && candidate != dynamoType )) {
                break;
            }
            candidate = dynamoType;
        }
        return Optional.ofNullable(candidate);
    }

    @Override
    public void serialize(ArrayNode arrayNode, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        final var candidateSetType = retrieveCandicateSetTypeFor(arrayNode);

        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName(
                candidateSetType.map(type -> type + "S").orElse("L")
        );

        jsonGenerator.writeStartArray();

        for (Iterator<JsonNode> it = arrayNode.elements(); it.hasNext(); ) {
            final var node = it.next();
            if (candidateSetType.isEmpty()) {
                serializerProvider.defaultSerializeValue(node, jsonGenerator);
            } else {
                jsonGenerator.writeString(node.asText());
            }
        }

        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
