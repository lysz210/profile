package it.lysz210.me.profile.dto;

import java.io.Serializable;

public record ProfileDto(
        Long id,
        String firstName,
        String lastName,
        String presentation
) implements Serializable {
}
