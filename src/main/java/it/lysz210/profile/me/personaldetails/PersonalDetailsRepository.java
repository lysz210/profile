package it.lysz210.profile.me.personaldetails;

import it.lysz210.profile.lib.dynamodb.repository.query.FindAll;
import reactor.core.publisher.Flux;

public interface PersonalDetailsRepository
    extends FindAll<PersonalDetails>
{
}
