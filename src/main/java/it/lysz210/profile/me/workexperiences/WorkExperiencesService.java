package it.lysz210.profile.me.workexperiences;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class WorkExperiencesService {
    private final WorkExperiencesRepository workExperiencesRepository;

    @Observed(
            name = "service.work-experiences.list",
            contextualName = "list-all-work-experiences"
    )
    public Flux<WorkExperience> list(Locale locale) {
        return workExperiencesRepository.findAll(locale);
    }
}
