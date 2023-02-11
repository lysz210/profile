package it.lysz210.profile.configs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.lysz210.profile.me.personaldetails.DynamoPersonalDetailsRepository;
import it.lysz210.profile.me.personaldetails.PersonalDetails;
import it.lysz210.profile.me.socials.accounts.DynamoSocialAccountsRepository;
import it.lysz210.profile.me.socials.accounts.SocialAccount;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Flux;
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
import java.util.Collection;

@RequiredArgsConstructor
@Configuration
public class AmazonAwsConfig {

    @Value("classpath:me/social-accounts.json")
    private Path socialAccountsFile;
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

    @EventListener
    public void initTables(ContextRefreshedEvent event) throws IOException {
        final var context = event.getApplicationContext();
        final var detailsRepository = context.getBean(DynamoPersonalDetailsRepository.class);
        final var details = new PersonalDetails();
        details.setName("Lingyong");
        details.setSurname("Sun");

        final var socialAccountRepository = context.getBean(DynamoSocialAccountsRepository.class);
        final var socials = objectMapper.readValue(
                this.socialAccountsFile.toFile(),
                new TypeReference<Collection<SocialAccount>>() {}
        );
        socialAccountRepository.createTable();
        detailsRepository.createTable();
        Flux.concat(
                detailsRepository.save(details),
                socialAccountRepository.saveAll(socials).collectList()
        ).blockLast();
    }
}
