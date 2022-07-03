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
public class Locale implements StdEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

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
