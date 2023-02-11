package it.lysz210.profile.me.personaldetails;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.util.Locale;

@RequiredArgsConstructor
@RestController
public class PersonalDetailsController {
    private final PersonalDetailsRepository personalDetailsRepository;

    @GetMapping("/me/personal-details")
    public Mono<PersonalDetails> findPersonalDetails(@RequestParam(defaultValue = "EN") Locale locale) {
        return personalDetailsRepository.find(locale);
    }

    @Inject
    MyRepo repo;

    @GetMapping("/me/test")
    public Object test() {
        return repo.findAll();
    }

    @Inject
    DynamoDBMapper mapper;

    @Inject
    AmazonDynamoDB dynamoDB;

    @GetMapping("/me/init")
    public Object init() {
        final var request = mapper.generateCreateTableRequest(PersonalDetails.class);
        request.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L)
        );
        return dynamoDB.createTable(request);
    }
}
