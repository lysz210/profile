package it.lysz210.profile.services;

import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.Writer;

@RequiredArgsConstructor
@Service
public class CvPdfGenerator implements PdfProcessor {
    private final Configuration configuration;

    @SneakyThrows
    @Override
    public void generate(Writer writer) {

        final var tpl = configuration.getTemplate("cv.html");
        tpl.process(null, writer);
    }
}
