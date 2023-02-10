package it.lysz210.profile.me;

import it.lysz210.profile.me.personaldetails.PersonalDetails;
import it.lysz210.profile.me.socials.accounts.SocialAccount;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class Profile {
    private PersonalDetails details;
    private Collection<SocialAccount> socialAccounts;
}
