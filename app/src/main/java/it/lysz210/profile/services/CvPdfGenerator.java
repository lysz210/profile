package it.lysz210.profile.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CvPdfGenerator implements PdfProcessor {
    private final Configuration configuration;
    private final MessageSource messages;

    public record I18nMessages (
            MessageSource messages,
            Locale locale
    ) {
        public String translate(String path, Locale locale) {
            try {
                return messages.getMessage(path, null, locale);
            } catch (Exception ex) {
                return null;
            }
        }

        public String translate(String path) {
            return this.translate(path, this.locale);
        }
    }

    public record ProfileModel(
            Map<String, Map<String, Object>> data
    ) {
        public Object get(String path) {
            return this.data.get(path);
        }
        public Collection getList(String base) {
            return this.data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith(base))
                    .map(Map.Entry::getValue)
                    .toList();
        }
    }

    @SneakyThrows
    @Override
    public byte[] generate(Locale locale, Map<String, ObjectNode> context) {

        final var tpl = configuration.getTemplate("cv.ftlh");

        var om = new ObjectMapper();

        var profile = context.entrySet().stream()
                .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> om.convertValue(entry.getValue(), new TypeReference<Map<String, Object>>() {}),
                (a, b) -> a,
                TreeMap<String, Map<String, Object>>::new
        ));

        final var outStream = new ByteArrayOutputStream();
        final var writer = new OutputStreamWriter(outStream);
        final var i18n = new I18nMessages(this.messages, locale);
        tpl.process(Map.of(
                "i18n", i18n,
                "profile", new ProfileModel(profile),
                "cvLink", "https://lysz210.name"
        ), writer);
        return outStream.toByteArray();
    }
}
