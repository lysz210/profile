package it.lysz210.me.profile.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
public class TranslationId implements Serializable {
    @NotNull
    private Long locale;
    @NotNull
    private Long group;
    @NotNull
    private String key;
    @NotNull
    private Long itemId;
}
