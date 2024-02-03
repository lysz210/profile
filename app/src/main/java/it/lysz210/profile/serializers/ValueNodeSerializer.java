package it.lysz210.profile.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

public class ValueNodeSerializer<T extends ValueNode> extends StdSerializer<T> {

    @RequiredArgsConstructor
    enum DynamoDbSimpleType {
        S (Set.of(TextNode.class)),
        N (Set.of(NumericNode.class)),
        BOOL (Set.of(BooleanNode.class)),
        B (Set.of(BinaryNode.class))
        ;
        private final Set<Class<? extends ValueNode>> jsonNodeTypes;

        public static Optional<DynamoDbSimpleType> forValueNode(@NonNull Class<? extends ValueNode> type) {
            return Arrays.stream(values())
                    .filter(dynamoType -> dynamoType.jsonNodeTypes.contains(type))
                    .findFirst();
        }

        public static <V extends ValueNode> DynamoDbSimpleType forValueNodeInstance(@NonNull V valueNode) {
            DynamoDbSimpleType matchedType = null;
            if (valueNode.isBinary()) {
                matchedType = B;
            } else if (valueNode.isBoolean()) {
                matchedType = BOOL;
            } else if (valueNode.isNumber()) {
                matchedType = N;
            } else if (valueNode.isTextual()) {
                matchedType = S;
            }
            return matchedType;
        }
    }

    private final DynamoDbSimpleType dynamoDbSimpleType;
    public ValueNodeSerializer(Class<T> type) {
        super(type);
        this.dynamoDbSimpleType = DynamoDbSimpleType.forValueNode(type).orElseThrow(() -> new IllegalArgumentException(
                String.format("No type registered for type %s", type.getCanonicalName())
        ));
    }

    @Override
    public void serialize(T jsonNodes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(dynamoDbSimpleType.name(), jsonNodes.asText());
        jsonGenerator.writeEndObject();
    }
}
