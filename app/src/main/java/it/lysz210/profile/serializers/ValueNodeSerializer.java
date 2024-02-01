package it.lysz210.profile.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ValueNodeSerializer extends StdSerializer<ValueNode> {
    public ValueNodeSerializer() {
        this(null);
    }

    public ValueNodeSerializer(Class<ValueNode> type) {
        super(type);
    }

    @Override
    public void serialize(ValueNode jsonNodes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("S", jsonNodes.asText());
        jsonGenerator.writeEndObject();
    }
}
