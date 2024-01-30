package it.lysz210.profile.runners;

import com.pulumi.Pulumi;
import com.pulumi.core.Output;
import it.lysz210.profile.repositories.I18nRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class PulumiRunner implements ApplicationRunner {

    private final I18nRepository i18nRepository;
    @Override
    public void run(ApplicationArguments args) {
        final var information = i18nRepository.retrieveTranslation(Paths.get("index.yaml"));
        Pulumi.run(ctx ->
                ctx.export("exampleOutput", Output.of(information.toString()))
        );
    }
}
