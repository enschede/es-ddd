package nl.marcenschede.tests.elastic.base.repository;

import nl.marcenschede.tests.elastic.base.domains.DomainEntity;

public class IdNotNullException extends Exception {
    
    private DomainEntity t;

    public <T extends DomainEntity> IdNotNullException(T t) {
        this.t = t;
    }
}
