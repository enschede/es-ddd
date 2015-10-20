package nl.marcenschede.tests.elastic.base.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import nl.marcenschede.tests.elastic.domain.factuur.Factuur;
import nl.marcenschede.tests.elastic.domain.order.Order;

import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "domainClass")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Order.class, name = "order"),
        @JsonSubTypes.Type(value = Factuur.class, name = "factuur")})
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
