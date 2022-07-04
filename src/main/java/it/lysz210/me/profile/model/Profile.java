package it.lysz210.me.profile.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
public class Profile implements TranslatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNull
    private String group;

    private String firstName;

    private String lastName;

    private String presentation;

    @Override
    public Set<String> getTranslatedFields() {
        return Set.of(
                "presentation"
        );
    }

    @Override
    public int hashCode() {
        return this.hashCodeId();
    }

    @Override
    public boolean equals(Object obj) {
        return this.equalsById(obj);
    }
}
