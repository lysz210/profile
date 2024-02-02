package it.lysz210.profile.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class ObjectNodeSerializer extends StdSerializer<ObjectNode> {

    public ObjectNodeSerializer(Class<ObjectNode> type) {
        super(type);
    }

    @Override
    public void serialize(ObjectNode jsonNodes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("M");
        jsonGenerator.writeStartObject();

        for (Iterator<Map.Entry<String, JsonNode>> it = jsonNodes.fields(); it.hasNext(); ) {
            var entry = it.next();
            serializerProvider.defaultSerializeField(entry.getKey(), entry.getValue(), jsonGenerator);

        }

        jsonGenerator.writeEndObject();

        jsonGenerator.writeEndObject();
    }
}
