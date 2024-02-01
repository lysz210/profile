package it.lysz210.profile.services;

import java.util.Map;

public interface I18nService<K, T> {
    Map<K, T> allTranslations();
}
