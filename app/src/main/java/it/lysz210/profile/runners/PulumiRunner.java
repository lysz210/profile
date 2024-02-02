package it.lysz210.profile.runners;

import com.pulumi.Pulumi;
import com.pulumi.core.Output;
import it.lysz210.profile.repositories.I18nRepository;
import it.lysz210.profile.services.DynamoDbTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PulumiRunner implements ApplicationRunner {
    private final DynamoDbTranslationService dynamoDbTranslationService;
    private final I18nRepository i18nRepository;
    @Override
    public void run(ApplicationArguments args) {
        final var information = dynamoDbTranslationService.allTranslations();
//        Pulumi.run(ctx ->
//                ctx.export("exampleOutput", Output.of(information.toString()))
//        );
    }
}
