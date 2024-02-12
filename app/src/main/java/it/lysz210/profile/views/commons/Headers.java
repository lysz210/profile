package it.lysz210.profile.views.commons;

import htmlflow.HtmlPage;
import org.xmlet.htmlapifaster.Head;
import org.xmlet.htmlapifaster.Html;

import java.util.function.Consumer;

public class Headers implements Consumer<Head<Html<HtmlPage>>> {
    @Override
    public void accept(Head<Html<HtmlPage>> head) {
        head.meta().attrCharset("utf-8").__()
        // todo: rendere dinamico il nome, cognome e last update
        .meta().attrName("description").attrContent("Curriculum vitae di Lingyong Sun aggiornato a Settembre 2015").__()
        .meta().attrName("author").attrContent("Linyong Sun").__()
        .meta().attrName("keywords").attrContent("curriculum vitae, cv, curriculum, Lingyong, Sun, Lingyong Sun").__()
        .meta().attrName("viewport").attrContent("width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui").__()
        .title().text("Curriculum vitae di Lingyong Sun").__();
        // fixme: aggiungere stylesheet
    }
}
