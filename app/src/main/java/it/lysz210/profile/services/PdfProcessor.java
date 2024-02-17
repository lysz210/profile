package it.lysz210.profile.services;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Locale;
import java.util.Map;

public interface PdfProcessor {
    byte[] generate(Locale locale, Map<String, ObjectNode> context);
}
