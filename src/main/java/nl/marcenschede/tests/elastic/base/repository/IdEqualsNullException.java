package nl.marcenschede.tests.elastic.base.repository;

import nl.marcenschede.tests.elastic.base.domains.AggregateBase;

public class IdEqualsNullException extends Throwable {

    private AggregateBase aggregateBase;

    public <T extends AggregateBase> IdEqualsNullException(T t) {
        this.aggregateBase = t;
    }
}
