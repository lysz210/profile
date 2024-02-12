package it.lysz210.profile.views.contents;

import htmlflow.HtmlPage;
import it.lysz210.profile.views.commons.DlItem;
import it.lysz210.profile.views.commons.MainSection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.xmlet.htmlapifaster.Body;
import org.xmlet.htmlapifaster.Html;
import org.xmlet.htmlapifaster.Section;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class Competenze implements MainSection {
    @NotNull
    private final String id;

    @Override
    public void section(Section<Body<Html<HtmlPage>>> section) {
        section
                .h1().text("competenze.title").__()
                .dl()
                    .of(DlItem.of("competenze.lingue.madre.title", "competenze.lingue.madre.content"))
                .__();
    }

}
