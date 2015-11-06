package nl.marcenschede.tests.cqrs2.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by marc on 06/11/15.
 */
public interface DtoObject {

    @JsonIgnore
    String getDtoEntityType();
}
