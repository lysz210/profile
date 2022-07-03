package it.lysz210.me.profile.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class Profile implements StdEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String firstName;

    private String lastName;

    private String presentation;

    @Override
    public int hashCode() {
        return this.hashCodeId();
    }

    @Override
    public boolean equals(Object obj) {
        return this.equalsById(obj);
    }
}
