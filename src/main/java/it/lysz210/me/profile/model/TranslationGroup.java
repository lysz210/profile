package it.lysz210.me.profile.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@ToString(exclude = "translations")
@Entity
public class TranslationGroup implements StdEntity<Long> {
    @Id
    private Long id;

    private String group;

    @OneToMany
    private Collection<Translation> translations;

    @Override
    public int hashCode() {
        return this.hashCodeId();
    }

    @Override
    public boolean equals(Object obj) {
        return this.equalsById(obj);
    }
}
