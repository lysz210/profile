package it.lysz210.me.profile.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record TranslationDto(
        @NotNull LocaleDto locale,
        @NotNull TranslationGroupDto group,
        @NotNull String key,
        @NotNull Long itemId,
        @NotNull String value
) implements Serializable {
}
