package it.lysz210.me.profile.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record ProfileDto(
        Long id,
        String firstName,
        String lastName,
        @JsonAnyGetter
        Map<String, String> translated
) implements Serializable {
        public ProfileDto(Long id, String firstName, String lastName,
                          Map<String, String> translated) {
                this.id = id;
                this.firstName = firstName;
                this.lastName = lastName;
                this.translated = Optional.ofNullable(translated)
                        .map(HashMap::new)
                        .orElseGet(HashMap::new);
        }

        @JsonAnySetter
        void add(String key, String value) {
                translated.put(key, value);
        }
}
