package it.lysz210.profile.services;

import htmlflow.HtmlFlow;
import htmlflow.HtmlView;
import it.lysz210.profile.views.BaseTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProfileDocument {
    public HtmlView<Object> view() {
        // todo: definire le risorse per il pdf generatore.
        // tutte le risorse dovranno avere il base path corretto
        return HtmlFlow.view(new BaseTemplate());
    }
}
