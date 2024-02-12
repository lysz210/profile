package it.lysz210.profile.views.contents;

import htmlflow.HtmlPage;
import org.xmlet.htmlapifaster.Body;
import org.xmlet.htmlapifaster.Html;

import java.util.function.Consumer;

public class Header implements Consumer<Body<Html<HtmlPage>>> {
    @Override
    public void accept(Body<Html<HtmlPage>> htmlBody) {
        htmlBody.header()
            .h1()
                .img()
                    .attrClass("logo-europass")
                    .attrSrc("")
                    .attrAlt("logo europass")
                    .attrTitle("logo europass")
                .__()
                .span().text("Curriculum Vitae").__()
            .__()
        .__();
    }
}
