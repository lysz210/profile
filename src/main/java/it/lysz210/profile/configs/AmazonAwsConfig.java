package it.lysz210.profile.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import it.lysz210.profile.lib.common.I18nazed;
import it.lysz210.profile.lib.dynamodb.repository.AbsDynamoRepository;
import it.lysz210.profile.me.personaldetails.PersonalDetails;
import it.lysz210.profile.me.socials.accounts.SocialAccount;
import it.lysz210.profile.me.workexperiences.WorkExperience;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Configuration
public class AmazonAwsConfig {

    @Value("classpath:me/social-accounts.json")
    private Path socialAccountsFile;

    @Value("classpath:me/en-work-experiences.json")
    private Path enWorkExperiences;
    @Value("classpath:me/it-work-experiences.json")
    private Path itWorkExperiences;

    private final ObjectMapper objectMapper;

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "amazon.dynamodb")
    public static class DynamoDBProperties {
        private URL endpoint;
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "amazon.aws")
    public static class AwsProperties {
        private String secretKey;
        private String accessKey;
    }

    @Bean
    public AwsCredentialsProvider amazonAwsCredetials (AwsProperties awsProperties) {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(
                awsProperties.accessKey,
                awsProperties.secretKey
        ));
    }

    @Bean
    public DynamoDbAsyncClient dynamodb(
            AwsCredentialsProvider credentialsProvider,
            DynamoDBProperties dynamoDBProperties
    ) throws URISyntaxException {
        return DynamoDbAsyncClient.builder()
                .credentialsProvider(credentialsProvider)
                .endpointOverride(dynamoDBProperties.endpoint.toURI())
                .region(Region.EU_SOUTH_1)
                .build();
    }
    @Bean
    public DynamoDbEnhancedAsyncClient dynamodbEnhanced(DynamoDbAsyncClient dynamodbClient) {

        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamodbClient)
                .build();
    }

    @Observed(
            name = "configs.AmazonAwsConfig.initTables",
            contextualName = "dynamodb-init-tables"
    )
    @EventListener
    public void initTables(ContextRefreshedEvent event) {
        final var context = event.getApplicationContext();
        Flux.just(AbsDynamoRepository.class)
                .map(context::getBeansOfType)
                .flatMapIterable(Map::values)
                .flatMap(repository -> repository.createTable()
                        .thenReturn(repository)
                        .onErrorReturn(repository)
                        .flux()
                        .flatMap(unused -> this.seedTable(repository)))
                .blockLast();
    }

    public <T> Flux<T> provideIterableData(final Class<T> entityType) {
        return Flux.just(entityType)
                .flatMap(t -> {
                    if (SocialAccount.class == entityType) {
                        return Mono.just(this.socialAccountsFile.toFile());
                    } else if (WorkExperience.class == entityType) {
                        return Flux.just(
                                this.enWorkExperiences.toFile(),
                                this.itWorkExperiences.toFile()
                        );
                    } else {
                        return Flux.empty();
                    }
                }).flatMap(file -> {
                    Flux<T> data;
                    try {
                        data = Flux.fromIterable(objectMapper.readValue(
                                file,
                                objectMapper.getTypeFactory()
                                        .constructCollectionType(List.class, entityType)
                        ));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    final var locale = Locale.of(file.getName().substring(0, 2));
                    if (Set.of(entityType.getInterfaces()).contains(I18nazed.class)) {
                        data = data.map(entity -> {
                            ((I18nazed) entity).setLocale(locale);
                            return entity;
                        });
                    }

                    return data;
                });

    }

    public <T> Publisher<T> provideData(Class<T> entityType) {
        if (PersonalDetails.class == entityType) {
            final var details = new PersonalDetails();
            details.setName("Lingyong");
            details.setSurname("Sun");
            return Mono.just(entityType.cast(details));
        } else {
            return provideIterableData(entityType);
        }
    }

    @Observed(
            name = "configs.AmazonAwsConfig.seedTables",
            contextualName = "configs-amazon-aws-config"
    )
    public <T> Flux<T> seedTable(AbsDynamoRepository<T> repository) {
        return Flux.from(this.provideData(repository.getEntityType()))
                .flatMap(entity -> repository.save(repository.getEntityType().cast(entity)));
    }
}
