package it.lysz210.me.profile;

import it.lysz210.me.profile.dto.ProfileDto;
import it.lysz210.me.profile.repositories.LocalesRepository;
import it.lysz210.me.profile.repositories.ProfilesRepository;
import it.lysz210.me.profile.repositories.TranslationGroupsRepository;
import it.lysz210.me.profile.repositories.TranslationsRepository;
import it.lysz210.me.profile.services.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final TranslationService translationService;
    private final LocalesRepository localesRepo;
    private final TranslationGroupsRepository translationGroupsRepo;
    private final TranslationsRepository translationsRepo;
    private final ProfilesRepository profilesRepo;


    @GetMapping("/{profileId}")
    public ProfileDto findProfile(@PathVariable Long profileId) {
        return profileId == null
                ? null
                : profilesRepo.findById(profileId)
                    .map(profile -> new ProfileDto(
                            profile.getId(),
                            profile.getFirstName(),
                            profile.getLastName(),
                            translationService.translate(profile)
                    ))
                    .orElse(null);
    }
}
