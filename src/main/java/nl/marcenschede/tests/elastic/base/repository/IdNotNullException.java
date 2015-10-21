package nl.marcenschede.tests.elastic.base.repository;

import nl.marcenschede.tests.elastic.base.domains.AggregateBase;

public class IdNotNullException extends Exception {
    
    private AggregateBase t;

    public <T extends AggregateBase> IdNotNullException(T t) {
        this.t = t;
    }
}
