package it.lysz210.profile.me.personaldetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@DynamoDbBean
public class PersonalDetails {

    @JsonIgnore
    private String id;

    @DynamoDbPartitionKey
    public String getId() {
        return name + "@" + surname;
    }

    private String name;
    @DynamoDbSortKey
    public String getName() {
        return name;
    }

    private String surname;
}
