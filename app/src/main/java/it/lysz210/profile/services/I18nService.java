package it.lysz210.profile.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.lang.NonNull;

import java.util.Collection;

public interface I18nService {


    record I18nGroup (
            @NonNull I18nKey key,
            ObjectNode rootAttributes
    ) { }


    record I18nKey (
            @NonNull String locale,
            @NonNull String fullpath
    ) { }

    Collection<I18nGroup> allTranslations();
}
