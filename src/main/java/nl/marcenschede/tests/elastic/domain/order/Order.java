package nl.marcenschede.tests.elastic.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.marcenschede.tests.elastic.base.domains.AggregateBase;
import nl.marcenschede.tests.elastic.base.domains.AggregateType;
import nl.marcenschede.tests.elastic.base.events.Event;
import nl.marcenschede.tests.elastic.infra.repository.OrderRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class Order extends AggregateBase {

    @Autowired
    private OrderRepositoryImpl orderRepositoryImpl;

    private String naam;
    private List<OrderLine> orderLines = new ArrayList<>();

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    @JsonIgnore
    public List<Event> getRelatedEvents() {
        return orderRepositoryImpl.getRelatedEvents(getId());
    }

    @Override
    public AggregateType getDomainEntityType() {
        return AggregateType.ORDER;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderRepositoryImpl=" + orderRepositoryImpl +
                ", naam='" + naam + '\'' +
                ", super='" + super.toString() + '\'' +
                '}';
    }
}
