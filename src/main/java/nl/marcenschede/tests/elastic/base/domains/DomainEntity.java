package nl.marcenschede.tests.elastic.base.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public abstract class DomainEntity implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public abstract DomainEntityType getDomainEntityType();

    @Override
    public String toString() {
        return "DomainEntity{" +
                "id='" + id + '\'' +
                '}';
    }
}
