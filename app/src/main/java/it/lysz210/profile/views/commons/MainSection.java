package it.lysz210.profile.views.commons;

import htmlflow.HtmlPage;
import org.xmlet.htmlapifaster.Body;
import org.xmlet.htmlapifaster.Html;
import org.xmlet.htmlapifaster.Section;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;

public interface MainSection extends Consumer<Body<Html<HtmlPage>>> {
    @NotNull
    String getId();

    @Override
    default void accept(Body<Html<HtmlPage>> htmlBody) {
        htmlBody.section()
                .attrId(this.getId())
                .of(this::section)
        .__();
    }

    void section(Section<Body<Html<HtmlPage>>> section);
}
