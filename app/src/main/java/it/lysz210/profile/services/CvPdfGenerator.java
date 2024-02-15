package it.lysz210.profile.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CvPdfGenerator implements PdfProcessor {
    private final Configuration configuration;

    @SneakyThrows
    @Override
    public byte[] generate(Map<String, ObjectNode> context) {

        final var tpl = configuration.getTemplate("cv.ftl");

        var om = new ObjectMapper();

        var ctx = context.entrySet().stream()
                .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> om.convertValue(entry.getValue(), new TypeReference<Map<String, Object>>() {})
        ));

        final var outStream = new ByteArrayOutputStream();
        final var writer = new OutputStreamWriter(outStream);
        tpl.process(ctx, writer);
        return outStream.toByteArray();
    }
}
