package it.lysz210.profile.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Iterator;

public class ArrayNodeSerializer extends StdSerializer<ArrayNode> {

    public ArrayNodeSerializer(Class<ArrayNode> type) {
        super(type);
    }

    @Override
    public void serialize(ArrayNode arrayNode, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("L");

        jsonGenerator.writeStartArray();

        for (Iterator<JsonNode> it = arrayNode.elements(); it.hasNext(); ) {
            serializerProvider.defaultSerializeValue(it.next(), jsonGenerator);
        }

        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
