package it.lysz210.profile.me.socials.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.net.URL;

@Data
@DynamoDbBean
public class SocialAccount {
    @JsonIgnore
    private String id;

    @DynamoDbPartitionKey
    public String getId() {
        return username + "@" + name;
    }

    /**
     * Account name
     */
    private String name;

    /**
     * Account username
     */
    private String username;
    @DynamoDbSortKey
    public String getUsername() {
        return username;
    }
    /**
     * Account icon
     */
    private String icon;
    /**
     * Account color
     */
    private String color;
    /**
     * Account url
     */
    private URL url;
}
