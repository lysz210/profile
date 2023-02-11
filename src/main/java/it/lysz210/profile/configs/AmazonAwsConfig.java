package it.lysz210.profile.configs;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import lombok.Data;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URL;

@Configuration
@EnableDynamoDBRepositories(
        basePackages = "it.lysz210.profile"
)
public class AmazonAwsConfig {

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
    public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider credentials, DynamoDBProperties dynamoDBProperties) {
        return AmazonDynamoDBClient.builder()
                .withCredentials(credentials)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        dynamoDBProperties.endpoint.toString(),
                        Regions.EU_SOUTH_1.getName()
                )).build();
    }

    @Bean
    public AWSCredentialsProvider amazonAwsCredetials (AwsProperties awsProperties) {
        final var credentials = new BasicAWSCredentials (
                awsProperties.accessKey,
                awsProperties.secretKey
        );
        return new AWSStaticCredentialsProvider(credentials);
    }
}
