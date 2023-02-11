package it.lysz210.profile.me.personaldetails;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@EnableScan
public interface MyRepo extends CrudRepository<PersonalDetails, String> {
    Optional<PersonalDetails> findById(String id);
}
