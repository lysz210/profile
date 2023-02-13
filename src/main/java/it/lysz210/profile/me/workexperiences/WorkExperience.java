package it.lysz210.profile.me.workexperiences;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.Collection;
import java.util.Locale;

@Data
@DynamoDbBean
public class WorkExperience {
    private Locale locale;
    private String name;

    private String start;
    private String end;
    private String role;
    private String company;
    private String sector;
    private Collection<String> jobs;

    @DynamoDbPartitionKey
    public Locale getLocale() {
        return this.locale;
    }
    @DynamoDbSortKey
    public String getName() {
        return this.name;
    }

}
