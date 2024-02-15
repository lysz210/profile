package it.lysz210.profile.services;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map;

public interface PdfProcessor {
    byte[] generate(Map<String, ObjectNode> context);
}
