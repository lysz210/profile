package it.lysz210.me.profile.model;

import java.util.Set;

public interface TranslatedEntity extends StdEntity<Long> {
    String getGroup();

    Set<String> getTranslatedFields();
}
