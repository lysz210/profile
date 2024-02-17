package it.lysz210.profile.configuration;

import freemarker.template.TemplateException;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import java.io.IOException;
import java.util.Locale;

@Configuration
public class CustomFreeMarkerConfiguration {
    public record I18nMessages (
            MessageSource messages
    ) {
        public String translate(String path, Locale locale) {
            try {
                return messages.getMessage(path, null, locale);
            } catch (Exception ex) {
                return null;
            }
        }
    }
    @Primary
    @Bean
    public freemarker.template.Configuration customFreeMarkerConfigurationFactory(
            FreeMarkerConfigurationFactory factory,
            MessageSource messages
    ) throws TemplateException, IOException {
        final var configuration = factory.createConfiguration();
        configuration.setSharedVariable("messages", new I18nMessages(messages));
        return configuration;
    }
}
