package it.lysz210.profile.runners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper dynamodbMapper;
    @Override
    public void run(ApplicationArguments args) throws JsonProcessingException {
        final var groups = dynamoDbTranslationService.allTranslations();
        System.out.println(dynamodbMapper.writerWithDefaultPrettyPrinter().writeValueAsString(groups));
//        Pulumi.run(ctx ->
//                ctx.export("exampleOutput", Output.of(information.toString()))
//        );
    }
}
