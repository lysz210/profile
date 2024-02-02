package it.lysz210.profile.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.lysz210.profile.services.I18nService;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class I18nGroupSerializer extends StdSerializer<I18nService.I18nGroup> {

    public I18nGroupSerializer(Class<I18nService.I18nGroup> type) {
        super(type);
    }

    @Override
    public void serialize(I18nService.I18nGroup i18nGroup, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        final var key = i18nGroup.key();

        serializerProvider.defaultSerializeField("locale", TextNode.valueOf(key.locale()), jsonGenerator);
        serializerProvider.defaultSerializeField("fullpath", TextNode.valueOf(key.fullpath()), jsonGenerator);

        final var root = i18nGroup.rootAttributes();
        if (Objects.nonNull(root)) {
            for (Iterator<Map.Entry<String, JsonNode>> it = root.fields(); it.hasNext(); ) {
                var entry = it.next();
                serializerProvider.defaultSerializeField(entry.getKey(), entry.getValue(), jsonGenerator);

            }
        }

        jsonGenerator.writeEndObject();
    }
}
