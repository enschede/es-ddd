package nl.marcenschede.tests.elastic.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.marcenschede.tests.elastic.base.domains.DomainEntity;
import nl.marcenschede.tests.elastic.base.domains.DomainEntityType;
import nl.marcenschede.tests.elastic.base.events.Event;
import nl.marcenschede.tests.elastic.domain.order.repository.OrderRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Order extends DomainEntity {

    @Autowired
    private OrderRepositoryImpl orderRepositoryImpl;

    private String naam;

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @JsonIgnore
    public List<Event> getRelatedEvents() {
        return orderRepositoryImpl.getRelatedEvents(getId());
    }

    @Override
    public DomainEntityType getDomainEntityType() {
        return DomainEntityType.ORDER;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderRepositoryImpl=" + orderRepositoryImpl +
                ", naam='" + naam + '\'' +
                ", domainEntity='" + super.toString() + '\'' +
                '}';
    }
}
