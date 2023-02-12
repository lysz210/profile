package it.lysz210.profile.me.workexperiences;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Locale;

@RequiredArgsConstructor
@RestController
@RequestMapping("/me/work-experiences")
public class WorkExperiencesController {
    private final WorkExperiencesService workExperiencesService;
    @GetMapping("")
    public Flux<WorkExperience> list(@RequestParam(defaultValue = "EN") Locale locale) {
        return this.workExperiencesService.list(locale);
    }

}
