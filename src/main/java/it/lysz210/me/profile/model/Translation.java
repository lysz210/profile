package it.lysz210.me.profile.model;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity
public class Translation implements StdEntity<TranslationId> {
    @EmbeddedId
    private TranslationId id;

    @NotNull
    @ManyToOne
    @MapsId("locale")
    private Locale locale;

    @NonNull
    @ManyToOne
    @MapsId("group")
    private TranslationGroup group;

    private String value;

    @Override
    public int hashCode() {
        return this.hashCodeId();
    }

    @Override
    public boolean equals(Object obj) {
        return this.equalsById(obj);
    }
}
