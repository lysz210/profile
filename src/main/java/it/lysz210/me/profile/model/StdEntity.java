package it.lysz210.me.profile.model;

import java.io.Serializable;
import java.util.Objects;

public interface StdEntity<ID> extends Serializable {
    ID getId();
    void setId(ID id);

    default int hashCodeId() {
        return Objects.hashCode(this.getId());
    }

    default boolean equalsById(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (this.getClass() == other.getClass()) {
            return Objects.equals(this.getId(), ((StdEntity<?>) other).getId());
        }
        return false;
    }
}
