package it.lysz210.profile.me.workexperiences;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@DynamoDbBean
public class WorkExperience {
    @JsonIgnore
    private String id;
    private String name;

    private String start;
    private String end;
    private String role;
    private String company;
    private String sector;
    private Collection<String> jobs;

    @DynamoDbPartitionKey
    public String getId() {
        return Stream.of(this.name, this.start)
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }
    @DynamoDbSortKey
    public String getStart() {
        return this.start;
    }

}
