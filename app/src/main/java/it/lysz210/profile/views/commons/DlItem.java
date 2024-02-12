package it.lysz210.profile.views.commons;

import htmlflow.HtmlPage;
import org.xmlet.htmlapifaster.*;

import java.util.function.Consumer;

public interface DlItem extends Consumer<Dl<Section<Body<Html<HtmlPage>>>>> {
    @Override
    default void accept(Dl<Section<Body<Html<HtmlPage>>>> dl) {
        dl
                .dt().of(this::dt).__()
                .dd().of(this::dd).__();
    }

    void dd(Dd<Dl<Section<Body<Html<HtmlPage>>>>> dd);

    void dt(Dt<Dl<Section<Body<Html<HtmlPage>>>>> dt);

    static DlItem of(final String term, final String definition) {
        return new DlItem() {
            @Override
            public void dd(Dd<Dl<Section<Body<Html<HtmlPage>>>>> dd) {
                dd.text(definition);
            }

            @Override
            public void dt(Dt<Dl<Section<Body<Html<HtmlPage>>>>> dt) {
                dt.text(term);
            }
        };
    }
}
