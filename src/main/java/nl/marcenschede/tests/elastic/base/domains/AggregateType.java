package nl.marcenschede.tests.elastic.base.domains;

import nl.marcenschede.tests.elastic.domain.factuur.Factuur;
import nl.marcenschede.tests.elastic.domain.order.Order;

public enum AggregateType {
    ORDER("order", Order.class),
    FACTUUR("factuur", Factuur.class);

    private String type;
    private Class<?> clazz;

    AggregateType(String type, Class clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public String getType() {
        return type;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
