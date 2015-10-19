package nl.marcenschede.tests.elastic.order;

import nl.marcenschede.tests.elastic.base.domains.DomainEntity;
import nl.marcenschede.tests.elastic.base.domains.DomainEntityType;

public class Order extends DomainEntity {

    private String naam;

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public DomainEntityType getDomainEntityType() {
        return DomainEntityType.ORDER;
    }

    @Override
    public String toString() {
        return "Order{" +
                "naam='" + naam + "\'," +
                super.toString() +
                '}';
    }
}
