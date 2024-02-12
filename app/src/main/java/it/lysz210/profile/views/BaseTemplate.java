package it.lysz210.profile.views;

import htmlflow.HtmlPage;
import htmlflow.HtmlTemplate;
import it.lysz210.profile.views.commons.Headers;
import it.lysz210.profile.views.contents.*;

public class BaseTemplate implements HtmlTemplate {

    public void resolve(HtmlPage page) {
        page
            .html()
                .attrLang("en")
                .head().of(new Headers()).__()
                .body()
                    .of(new Header())
                    .of(new InformazioniPersonali("informazioni_personali"))
                    .of(new Esperienze("esperienze_professionali"))
                    .of(new Formazione("istruzione_e_formazione"))
                    .of(new Competenze("competenze_personali"))
                    .of(new Consenso("Formazione"))
                .__()
            .__();
    }
}
